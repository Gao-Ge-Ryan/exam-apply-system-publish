package top.gaogle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.service.FileService;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件管理
 *
 * @author goge
 * @since 1.0.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public ResponseEntity<I18nResult<String>> upload(MultipartFile file) {
        return fileService.upload(file).toResponseEntity();
    }

    /**
     * 图片上传
     */
    @PostMapping("/upload/picture")
    public ResponseEntity<I18nResult<String>> uploadPicture(MultipartFile file) {
        return fileService.uploadPicture(file).toResponseEntity();
    }

    /**
     * 文件访问路径
     */
    @GetMapping("/object_url")
    public ResponseEntity<I18nResult<String>> getObjectUrl(@RequestParam String objectName) {
        return fileService.getObjectUrl(objectName).toResponseEntity();
    }

    /**
     * 获取准考证
     */
    @GetMapping("/obtain_admission_ticket")
    public void obtainAdmissionTicket(HttpServletResponse response, @RequestParam String registerPublishId) {
        fileService.obtainAdmissionTicket(response, registerPublishId);
    }

    /**
     * 导出报名ALL信息Excel
     */
    @Anonymous
    @GetMapping("/export_register_info")
    public void exportRegisterInfo(HttpServletResponse response, @RequestParam String registerPublishId) {
        fileService.exportRegisterInfo(response, registerPublishId);
    }

    /**
     * 导出报名基础信息Excel
     */
    @Anonymous
    @GetMapping("/export_register_base_info")
    public void exportRegisterBaseInfo(HttpServletResponse response, @RequestParam String registerPublishId) {
        fileService.exportRegisterBaseInfo(response, registerPublishId);
    }

    /**
     * 导出成绩模板
     */
    @Anonymous
    @GetMapping("/export_score_template")
    public void exportScoreTemplate(HttpServletResponse response, @RequestParam String registerPublishId) {
        fileService.exportScoreTemplate(response, registerPublishId);
    }

    /**
     * 导入成绩
     */
    @Anonymous
    @PostMapping("/upload_score_template")
    public ResponseEntity<I18nResult<Boolean>> uploadScoreTemplate(@RequestParam("file") MultipartFile file, @RequestParam String registerPublishId) {
        return fileService.uploadScoreTemplate(file, registerPublishId).toResponseEntity();
    }
}
