package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.enums.impl.ExamStatusEnum;
import com.exam.common.enums.impl.ExamTypeEnum;
import com.exam.dao.ExamDao;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.model.DashboardModel;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.model.ExamUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private ExamUserDao examUserDao;


    @GetMapping("/exam")
    public Result examInfo() {
        HashMap<String, HashMap<String, Integer>> bigMap = new HashMap<>();
        HashMap<String, Integer> statusInfoMap = new HashMap<>();
        HashMap<String, Integer> typeInfoMap = new HashMap<>();
        List<ExamModel> examModels = examDao.queryAll();
        List<ExamModel> NotStarted = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamStatusEnum.NotStarted)).collect(Collectors.toList());
        List<ExamModel> Start = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamStatusEnum.Start)).collect(Collectors.toList());
        List<ExamModel> Stop = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamStatusEnum.Stop)).collect(Collectors.toList());
        List<ExamModel> Score_Inquiry = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamStatusEnum.Score_Inquiry)).collect(Collectors.toList());
        List<ExamModel> Mandarin = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Mandarin)).collect(Collectors.toList());
        List<ExamModel> Cet_Band_4 = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Cet_Band_4)).collect(Collectors.toList());
        List<ExamModel> Cet_Band_6 = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Cet_Band_6)).collect(Collectors.toList());
        List<ExamModel> Accounting_Exam = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Accounting_Exam)).collect(Collectors.toList());
        List<ExamModel> Computer_Rank_Examination = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Computer_Rank_Examination)).collect(Collectors.toList());
        List<ExamModel> Teacher_Qualification_Examination = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Teacher_Qualification_Examination)).collect(Collectors.toList());
        List<ExamModel> Test_Of_Spoken_English = examModels.stream().filter(n -> n.getExamType().getEnumCode().equals(ExamTypeEnum.Test_Of_Spoken_English)).collect(Collectors.toList());
        List<ExamModel> Other_Test = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamTypeEnum.Other_Test)).collect(Collectors.toList());
        statusInfoMap.put("NotStarted",NotStarted.size());
        statusInfoMap.put("Start",Start.size());
        statusInfoMap.put("Stop",Stop.size());
        statusInfoMap.put("Score_Inquiry",Score_Inquiry.size());


        typeInfoMap.put("Mandarin",Mandarin.size());
        typeInfoMap.put("Cet_Band_4",Cet_Band_4.size());
        typeInfoMap.put("Cet_Band_6",Cet_Band_6.size());
        typeInfoMap.put("Accounting_Exam",Accounting_Exam.size());
        typeInfoMap.put("Computer_Rank_Examination",Computer_Rank_Examination.size());
        typeInfoMap.put("Teacher_Qualification_Examination",Teacher_Qualification_Examination.size());
        typeInfoMap.put("Test_Of_Spoken_English",Test_Of_Spoken_English.size());
        typeInfoMap.put("Other_Test", Other_Test.size());
        bigMap.put("status",statusInfoMap);
        bigMap.put("type",typeInfoMap);
        return Result.ok(bigMap);}

    @GetMapping("/scoreInquiry")
    public Result getScore() {
        // 成绩监控

        List<ExamModel> examModels = examDao.queryAll();
        List<ExamModel> models = examModels.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamStatusEnum.Score_Inquiry)).collect(Collectors.toList());
        return Result.ok(models);
    }

    @GetMapping("/scoreInquiry/{examId}")
    public Result getScoreByExamId(@PathVariable("examId") String examId) {
        ArrayList<DashboardModel> dashboardModels = new ArrayList<>();
        List<ExamUserModel> examUserModelsNull = examUserDao.selectByExampleId(examId);
        List<ExamUserModel> examUserModels = examUserModelsNull.stream().filter(n ->! StringUtils.isEmpty(n.getScore())).collect(Collectors.toList());
        List<ExamUserModel> fail = examUserModels.stream().filter(n -> Integer.valueOf(n.getScore()) < 60).collect(Collectors.toList());
        List<ExamUserModel> pass = examUserModels.stream().filter(n -> Integer.valueOf(n.getScore()) >= 60 && Integer.valueOf(n.getScore()) < 70).collect(Collectors.toList());
        List<ExamUserModel> good = examUserModels.stream().filter(n -> Integer.valueOf(n.getScore()) >= 70 && Integer.valueOf(n.getScore()) < 90).collect(Collectors.toList());
        List<ExamUserModel> excellent = examUserModels.stream().filter(n -> Integer.valueOf(n.getScore()) >= 90).collect(Collectors.toList());

        HashMap<String, Integer> hashMap = new HashMap<>();
        DashboardModel fail1;
        if (fail == null || fail.size() == 0) {
            hashMap.put("fail", 0);
             fail1 = new DashboardModel(0, "不及格");
        } else {
            hashMap.put("fail", fail.size());
             fail1 = new DashboardModel(fail.size(), "不及格");
        }
        dashboardModels.add(fail1);
        DashboardModel pass1;
        if (pass == null || pass.size() == 0) {
            hashMap.put("pass", 0);
            pass1 = new DashboardModel(0, "及格");
        } else {
            hashMap.put("pass", pass.size());
            pass1 = new DashboardModel(pass.size(), "及格");
        }
        dashboardModels.add(pass1);
        DashboardModel goods1;
        if (good == null || good.size() == 0) {
            hashMap.put("good", 0);
            goods1 = new DashboardModel(0,"良好");
        } else {
            hashMap.put("good", good.size());
            goods1 = new DashboardModel(good.size(),"良好");
        }
        dashboardModels.add(goods1);
        DashboardModel excellent1;
        if (excellent == null || excellent.size() == 0) {
            hashMap.put("excellent", 0);
            excellent1 = new DashboardModel(0,"优秀");

        } else {
            hashMap.put("excellent", excellent.size());
            excellent1 = new DashboardModel(excellent.size(),"优秀");
        }
        dashboardModels.add(excellent1);

        return Result.ok(dashboardModels);
    }

}
