package top.gaogle.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import top.gaogle.common.RegisterConst;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.slave.DynamicRegisterInfoMapper;
import top.gaogle.framework.config.MinioConfig;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.util.*;
import top.gaogle.pojo.dto.DynamicRegisterInfoExcelDTO;
import top.gaogle.pojo.dto.ScoreTemplateExcelDTO;
import top.gaogle.pojo.enums.DynamicRegisterInfoApproveEnum;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.pojo.model.DynamicRegisterInfoModel;
import top.gaogle.pojo.model.RegisterPublishModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static top.gaogle.common.RegisterConst.REGISTER_INFO_TABLE_NAME;

@Service
public class FileService extends SuperService {
    @Value("${minio.bucket-name-public}")
    private String bucketName;

    private final MinioUtil minioUtil;
    private final DynamicRegisterInfoMapper dynamicRegisterInfoMapper;
    private final RegisterPublishMapper registerPublishMapper;
    private final MinioConfig minioConfig;

    @Autowired
    public FileService(MinioUtil minioUtil, DynamicRegisterInfoMapper dynamicRegisterInfoMapper, RegisterPublishMapper registerPublishMapper, MinioConfig minioConfig) {
        this.minioUtil = minioUtil;
        this.dynamicRegisterInfoMapper = dynamicRegisterInfoMapper;
        this.registerPublishMapper = registerPublishMapper;
        this.minioConfig = minioConfig;
    }

    public I18nResult<String> upload(MultipartFile file) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            String filename = file.getOriginalFilename();
            assert filename != null;
            String type = filename.substring(filename.lastIndexOf("."));
            String objectName = UniqueUtil.getUniqueId() + type;
            minioUtil.putObject(bucketName, file, objectName, type);
            result.succeed().setData(objectName);
        } catch (Exception e) {
            log.error("文件上传发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "文件上传发生异常");
        }
        return result;

    }

    public I18nResult<String> getObjectUrl(String objectName) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            String objectUrl = minioUtil.getExpiryObjectUrl(bucketName, objectName, 1, TimeUnit.HOURS, "picture");
            result.succeed().setData(objectUrl);
        } catch (Exception e) {
            log.error("图片上传发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "图片上传发生异常");
        }
        return result;
    }

    public I18nResult<String> uploadPicture(MultipartFile file) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            String magicNumberType = FileUtil.getFileTypeByMagicNumber(file);
            if (!("jpg".equals(magicNumberType) || "png".equals(magicNumberType))) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "仅支持上传jpg、png格式文件");
            }
            String minioFileType = FileUtil.getMinioFileType(magicNumberType);
            String objectName = UniqueUtil.getUniqueId() + RegisterConst.DOT + magicNumberType;
            minioUtil.putObject(bucketName, file, objectName, minioFileType, "picture");
            result.succeed().setData(objectName);
        } catch (Exception e) {
            log.error("文件上传发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "文件上传发生异常");
        }
        return result;
    }

    public void obtainAdmissionTicket(HttpServletResponse response, String registerPublishId) {
        try {
            if (StringUtils.isAnyEmpty(registerPublishId)) {
                log.info("缺失必要参数");
                return;
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            Long timeMillis = DateUtil.currentTimeMillis();
            if (registerPublishModel == null || timeMillis < registerPublishModel.getTicketStartAt() || timeMillis > registerPublishModel.getTicketEndAt()) {
                log.info("考试不存在或不在打印准考证时间范围内!");
                return;
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            String loginUsername = SecurityUtil.getLoginUsername();
            DynamicRegisterInfoModel dynamicRegisterInfoModel = dynamicRegisterInfoMapper.obtainAdmissionTicket(tableName, loginUsername);
            if (dynamicRegisterInfoModel == null) {
                log.info("未查询到考生信息！");
                return;
            }
            String pdfFileName = "准考证";
            String noticeHtml = "<p>1. 凭纸质版准考证、本人有效居民身份证原件（缺一不可）进入考场，在座次表上签到后，对号入座，并将两证放在桌子边角。</p>" +
                    "<p>2. 考试时须携带黑色字迹的钢笔或签字笔、2B铅笔、橡皮。严禁携带任何书籍、资料、草稿纸及手机、计算器、智能手表、智能手环、蓝牙耳机等通信、计算、存储设备；已带入的，应存放在指定位置，电子产品应切断电源。考试期间，凡发现应试人员随身携带违禁物品或带至座位的，一律按违纪处理。</p>" +
                    "<p>3. 考试开始和结束时间以考试铃声为准，个人计时工具及考场内钟表时间仅作参考。</p>" +
                    "<p>4. 考试实行全场封闭，开考 30 分钟后不得入场；考试结束前不得提前交卷出场。</p>" +
                    "<p>5. 考试结束铃响，应立即停止答题，将试卷、答题卡分别反面向上放在桌子上，并举起右手。监考人员回收试卷、答题卡时，应试人员须在座次表上履行交卷签字程序。经监考人员允许后，应试人员方可离开考场，不得将试卷、题卡、草稿纸带出或传出考场。考试配发的草稿纸，考后统一收回。</p>" +
                    "<p>6. 考试期间，不得损毁试卷，不得以任何形式抄录试题、答案及相关内容。</p>" +
                    "<p>7. 考试期间，应试人员有义务保护好自己的试卷和答题信息，不被他人抄袭。阅卷过程中将采用技术手段对答卷进行雷同检测，对被甄别为雷同的，将给予考试成绩无效处理。</p>" +
                    "<p>8. 自觉遵守“考场规则”，如有违纪违规行为，将严肃处理。</p>" +
                    "<p>9. 考试期间出行人数较多，可能造成考点周围交通拥堵，请于考试前熟悉考点地址和交通路线。考生进入考点考场前，须接受身份核验等，建议提前 1 小时到达考点以免影响考试。</p>" +
                    "<p>10. 请妥善保管准考证，以备成绩查询和面试时使用。成绩公布后考生按指定网站查询。</p>";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "filename=" + new String((pdfFileName + ".pdf").getBytes(), "iso8859-1"));
            OutputStream os = response.getOutputStream();
            //构造模板引擎
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            //模板所在目录，相对于当前classloader的classpath
            resolver.setPrefix("templates/");
            //模板文件后缀
            resolver.setSuffix(".html");
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(resolver);
            //构造上下文(Model)
            Context context = new Context();
            context.setVariable("registerPublishTitle", registerPublishModel.getTitle());
            context.setVariable("name", dynamicRegisterInfoModel.getName());
            context.setVariable("registerNumber", dynamicRegisterInfoModel.getAdmissionTicketNumber());
            context.setVariable("idNumber", dynamicRegisterInfoModel.getIdNumber());
            context.setVariable("spot", dynamicRegisterInfoModel.getSpot());
            context.setVariable("address", dynamicRegisterInfoModel.getSpotAddress());
            context.setVariable("roomNumber", dynamicRegisterInfoModel.getRoomNumber());
            context.setVariable("seatNumber", dynamicRegisterInfoModel.getSeatNumber());
            Long activityStartAt = registerPublishModel.getActivityStartAt();
            Long activityEndAt = registerPublishModel.getActivityEndAt();
            String date = DateUtil.timeMillisFormatter(activityStartAt, "yyyy-MM-dd");
            String startTime = DateUtil.timeMillisFormatter(activityStartAt, "HH:mm");
            String endTime = DateUtil.timeMillisFormatter(activityEndAt, "HH:mm");

            context.setVariable("date", date);
            context.setVariable("time", startTime + " - " + endTime);
            context.setVariable("noticeHtml", noticeHtml);
            //渲染模板
            String example = templateEngine.process("admissionTicket", context);
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFont(ResourceUtils.getFile("classpath:fonts/simsun.ttf"), "simsun");
            builder.useFastMode();
            builder.withHtmlContent(example, ResourceUtils.getURL("classpath:static/images/pdf/").toString());
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            log.error("文件上传发生异常:", e);
        }

    }

    public void exportRegisterInfo(HttpServletResponse response, String registerPublishId) {
        try {
            // 设置响应的内容类型为 Excel 格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            // 设置文件名并进行 URL 编码
            String fileName = URLEncoder.encode("dynamicHeadWrite" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            // 定义动态表头
            List<List<String>> headList = new ArrayList<>();
            List<String> head0 = new ArrayList<>();
            head0.add("字符串" + System.currentTimeMillis());
            List<String> head1 = new ArrayList<>();
            head1.add("数字" + System.currentTimeMillis());
            List<String> head2 = new ArrayList<>();
            head2.add("图片" + System.currentTimeMillis());
            headList.add(head0);
            headList.add(head1);
            headList.add(head2);

            // 定义数据
            List<List<Object>> data = new ArrayList<>();
            List<Object> row = new ArrayList<>();
            row.add("geg");
            row.add("gao");

            row.add(new URL("http://82.157.42.25:9001/style-register-public/picture/1814866032196718592.png"));
            data.add(row);

            // 使用 EasyExcel 将数据写入响应的输出流
            EasyExcel.write(response.getOutputStream())
                    .head(headList)
                    .sheet("模板")
                    .doWrite(data);

        } catch (IOException e) {
            log.error("下载excel异常:", e);
        }
    }


    public void exportRegisterBaseInfo(HttpServletResponse response, String registerPublishId) {
        try {
            // 设置响应的内容类型为 Excel 格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            // 设置文件名并进行 URL 编码
            String fileName = URLEncoder.encode("dynamicHeadWrite" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<DynamicRegisterInfoModel> infoModels = dynamicRegisterInfoMapper.selectBaseInfoByRegisterPublishId(tableName, registerPublishId);
            List<DynamicRegisterInfoExcelDTO> excelDTOS = new ArrayList<>();
            for (DynamicRegisterInfoModel infoModel : infoModels) {
                DynamicRegisterInfoExcelDTO excelDTO = new DynamicRegisterInfoExcelDTO();
                excelDTO.setId(infoModel.getId());
                excelDTO.setName(infoModel.getName());
                excelDTO.setIdNumber(infoModel.getIdNumber());
                excelDTO.setAdmissionTicketNumber(infoModel.getAdmissionTicketNumber());
                excelDTO.setPhoto(new URL(minioConfig.getEndpoint() + ":" + minioConfig.getPort() + "/" +
                        minioConfig.getBucketNamePublic() + "/picture/" + infoModel.getPhoto()));
                excelDTO.setPhoneNumber(infoModel.getPhoneNumber());
                excelDTO.setEmail(infoModel.getEmail());
                excelDTO.setSpot(infoModel.getSpot());
                excelDTO.setSpotAddress(infoModel.getSpotAddress());
                excelDTO.setRoomNumber(infoModel.getRoomNumber());
                excelDTO.setSeatNumber(infoModel.getSeatNumber());
                excelDTO.setGender(infoModel.getGender());
                excelDTO.setEducation(infoModel.getEducation());
                excelDTO.setMajor(infoModel.getMajor());
                excelDTO.setScore(infoModel.getScore());
                excelDTO.setStatus(infoModel.getStatus());
                excelDTO.setApprove(DynamicRegisterInfoApproveEnum.fromValue(infoModel.getApprove()).title());
                excelDTO.setReason(infoModel.getReason());
                excelDTO.setCreateBy(infoModel.getCreateBy());
                excelDTO.setCreateAt(DateUtil.timeMillisFormatter(infoModel.getCreateAt(), "yyyy-MM-dd HH:mm:ss"));
                excelDTO.setUpdateBy(infoModel.getUpdateBy());
                excelDTO.setUpdateAt(DateUtil.timeMillisFormatter(infoModel.getUpdateAt(), "yyyy-MM-dd HH:mm:ss"));
                excelDTOS.add(excelDTO);
            }
            // 使用 EasyExcel 将数据写入响应的输出流
            EasyExcelFactory.write(response.getOutputStream())
                    .head(DynamicRegisterInfoExcelDTO.class)
                    .sheet("模板")
                    .doWrite(excelDTOS);

        } catch (IOException e) {
            log.error("下载excel异常:", e);
        }
    }

    public void exportScoreTemplate(HttpServletResponse response, String registerPublishId) {
        try {
            // 设置响应的内容类型为 Excel 格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            // 设置文件名并进行 URL 编码
            String fileName = URLEncoder.encode("dynamicHeadWrite" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<DynamicRegisterInfoModel> infoModels = dynamicRegisterInfoMapper.selectBaseInfoByRegisterPublishId(tableName, registerPublishId);
            List<ScoreTemplateExcelDTO> excelDTOS = new ArrayList<>();
            for (DynamicRegisterInfoModel infoModel : infoModels) {
                ScoreTemplateExcelDTO excelDTO = new ScoreTemplateExcelDTO();
                excelDTO.setId(infoModel.getId());
                excelDTO.setName(infoModel.getName());
                excelDTO.setIdNumber(infoModel.getIdNumber());
                excelDTO.setAdmissionTicketNumber(infoModel.getAdmissionTicketNumber());
                excelDTOS.add(excelDTO);
            }
            // 使用 EasyExcel 将数据写入响应的输出流
            EasyExcelFactory.write(response.getOutputStream())
                    .head(ScoreTemplateExcelDTO.class)
                    .sheet("模板")
                    .doWrite(excelDTOS);

        } catch (IOException e) {
            log.error("下载excel异常:", e);
        }
    }


    public I18nResult<Boolean> uploadScoreTemplate(MultipartFile file, String registerPublishId) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try (InputStream inputStream = file.getInputStream()) {  // 使用 try-with-resources 确保流关闭
            // 使用 EasyExcel 读取上传的 Excel 文件
            List<ScoreTemplateExcelDTO> scoreTemplates = EasyExcelFactory.read(inputStream)
                    .head(ScoreTemplateExcelDTO.class)
                    .sheet()
                    .doReadSync();

            // 遍历读取到的数据
            for (ScoreTemplateExcelDTO scoreTemplate : scoreTemplates) {
                String id = scoreTemplate.getId();
                String name = scoreTemplate.getName();
                String idNumber = scoreTemplate.getIdNumber();
                String admissionTicketNumber = scoreTemplate.getAdmissionTicketNumber();
                String score = scoreTemplate.getScore();
                if (StringUtils.isAnyEmpty(id, name, idNumber, admissionTicketNumber, score)) {
                    return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(id + "-" + name + "缺失必要参数");
                }
                String loginUsername = SecurityUtil.getLoginUsername();
                Long timeMillis = DateUtil.currentTimeMillis();
                String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
                dynamicRegisterInfoMapper.updateScoreByUnicode(tableName, score, loginUsername, timeMillis, id, idNumber, admissionTicketNumber);
            }
            result.succeed().setData(true);
        } catch (IOException e) {
            log.error("导入成绩发生异常:", e);
            result.failed().setData(false);
        }
        return result;
    }
}
