package com.exam.controller.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.exam.common.enums.impl.ExamUserStatusEnum;
import com.exam.dao.ExamDao;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.model.ExamUserModel;
import com.exam.pojo.param.ExamUserParam;
import com.exam.security.util.GetTokenInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * @author 高歌
 * @Description 支付宝支付
 * @Date 2021-08-24
 */
@Controller
@Api(tags = "支付模块接口")
@CrossOrigin
public class AlipayController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ExamUserDao examUserDao;
    @Autowired
    private ExamDao examDao;
    private String examId;

    //支付宝账号信息
    @Value("${alipay.info.appId}")
    private String appId;
    @Value("${alipay.info.privateKey}")
    private String privateKey;
    @Value("${alipay.info.alipayPublicKey}")
    private String alipayPublicKey;

    /**
     * 这么写会有线程安全问题，待完善。
     */
    //收货人：地址

    //支付人姓名获取
    private String tokenStorage;

    //用户名
    private String username;


    @ApiOperation(value = "支付路径")
    @PostMapping("/alipay/{examId}")
    @ResponseBody
    public String ailpay(@PathVariable("examId") String examId,
                         HttpServletRequest httpServletRequest)
            throws IOException {
         username = GetTokenInfoUtil.getUsername();
        ExamUserModel examUserModel = examUserDao.queryByExamIdAndUserId(examId, username);
        ExamModel examModel = examDao.queryById(examUserModel.getExamId());
        String total_amount = examModel.getPrice();
        this.examId = examId;
        //去沙箱里面找自己的
        AlipayClient alipayClient = new
                DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                appId,
                privateKey,  //私钥 不知道是什么 往上面看 配置沙箱密钥的时候 自己保存的
                "json", "utf-8",
                alipayPublicKey, "RSA2");
        //订单号  自定义
        String out_trade_no = UUID.randomUUID().toString().replace("-", "");
        out_trade_no = URLDecoder.decode(out_trade_no, "UTF-8");
//        String total_amount = "200";
        total_amount = URLDecoder.decode(total_amount, "UTF-8");
        String subject = "标题";
        subject = URLDecoder.decode(subject, "UTF-8");
        String body = "描述";
        body = URLDecoder.decode(body, "UTF-8");
        String passback_params = username;
        passback_params = URLDecoder.decode(passback_params, "UTF-8");
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        //同步通知
        alipayRequest.setReturnUrl("http://82.157.42.25:2020/paySuccessful");
        //异步通知
        alipayRequest.setNotifyUrl("支付成功去接口处理东西【必须外网能访问】");
        //配置参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"passback_params\":\"" + passback_params + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + total_amount + "," +
                "    \"subject\":\"" + subject + "\"," +
                "    \"body\":\"" + body + "\"" +
                "    }" +
                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //form就是一个表单 html 直接给前端 替换 body标签里面的东西
        return form;
    }

    @ApiOperation("/支付成功跳转")
    @GetMapping("/paySuccessful")
    public String paySuccessful() throws Exception {
//        String username = GetTokenInfoUtil.getUsername();
        ExamUserModel examUserModel = examUserDao.queryByExamIdAndUserId(examId, username);
        ExamUserParam examUserParam = new ExamUserParam();
        examUserParam.setStatus(ExamUserStatusEnum.Apply_Pay);
        examUserParam.setId(examUserModel.getId());
         examUserDao.update(examUserParam);
//        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/#/registrationQuery/index?type=registrationQuery")
//                         .openConnection();
//                conn.setInstanceFollowRedirects(false);
//                conn.setConnectTimeout(5000);
//                 return conn.getHeaderField("Location");
//        return Result.ok("支付成功");
        return "redirect:http://82.157.42.25:2021/#/registrationQuery/index?type=registrationQuery";
    }

}
