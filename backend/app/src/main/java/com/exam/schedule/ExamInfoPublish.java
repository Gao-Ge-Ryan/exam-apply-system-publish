package com.exam.schedule;

import com.exam.common.Utils.DateUtil;
import com.exam.common.Utils.IDGenerator;
import com.exam.common.enums.impl.ExamStatusEnum;
import com.exam.common.enums.impl.InfoTypeEnum;
import com.exam.dao.ExamDao;
import com.exam.dao.InfoDao;
import com.exam.pojo.entity.Info;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.param.ExamParam;
import com.exam.pojo.param.InfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务
 */
@Component
public class ExamInfoPublish {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExamDao examDao;
    @Autowired
    private InfoDao infoDao;

    /**
     * 编写定时任务发布信息
     */
    @Scheduled(cron = "0/10 * *  * * ?")
    public void infoPublish() {
        ExamParam examParam = new ExamParam();
        List<ExamModel> examModels = examDao.queryAllByLimit(examParam);
        long currentTimeMillis = DateUtil.getCurrentTimeMillis();
        logger.info("查询出考试信息", examModels);
        for (ExamModel examModel : examModels) {
            // 发布报名信息
            if (examModel.getApplyStartTime() <= currentTimeMillis && examModel.getApplyEndTime() >= currentTimeMillis) {
                InfoParam infoParam = new InfoParam();
                infoParam.setId(IDGenerator.StringID());
                infoParam.setType(InfoTypeEnum.Registration_Time_Announcement);
                infoParam.setTitle(examModel.getTitle());
                infoParam.setContent(examModel.getDescription());
                infoParam.setExamId(examModel.getId());
                Info info = infoDao.selectByExamIdAndType(examModel.getId(), InfoTypeEnum.Registration_Time_Announcement.getCode());
                examParam.setStatus(ExamStatusEnum.Start);
                examParam.setId(examModel.getId());
                examDao.update(examParam);
                if (info == null) {
                    infoParam.setCreateTime(DateUtil.getCurrentTimeMillis());
                    infoDao.insert(infoParam);
                    logger.info("发布报名信息", examModel);

                }

            }
            // 发布打印准考证信息
            if (examModel.getApplyEndTime() <= currentTimeMillis && examModel.getStartTime() >= currentTimeMillis) {
                InfoParam infoParam = new InfoParam();
                infoParam.setId(IDGenerator.StringID());
                infoParam.setType(InfoTypeEnum.Notice_of_Examination_Time);
                infoParam.setTitle(examModel.getTitle());
                infoParam.setContent(examModel.getDescription());
                infoParam.setExamId(examModel.getId());
                Info info = infoDao.selectByExamIdAndType(examModel.getId(), InfoTypeEnum.Notice_of_Examination_Time.getCode());
                examParam.setStatus(ExamStatusEnum.Stop);
                examParam.setId(examModel.getId());
                examDao.update(examParam);
                if (info == null) {
                    infoParam.setCreateTime(DateUtil.getCurrentTimeMillis());
                    infoDao.insert(infoParam);
                    logger.info("发布打印准考证信息信息", examModel);
                }
            }
            // 成绩查询公告
            if (examModel.getEndTime() <= currentTimeMillis) {
                InfoParam infoParam = new InfoParam();
                infoParam.setId(IDGenerator.StringID());
                infoParam.setType(InfoTypeEnum.Examination_Announcement);
                infoParam.setTitle(examModel.getTitle());
                infoParam.setContent(examModel.getDescription());
                infoParam.setExamId(examModel.getId());
                Info info = infoDao.selectByExamIdAndType(examModel.getId(), InfoTypeEnum.Examination_Announcement.getCode());
                examParam.setStatus(ExamStatusEnum.Score_Inquiry);
                examParam.setId(examModel.getId());
                examDao.update(examParam);
                if (info == null) {
                    infoParam.setCreateTime(DateUtil.getCurrentTimeMillis());
                    infoDao.insert(infoParam);
                    logger.info("发布成绩查询信息", examModel);
                }
            }
        }

    }


}
