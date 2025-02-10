package top.gaogle.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import top.gaogle.common.RegisterConst;
import top.gaogle.config.AlipayConfig;
import top.gaogle.dao.master.EnterpriseBillMapper;
import top.gaogle.dao.master.EnterpriseMapper;
import top.gaogle.dao.master.RegisterBillMapper;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.util.*;
import top.gaogle.pojo.dto.TimeAfterMinutesDTO;
import top.gaogle.pojo.enums.AlipayTradeStatusEnum;
import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.enums.EnterpriseBillTypeEnum;
import top.gaogle.pojo.model.*;
import top.gaogle.pojo.param.EnterpriseBillEditParam;
import top.gaogle.pojo.param.RegisterBillEditParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static top.gaogle.common.RegisterConst.ALIPAY_PRODUCT_CODE;

@Service
public class AlipayService extends SuperService {

    private static final Logger SYS_PAY_LOGGER = LoggerFactory.getLogger("sys-pay");

    private final AlipayConfig aliPayConfig;
    private final EnterpriseBillMapper enterpriseBillMapper;
    private final EnterpriseMapper enterpriseMapper;
    private final TransactionTemplate transactionTemplate;
    private final RegisterPublishMapper registerPublishMapper;
    private final RegisterBillMapper registerBillMapper;
    private final MoreTransactionService moreTransactionService;

    @Autowired
    public AlipayService(AlipayConfig aliPayConfig, EnterpriseBillMapper enterpriseBillMapper, EnterpriseMapper enterpriseMapper, @Qualifier("transactionTemplate") TransactionTemplate transactionTemplate, RegisterPublishMapper registerPublishMapper, RegisterBillMapper registerBillMapper, MoreTransactionService moreTransactionService) {
        this.aliPayConfig = aliPayConfig;
        this.enterpriseBillMapper = enterpriseBillMapper;
        this.enterpriseMapper = enterpriseMapper;
        this.transactionTemplate = transactionTemplate;
        this.registerPublishMapper = registerPublishMapper;
        this.registerBillMapper = registerBillMapper;
        this.moreTransactionService = moreTransactionService;
    }

    @Autowired
    private AlipayClient getPlatformAlipayClient() {
        return new DefaultAlipayClient(aliPayConfig.getOpenApiDomain(), aliPayConfig.getAppId(), aliPayConfig.getPrivateKey(), AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8, aliPayConfig.getAlipayPublicKey(), aliPayConfig.getSignType());
    }

    private AlipayTradePrecreateRequest getTradePreCreateRequest(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        //实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.precreate（统一收单线下交易预创建（扫码支付）
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();//设置业务参数
        model.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());//商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001
        model.setSubject(aliPayFaceToFaceModel.getSubject());//订单标题
        model.setTotalAmount(aliPayFaceToFaceModel.getTotalAmount());//订单金额，精确到小数点后两位
        model.setBody(aliPayFaceToFaceModel.getBody());//订单描述
        model.setPassbackParams("sss");
        request.setBizModel(model);
        /*
         异步通知地址，以http或者https开头的，商户外网可以post访问的异步地址，用于接收支付宝返回的支付结果，如果未收到该通知可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602475759
         */
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());

        return request;
    }


    /**
     * 支付宝预下单(二维码)
     */
    public I18nResult<AliPayResultModel> aliPayPreorder(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        I18nResult<AliPayResultModel> result = I18nResult.newInstance();
        log.info("支付宝预下单，商户订单号：{}", aliPayFaceToFaceModel.getOutTradeNo());
        try {
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradePrecreateRequest tradePreCreateRequest = getTradePreCreateRequest(aliPayFaceToFaceModel);
            AlipayTradePrecreateResponse response = alipayClient.execute(tradePreCreateRequest);
            log.info("支付宝预下单接口调用成功，返回参数：{}", response.getBody());
            // System.out.println(response.getBody());
            if (StringUtils.equalsAny(response.getCode(), "10000")) {
                AliPayResultModel resultModel = new AliPayResultModel();
                resultModel.setOutTradeNo(response.getOutTradeNo());
                resultModel.setQrCode(response.getQrCode());
                return result.succeed().setData(resultModel);
            } else {
                return result.failedBadRequest().setMessage("获取支付二维码失败，错误信息：" + response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result.failed().setMessage("下单失败");
        }
    }

    /**
     * 获取支付宝支付表单
     */
    public I18nResult<String> alipayPay(AliPayFaceToFaceModel aliPayFaceToFaceModel, HttpServletResponse servletResponse) {
        I18nResult<String> result = I18nResult.newInstance();
        log.info("支付宝预下单，商户订单号：{}", aliPayFaceToFaceModel.getOutTradeNo());
        try {
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(aliPayConfig.getNotifyUrl());
            request.setReturnUrl(aliPayConfig.getReturnUrl());
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();//设置业务参数
            model.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());//商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001
            model.setSubject(aliPayFaceToFaceModel.getSubject());//订单标题
            model.setTotalAmount(aliPayFaceToFaceModel.getTotalAmount());//订单金额，精确到小数点后两位
            model.setBody(aliPayFaceToFaceModel.getBody());//订单描述
            model.setPassbackParams("sss");
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            String timeExpire = DateUtil.getTimeAfterMinutes(1);
            log.info("过期时间：" + timeExpire);
            model.setTimeExpire(timeExpire);

            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            log.info("支付宝预下单接口调用成功，返回参数：{}", response.getBody());
            servletResponse.setContentType("text/html;charset=utf-8");
            servletResponse.getWriter().write(response.getBody());
            servletResponse.getWriter().flush();
            servletResponse.getWriter().close();
            if (response.isSuccess()) {
                return result.succeed().setData(response.getBody());
            } else {
                return result.failedBadRequest().setMessage("获取支付宝支付表单失败，错误信息：" + response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result.failed().setMessage("下单失败");
        }
    }


    /**
     * 交易状态查询
     * 可以查看以下帮助文档:
     * 判断交易是否成功：https://opensupport.alipay.com/support/helpcenter/195/201602516393?ant_source=zsearch
     * 状态ACQ.TRADE_NOT_EXIST（交易不存在）https://opensupport.alipay.com/support/helpcenter/89/201602475600?ant_source=zsearch
     */
    public I18nResult<AliPayResultModel> queryTrade(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        I18nResult<AliPayResultModel> result = I18nResult.newInstance();
        try {
            log.info("调用支付宝交易状态查询接口，单号：{}", aliPayFaceToFaceModel.getOutTradeNo());
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.query（统一收单线下交易查询）
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            // 注：交易号（TradeNo）与订单号（OutTradeNo）二选一传入即可，如果2个同时传入，则以交易号为准
            //支付接口传入的商户订单号。如：2020061601290011200000140004 **/
            model.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());
            // 异步通知/查询接口返回的支付宝交易号，如：2020061622001473951448314322 **/
            request.setBizModel(model);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            log.info("调用支付宝交易状态查询接口成功，返回参数：{}", response.getBody());
            if (response.isSuccess()) {
                AliPayResultModel resultModel = new AliPayResultModel();
                resultModel.setOutTradeNo(response.getOutTradeNo());
                resultModel.setTradeStatus(response.getTradeStatus());
                resultModel.setTradeNo(response.getTradeNo());
                return result.succeed().setData(resultModel);
            } else {
                log.info("调用支付宝交易状态查询接口失败，失败信息：{}", response.getSubMsg());
                return result.failedBadRequest().setMessage("支付宝订单查询失败：" + response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result.failed().setMessage("订单查询异常！");
        }
    }

    /**
     * 交易退款接口
     */
    public I18nResult<AliPayResultModel> payTradeRefund(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        I18nResult<AliPayResultModel> result = I18nResult.newInstance();
        try {
            log.info("调用支付宝退款接口，单号：{}", aliPayFaceToFaceModel.getOutTradeNo());
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
            //商户订单号
            refundModel.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());
            //退款金额
            refundModel.setRefundAmount(aliPayFaceToFaceModel.getTotalAmount());
            //退款描述
            refundModel.setRefundReason("正常退款");
            refundRequest.setBizModel(refundModel);
            AlipayTradeRefundResponse response = alipayClient.execute(refundRequest);
            log.info("支付宝交易退款接口调用成功，返回参数：{}", response.getBody());
            if (!response.isSuccess()) {
                log.error("支付宝交易退款接口调用失败，单号：{}", aliPayFaceToFaceModel.getOutTradeNo());
                return result.failedBadRequest().setMessage("退款失败，错误信息：" + response.getSubMsg());
            }
            AliPayResultModel resultModel = new AliPayResultModel();
            resultModel.setOutTradeNo(response.getOutTradeNo());
            resultModel.setRefundFee(response.getRefundFee());
            resultModel.setTradeNo(response.getTradeNo());
            resultModel.setFundChange(response.getFundChange());
            return result.succeed().setData(resultModel);
        } catch (Exception e) {
            e.printStackTrace();
            return result.failed().setMessage("退款异常！");
        }
    }


    public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            log.info("接收到支付回调信息returnUrl");
            //获取支付宝公钥
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.error("回调了前端============================");
                //修改订单状态,根据商户订单号查询订单信息
                response.sendRedirect(aliPayConfig.getFrontendUrl());
            } else {
                log.error("验签失败，请联系管理员解决");
                response.sendRedirect(aliPayConfig.getFrontendUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单修改失败，请联系管理员解决");
            response.sendRedirect(aliPayConfig.getFrontendUrl());
        }
    }


    /**
     * 支付宝支付回调（后端）
     *
     * @param request  请求
     * @param response 响应
     */
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("接收到notifyUrl支付回调信息");
            //获取支付宝公钥
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.info("验签成功，返回参数：{}", params);
                AliPayResultModel resultModel = new AliPayResultModel();
                resultModel.setOutTradeNo(params.get("out_trade_no"));
                resultModel.setTradeStatus(params.get("trade_status"));
                resultModel.setTradeNo(params.get("trade_no"));
                resultModel.setBody(params.get("body"));
                //TODO 调用业务接口
                log.error("回调了后端============================");
                return "success";

            } else {
                log.info("支付宝验签失败，请联系工作人员");
                //验签失败该接口被别人调用
                return "支付宝异步回调验签失败，请留意";
            }
        } catch (Exception e) {
            log.error("notifyUrl发生异常", e);
            return "支付宝异步回调验签失败，请留意";
        }
    }

    /**
     * 支付宝订单撤销(关闭订单)
     *
     * @param payModel 请求参数
     */
    public I18nResult<String> payTradeCancel(AliPayFaceToFaceModel payModel) {
        I18nResult<String> result = I18nResult.newInstance();
        log.info("调用支付宝订单撤销接口，单号：{}", payModel.getOutTradeNo());
        AlipayClient alipayClient = getPlatformAlipayClient();
        // 构造请求参数以调用接口
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        // 设置原支付请求的商户订单号
        model.setOutTradeNo(payModel.getOutTradeNo());
        request.setBizModel(model);
        try {
            AlipayTradeCancelResponse response = alipayClient.execute(request);
            log.info("调用支付宝订单撤销接口成功，返回参数：{}", response.getBody());
            if (response.isSuccess()) {
                return result.succeed().setData("成功：" + response.getBody());
            } else {
                return result.failedBadRequest().setMessage("关闭订单失败,失败原因：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return result.failed().setMessage("关闭订单异常！");
        }
    }


    public I18nResult<String> enterprisePay(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            Long numAmount = aliPayFaceToFaceModel.getNumAmount();
            String comment = aliPayFaceToFaceModel.getComment();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            String loginUsername = SecurityUtil.getLoginUsername();
            if (!RegisterConst.ENTERPRISE_RECHARGE_AMOUNT_LIST.contains(numAmount)) {
                return result.failedBadRequest().setMessage("充值金额不在规定范围内！");
            }

            if (StringUtils.isAnyEmpty(enterpriseId)) {
                return result.failedBadRequest().setMessage("缺少必要参数！");
            }

            EnterpriseBillEditParam editParam = new EnterpriseBillEditParam();
            String uniqueId = UniqueUtil.getUniqueId();
            editParam.setId(uniqueId);
            editParam.setEnterpriseId(enterpriseId);
            editParam.setType(EnterpriseBillTypeEnum.TOP_UP);
            editParam.setStatus(BillStatusEnum.INIT);
            editParam.setAmount(numAmount);
            editParam.setSubject("企业充值");
            editParam.setComment(comment);
            editParam.setSystemComment("系统备注");
            TimeAfterMinutesDTO minutesDTO = DateUtil.getTimeDTOAfterMinutes(10);
            String alipayTimeExpire = minutesDTO.getFormat();
            Long alipayTimeExpireAt = minutesDTO.getTimestamp();
            editParam.setAlipayTimeExpire(alipayTimeExpire);
            editParam.setAlipayTimeExpireAt(alipayTimeExpireAt);
            editParam.setCreateBy(loginUsername);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(aliPayConfig.getEnterpriseNotifyUrl());
            request.setReturnUrl(aliPayConfig.getEnterpriseReturnUrl());
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();//设置业务参数
            model.setOutTradeNo(uniqueId);//商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001
            model.setSubject("企业充值");//订单标题
            String totalAmount = StringUtil.amountLong2String(numAmount);
            model.setTotalAmount(totalAmount);//订单金额，精确到小数点后两位
            Map<String, String> body = new HashMap<>();
            body.put("enterpriseId", enterpriseId);
            body.put("totalAmount", totalAmount);
            body.put("alipayTimeExpire", alipayTimeExpire);
            body.put("outTradeNo", uniqueId);
            body.put("subject", "企业充值");
            String bodyJson = JsonUtil.object2Json(body);
            model.setBody("style-register系统余额支付");//订单描述
            model.setPassbackParams(bodyJson);
            model.setProductCode(ALIPAY_PRODUCT_CODE);
            model.setTimeExpire(alipayTimeExpire);
            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                enterpriseBillMapper.insert(editParam);
                return result.succeed().setData(response.getBody());
            } else {
                return result.failedBadRequest().setMessage("获取支付宝支付表单失败，错误信息：" + response.getSubMsg());
            }
        } catch (Exception e) {
            log.error("企业充值失败:", e);
            return result.failed().setMessage("下单失败");
        }
    }

    public String enterpriseNotifyUrl(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> requestParams = request.getParameterMap();
        log.info("enterpriseNotifyUrl-requestParams参数：{}", JsonUtil.object2Json(requestParams));
        try {
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipayPublicKey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.info("enterpriseNotifyUrl验签成功");
                String outTradeNo = params.get("out_trade_no");
                String tradeStatus = params.get("trade_status");
                String tradeNo = params.get("trade_no");
                EnterpriseBillModel enterpriseBillModel = enterpriseBillMapper.queryOneById(outTradeNo);
                if (enterpriseBillModel == null) {
                    log.error("enterpriseNotifyUrl未查询到数据库账单信息:{}", JsonUtil.object2Json(params));
                    SYS_PAY_LOGGER.error("enterpriseNotifyUrl未查询到数据库账单信息:{}", JsonUtil.object2Json(params));
                } else {
                    if (AlipayTradeStatusEnum.TRADE_SUCCESS.value().equals(tradeStatus) ||
                            AlipayTradeStatusEnum.TRADE_FINISHED.value().equals(tradeStatus)) {
                        if (!BillStatusEnum.VALID.equals(enterpriseBillModel.getStatus())) {
                            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                @Override
                                protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                                    String enterpriseBillId = enterpriseBillModel.getId();
                                    Long amount = enterpriseBillModel.getAmount();
                                    // 以状态作为版本控制，使并发安全，避免重复增加金额
                                    int effectRow = enterpriseBillMapper.updateBalanceAndStatusByBillId(enterpriseBillId, amount, BillStatusEnum.VALID,
                                            tradeStatus, tradeNo, DateUtil.currentTimeMillis());
                                    log.info("账单{}-企业余额更新完成影响行数:{}",enterpriseBillId, effectRow);
                                }
                            });
                        }
                    } else {//理论上不会存在这个情况，文档说只有变为TRADE_SUCCESS一种状态才会回调
                        log.error("enterpriseNotifyUrl-人工处理（收到支付宝通知但不是TRADE_SUCCESS或TRADE_FINISHED):{}", JsonUtil.object2Json(params));
                        SYS_PAY_LOGGER.error("enterpriseNotifyUrl-人工处理（收到支付宝通知但不是TRADE_SUCCESS或TRADE_FINISHED):{}", JsonUtil.object2Json(params));
                        enterpriseBillMapper.updateStatusById(enterpriseBillModel.getId(), BillStatusEnum.MANUAL_PROCESSING
                                , tradeStatus, tradeNo, DateUtil.currentTimeMillis(), 0L);
                    }
                }
                return "success";
            } else {
                log.info("enterpriseNotifyUrl支付宝异步回调验签失败，请留意");
                //验签失败该接口被别人调用
                return "支付宝异步回调验签失败，请留意";
            }
        } catch (Exception e) {
            log.error("enterpriseNotifyUrl发生异常:", e);
            return "支付宝异步回调验签失败，请留意";
        }
    }

    public void enterpriseReturnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String enterpriseFrontendUrl = aliPayConfig.getEnterpriseFrontendUrl();
        try {
            log.info("enterpriseReturnUrl接收到支付回调信息");
            //获取支付宝公钥
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.error("enterpriseReturnUrl验签成功");
                //
                response.sendRedirect(enterpriseFrontendUrl);
            } else {
                log.error("验签失败，请联系管理员解决");
                response.sendRedirect(enterpriseFrontendUrl);
            }
        } catch (Exception e) {
            log.error("enterpriseReturnUrl操作发生异常：", e);
            response.sendRedirect(enterpriseFrontendUrl);
        }
    }

    public I18nResult<String> clientPay(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            String registerPublishId = aliPayFaceToFaceModel.getRegisterPublishId();
            String comment = aliPayFaceToFaceModel.getComment();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            String loginUsername = SecurityUtil.getLoginUsername();
            if (StringUtils.isAnyEmpty(enterpriseId, registerPublishId)) {
                return result.failedBadRequest().setMessage("缺少必要参数！");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            if (registerPublishModel == null || registerPublishModel.getCost() == null || registerPublishModel.getCost() == 0 ||
                    !Boolean.TRUE.equals(registerPublishModel.getPayFlag())) {
                return result.failedBadRequest().setMessage("支付信息不存在");
            }
            Long numAmount = registerPublishModel.getCost();
            RegisterBillEditParam editParam = new RegisterBillEditParam();
            String uniqueId = UniqueUtil.getUniqueId();
            editParam.setId(uniqueId);
            editParam.setEnterpriseId(enterpriseId);
            editParam.setRegisterPublishId(registerPublishId);
            editParam.setStatus(BillStatusEnum.INIT);
            editParam.setAmount(numAmount);
            editParam.setSubject("用户缴费");
            editParam.setComment(comment);
            editParam.setSystemComment("系统备注");
            TimeAfterMinutesDTO minutesDTO = DateUtil.getTimeDTOAfterMinutes(10);
            String alipayTimeExpire = minutesDTO.getFormat();
            Long alipayTimeExpireAt = minutesDTO.getTimestamp();
            editParam.setAlipayTimeExpire(alipayTimeExpire);
            editParam.setAlipayTimeExpireAt(alipayTimeExpireAt);
            editParam.setCreateBy(loginUsername);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            AlipayClient alipayClient = getPlatformAlipayClient();
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(aliPayConfig.getClientNotifyUrl());
            request.setReturnUrl(aliPayConfig.getClientReturnUrl());
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();//设置业务参数
            model.setOutTradeNo(uniqueId);//商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001
            model.setSubject("用户缴费");//订单标题
            String totalAmount = StringUtil.amountLong2String(numAmount);
            model.setTotalAmount(totalAmount);//订单金额，精确到小数点后两位
            Map<String, String> body = new HashMap<>();
            body.put("enterpriseId", enterpriseId);
            body.put("registerPublishId", registerPublishId);
            body.put("totalAmount", totalAmount);
            body.put("alipayTimeExpire", alipayTimeExpire);
            body.put("outTradeNo", uniqueId);
            body.put("subject", "用户缴费");
            String bodyJson = JsonUtil.object2Json(body);
            model.setBody(bodyJson);//订单描述
            model.setPassbackParams(bodyJson);
            model.setProductCode(ALIPAY_PRODUCT_CODE);
            model.setTimeExpire(alipayTimeExpire);
            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                registerBillMapper.insert(editParam);
                return result.succeed().setData(response.getBody());
            } else {
                return result.failedBadRequest().setMessage("获取支付宝支付表单失败，错误信息：" + response.getSubMsg());
            }
        } catch (Exception e) {
            log.error("企业充值失败:", e);
            return result.failed().setMessage("下单失败");
        }
    }

    public String clientNotifyUrl(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> requestParams = request.getParameterMap();
        log.info("clientNotifyUrl-requestParams参数：{}", JsonUtil.object2Json(requestParams));
        try {
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipayPublicKey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.info("clientNotifyUrl验签成功");
                String outTradeNo = params.get("out_trade_no");
                String tradeStatus = params.get("trade_status");
                String tradeNo = params.get("trade_no");
                RegisterBillModel billModel = registerBillMapper.queryOneById(outTradeNo);
                if (billModel == null) {
                    log.error("clientNotifyUrl未查询到数据库账单信息:{}", JsonUtil.object2Json(params));
                    SYS_PAY_LOGGER.error("clientNotifyUrl未查询到数据库账单信息:{}", JsonUtil.object2Json(params));
                } else {
                    if (AlipayTradeStatusEnum.TRADE_SUCCESS.value().equals(tradeStatus) ||
                            AlipayTradeStatusEnum.TRADE_FINISHED.value().equals(tradeStatus)) {
                        if (!BillStatusEnum.VALID.equals(billModel.getStatus())) {
                            String billModelId = billModel.getId();
                            String registerPublishId = billModel.getRegisterPublishId();
                            String createBy = billModel.getCreateBy();
                            moreTransactionService.updateClientRegisterBill(billModelId, BillStatusEnum.VALID,
                                    tradeStatus, tradeNo, DateUtil.currentTimeMillis(), registerPublishId, createBy);
                        }
                    } else {//理论上不会存在这个情况，文档说只有变为TRADE_SUCCESS一种状态才会回调
                        log.error("clientNotifyUrl-人工处理（收到支付宝通知但不是TRADE_SUCCESS或TRADE_FINISHED):{}", JsonUtil.object2Json(params));
                        SYS_PAY_LOGGER.error("clientNotifyUrl-人工处理（收到支付宝通知但不是TRADE_SUCCESS或TRADE_FINISHED):{}", JsonUtil.object2Json(params));
                        registerBillMapper.updateStatusByBillId(billModel.getId(), BillStatusEnum.MANUAL_PROCESSING
                                , tradeStatus, tradeNo, DateUtil.currentTimeMillis());
                    }
                }
                return "success";
            } else {
                log.info("clientNotifyUrl支付宝异步回调验签失败，请留意");
                //验签失败该接口被别人调用
                return "支付宝异步回调验签失败，请留意";
            }
        } catch (Exception e) {
            log.error("clientNotifyUrl发生异常:", e);
            return "支付宝异步回调验签失败，请留意";
        }
    }

    public void clientReturnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientFrontendUrl = aliPayConfig.getClientFrontendUrl();
        try {
            log.info("clientReturnUrl接收到支付回调信息");
            //获取支付宝公钥
            String aliPayPublicKey = aliPayConfig.getAlipayPublicKey();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
            if (flag) {
                log.error("clientReturnUrl验签成功");
                //
                response.sendRedirect(clientFrontendUrl);
            } else {
                log.error("验签失败，请联系管理员解决");
                response.sendRedirect(clientFrontendUrl);
            }
        } catch (Exception e) {
            log.error("clientReturnUrl操作发生异常：", e);
            response.sendRedirect(clientFrontendUrl);
        }
    }
}
