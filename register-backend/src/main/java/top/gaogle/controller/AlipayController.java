package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.annotation.RepeatSubmit;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.pojo.model.AliPayFaceToFaceModel;
import top.gaogle.pojo.model.AliPayResultModel;
import top.gaogle.service.AlipayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付宝支付
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    private final AlipayService alipayService;

    @Autowired
    public AlipayController(AlipayService alipayService) {
        this.alipayService = alipayService;
    }


    /**
     * 获取支付二维码
     */
    @Anonymous
    @GetMapping("/alipay_preorder")
    public ResponseEntity<I18nResult<AliPayResultModel>> aliPayPreorder() {
        AliPayFaceToFaceModel aliPayFaceToFaceModel = new AliPayFaceToFaceModel();
        aliPayFaceToFaceModel.setBody("我是body体");
        aliPayFaceToFaceModel.setSubject("我是主题");
        aliPayFaceToFaceModel.setOutTradeNo("123122123");
        aliPayFaceToFaceModel.setTotalAmount("12.5");
        return alipayService.aliPayPreorder(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 企业充值（企业端）
     */
    @RepeatSubmit(interval = 5000)
    @PostMapping("/enterprise_pay")
    public ResponseEntity<I18nResult<String>> enterprisePay(@RequestBody AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        return alipayService.enterprisePay(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 支付宝后端回调地址(异步)(企业端)
     */
    @Anonymous
    @PostMapping("/enterprise_notify_url")
    public String enterpriseNotifyUrl(HttpServletRequest request, HttpServletResponse response) {
        return alipayService.enterpriseNotifyUrl(request, response);
    }

    /**
     * 支付宝后端回调地址（同步）(企业端)
     */
    @Anonymous
    @GetMapping("/enterprise_return_url")
    public void enterpriseReturnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        alipayService.enterpriseReturnUrl(request, response);
    }

    /**
     * 用户支付（客户端）
     */
    @Anonymous
    @PostMapping("/client_pay")
    public ResponseEntity<I18nResult<String>> clientPay(@RequestBody AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        return alipayService.clientPay(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 支付宝后端回调地址(异步)(客户端)
     */
    @Anonymous
    @PostMapping("/client_notify_url")
    public String clientNotifyUrl(HttpServletRequest request, HttpServletResponse response) {
        return alipayService.clientNotifyUrl(request, response);
    }

    /**
     * 支付宝后端回调地址（同步）(用户端)
     */
    @Anonymous
    @GetMapping("/client_return_url")
    public void clientReturnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        alipayService.clientReturnUrl(request, response);
    }

    /**
     * 获取支付宝支付表单
     */
    @Anonymous
    @GetMapping("/alipay_pay")
    public ResponseEntity<I18nResult<String>> alipayPay(@RequestParam("outTradeNo") String outTradeNo, HttpServletResponse response) {
        AliPayFaceToFaceModel aliPayFaceToFaceModel = new AliPayFaceToFaceModel();
        aliPayFaceToFaceModel.setBody("我是body体");
        aliPayFaceToFaceModel.setSubject("我是主题");
        aliPayFaceToFaceModel.setOutTradeNo(outTradeNo);
        aliPayFaceToFaceModel.setTotalAmount("12.5");
        return alipayService.alipayPay(aliPayFaceToFaceModel,response).toResponseEntity();
    }

    /**
     * 支付宝后端回调地址(异步)
     */
    @Anonymous
    @PostMapping("/notify_url")
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        return alipayService.notifyUrl(request, response);
    }

    /**
     * 支付宝后端回调地址（同步）
     */
    @Anonymous
    @GetMapping("/return_url")
    public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        alipayService.returnUrl(request, response);
    }

    /**
     * 查询订单
     */
    @Anonymous
    @GetMapping("/query_trade")
    public ResponseEntity<I18nResult<AliPayResultModel>> queryTrade(@RequestParam("outTradeNo") String outTradeNo) {
        AliPayFaceToFaceModel aliPayFaceToFaceModel = new AliPayFaceToFaceModel();
        aliPayFaceToFaceModel.setBody("我是body体");
        aliPayFaceToFaceModel.setSubject("我是主题");
        aliPayFaceToFaceModel.setOutTradeNo(outTradeNo);
        aliPayFaceToFaceModel.setTotalAmount("12.5");
        return alipayService.queryTrade(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 交易退款接口
     */
    @Anonymous
    @GetMapping("/pay_trade_refund")
    public ResponseEntity<I18nResult<AliPayResultModel>> payTradeRefund() {
        AliPayFaceToFaceModel aliPayFaceToFaceModel = new AliPayFaceToFaceModel();
        aliPayFaceToFaceModel.setBody("我是body体");
        aliPayFaceToFaceModel.setSubject("我是主题");
        aliPayFaceToFaceModel.setOutTradeNo("123122121234534");
        aliPayFaceToFaceModel.setTotalAmount("12.5");
        return alipayService.payTradeRefund(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 支付宝订单撤销(关闭订单)
     */
    @Anonymous
    @GetMapping("/pay_trade_cancel")
    public ResponseEntity<I18nResult<String>> payTradeCancel() {
        AliPayFaceToFaceModel aliPayFaceToFaceModel = new AliPayFaceToFaceModel();
        aliPayFaceToFaceModel.setBody("我是body体");
        aliPayFaceToFaceModel.setSubject("我是主题");
        aliPayFaceToFaceModel.setOutTradeNo("123122121234534");
        aliPayFaceToFaceModel.setTotalAmount("12.5");
        return alipayService.payTradeCancel(aliPayFaceToFaceModel).toResponseEntity();
    }

    /**
     * 通知前端
     */
    @Anonymous
    @GetMapping("/notice")
    public String notice() {

        return "redirect:http://82.157.42.25:82/comapny";
    }

}
