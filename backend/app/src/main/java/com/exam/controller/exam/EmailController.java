package com.exam.controller.exam;


import com.exam.common.Result;
import com.exam.common.Utils.IDGenerator;
import io.swagger.annotations.Api;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(tags = "Email")
@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${email.emailAccount}")
    private  String emailAccount;
    @Value("${email.authCode}")
    private  String authCode;

    @GetMapping("/getAuthCode")
    public Result getAuthCode(String receiver) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setCharset("utf-8");
        email.addTo(receiver);//设置收件人
        email.setFrom(emailAccount,"考试报名系统官方邮件");//发送人的邮箱为自己的，用户名可以随便填
        email.setAuthentication(emailAccount,authCode);//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setSubject("注册验证码");//设置发送主题
        String authCode = IDGenerator.authCode();
        String msg="您的验证码为"+authCode+","+"有效期为60秒";
        email.setMsg(msg);//设置发送内容

//        email.setMsg("曹浩");//设置发送内容
        email.send();//进行发送
        stringRedisTemplate.opsForValue().set("AuthCode"+receiver,authCode,60, TimeUnit.SECONDS);
        return Result.ok("发送成功");
    }
}
