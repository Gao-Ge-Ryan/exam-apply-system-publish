package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.model.RegisterPublishAnnouncementModel;
import top.gaogle.pojo.param.RegisterPublishAnnouncementEditParam;
import top.gaogle.pojo.param.RegisterPublishAnnouncementQueryParam;
import top.gaogle.service.RegisterPublishAnnouncementService;

/**
 * 报名发布公告
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/register_publish_announcement")
public class RegisterPublishAnnouncementController {
    private final RegisterPublishAnnouncementService registerPublishAnnouncementService;

    @Autowired
    public RegisterPublishAnnouncementController(RegisterPublishAnnouncementService registerPublishAnnouncementService) {
        this.registerPublishAnnouncementService = registerPublishAnnouncementService;
    }

    /**
     * 新增
     */
    @PostMapping
    public ResponseEntity<I18nResult<Boolean>> add(@RequestBody RegisterPublishAnnouncementEditParam editParam) {
        return registerPublishAnnouncementService.add(editParam).toResponseEntity();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<RegisterPublishAnnouncementModel>>> queryByPageAndCondition(RegisterPublishAnnouncementQueryParam queryParam) {
        return registerPublishAnnouncementService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 修改
     */
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> put(@RequestBody RegisterPublishAnnouncementEditParam editParam) {
        return registerPublishAnnouncementService.put(editParam).toResponseEntity();
    }

    /**
     * 根据id查询详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<I18nResult<RegisterPublishAnnouncementModel>> queryOneById(@PathVariable("id") String id) {
        return registerPublishAnnouncementService.queryOneById(id).toResponseEntity();
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<I18nResult<Boolean>> deleteById(@PathVariable("id") String id) {
        return registerPublishAnnouncementService.deleteById(id).toResponseEntity();
    }

}
