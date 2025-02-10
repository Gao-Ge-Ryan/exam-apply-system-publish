package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.dto.BindSpotDTO;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.pojo.param.RegisterPublishEditParam;
import top.gaogle.pojo.param.RegisterPublishQueryParam;
import top.gaogle.service.RegisterPublishService;

/**
 * 报名发布
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/register_publish")
public class RegisterPublishController {
    private final RegisterPublishService registerPublishService;

    @Autowired
    public RegisterPublishController(RegisterPublishService registerPublishService) {
        this.registerPublishService = registerPublishService;
    }

    /**
     * 新增
     */
    @PostMapping
    public ResponseEntity<I18nResult<Boolean>> add(@RequestBody RegisterPublishEditParam editParam) {
        return registerPublishService.add(editParam).toResponseEntity();
    }

    /**
     * 分页条件查询企业发布得考试
     */
    @GetMapping("/enterprise/page")
    public ResponseEntity<I18nResult<PageModel<RegisterPublishModel>>> enterpriseQueryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        return registerPublishService.enterpriseQueryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 分页条件查询企业发布得考试(客户端)
     */
    @Anonymous
    @GetMapping("/client/page")
    public ResponseEntity<I18nResult<PageModel<RegisterPublishModel>>> clientQueryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        return registerPublishService.clientQueryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 修改
     */
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> put(@RequestBody RegisterPublishEditParam editParam) {
        return registerPublishService.put(editParam).toResponseEntity();
    }

    /**
     * 绑定考点信息
     */
    @PutMapping("bind_spot")
    public ResponseEntity<I18nResult<Boolean>> bindSpot(@RequestBody BindSpotDTO bindSpotDTO) {
        return registerPublishService.bindSpot(bindSpotDTO).toResponseEntity();
    }

    /**
     * 解绑考点信息
     */
    @PutMapping("unbind_spot")
    public ResponseEntity<I18nResult<Boolean>> unbindSpot(@RequestBody BindSpotDTO bindSpotDTO) {
        return registerPublishService.unbindSpot(bindSpotDTO).toResponseEntity();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<RegisterPublishModel>>> queryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        return registerPublishService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 根据id查询详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<I18nResult<RegisterPublishModel>> queryOneById(@PathVariable("id") String id) {
        return registerPublishService.queryOneById(id).toResponseEntity();
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<I18nResult<Boolean>> deleteById(@PathVariable("id") String id) {
        return registerPublishService.deleteById(id).toResponseEntity();
    }
}
