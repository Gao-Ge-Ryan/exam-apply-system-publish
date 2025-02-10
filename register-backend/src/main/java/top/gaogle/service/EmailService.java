package top.gaogle.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.pojo.enums.HttpStatusEnum;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class EmailService extends SuperService {
    //注入邮件工具类
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public I18nResult<Boolean> send(String name, String form, String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            if (StringUtils.isAnyBlank(form, to, subject, content)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress(name + "<" + form + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, isHtml);
            //抄送
            if (!StringUtils.isEmpty(cc)) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (!StringUtils.isEmpty(bcc)) {
                messageHelper.setBcc(bcc.split(","));
            }
            //添加邮件附件
            if (!CollectionUtils.isEmpty(files)) {
                for (File file : files) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            log.error("发送邮件发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "发送邮件发生异常");
        }

        return result;
    }


    public void sendText(String form, String to, String subject, String content) {
        this.send("评论提醒", form, to, subject, content, false, null, null, null);
    }


    public void sendHtml(String form, String to, String subject, String content) {
        this.send("评论提醒", form, to, subject, content, true, null, null, null);
    }

}
