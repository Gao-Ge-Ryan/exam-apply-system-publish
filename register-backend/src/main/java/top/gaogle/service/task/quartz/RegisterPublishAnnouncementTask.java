package top.gaogle.service.task.quartz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.gaogle.common.RegisterConst;
import top.gaogle.dao.master.RegisterPublishAnnouncementMapper;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.slave.DynamicRegisterInfoMapper;
import top.gaogle.pojo.model.RegisterPublishAnnouncementModel;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.service.EmailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static top.gaogle.common.RegisterConst.REGISTER_INFO_TABLE_NAME;

/**
 * 定时任务 发布公告通知
 *
 * @author gaogle
 */
@Component("registerPublishAnnouncementTask")
public class RegisterPublishAnnouncementTask {

    @Value("${spring.mail.username}")
    public String platformMail;

    private final DynamicRegisterInfoMapper dynamicRegisterInfoMapper;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final RegisterPublishAnnouncementMapper registerPublishAnnouncementMapper;
    private final RegisterPublishMapper registerPublishMapper;

    public RegisterPublishAnnouncementTask(DynamicRegisterInfoMapper dynamicRegisterInfoMapper, EmailService emailService, TemplateEngine templateEngine, RegisterPublishAnnouncementMapper registerPublishAnnouncementMapper, RegisterPublishMapper registerPublishMapper) {
        this.dynamicRegisterInfoMapper = dynamicRegisterInfoMapper;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.registerPublishAnnouncementMapper = registerPublishAnnouncementMapper;
        this.registerPublishMapper = registerPublishMapper;
    }

    public void task(String registerPublishId, String announcementId) {
        String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
        List<String> fields = new ArrayList<>();
        List<Map<String, Object>> conditions = new ArrayList<>();
        fields.add(RegisterConst.COLUMN_CREATE_BY);
        RegisterPublishAnnouncementModel announcementModel = registerPublishAnnouncementMapper.queryOneById(announcementId);
        RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
        List<Map<String, Object>> selectDynamic = dynamicRegisterInfoMapper.selectDynamic(tableName, fields, conditions);
        if (!CollectionUtils.isEmpty(selectDynamic)) {
            for (Map<String, Object> objectMap : selectDynamic) {
                String email = objectMap.get(RegisterConst.COLUMN_CREATE_BY) != null ? objectMap.get(RegisterConst.COLUMN_CREATE_BY).toString() : "";
                if (StringUtils.isNotEmpty(email)) {
                    Context context = new Context();
                    String noticeHtml = announcementModel.getContent();
                    context.setVariable("title", announcementModel.getTitle());
                    context.setVariable("noticeHtml", noticeHtml);
                    String content = templateEngine.process("registerPublishAnnouncementTask.html", context);
                    emailService.send("Style-Register", platformMail, email, registerPublishModel.getTitle() + " 新公告提醒", content, true, null, null, null);
                }
            }
        }
    }

}
