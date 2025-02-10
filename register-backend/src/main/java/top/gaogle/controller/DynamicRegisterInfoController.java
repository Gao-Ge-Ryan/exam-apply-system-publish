package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.param.DynamicRegisterInfoEditParam;
import top.gaogle.pojo.param.DynamicRegisterInfoQueryParam;
import top.gaogle.service.DynamicRegisterInfoService;

import java.util.Map;

/**
 * 报名信息
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dynamic_register_info")
public class DynamicRegisterInfoController {

    private final DynamicRegisterInfoService dynamicRegisterInfoService;

    @Autowired
    public DynamicRegisterInfoController(DynamicRegisterInfoService dynamicRegisterInfoService) {
        this.dynamicRegisterInfoService = dynamicRegisterInfoService;
    }

    /**
     * 审批通过(企业端)
     */
    @PutMapping("/approve")
    public ResponseEntity<I18nResult<Boolean>> approve(@RequestBody DynamicRegisterInfoEditParam editParam) {
        return dynamicRegisterInfoService.approve(editParam).toResponseEntity();
    }

    /**
     * 录入成绩(企业端)
     */
    @PutMapping("/input_score")
    public ResponseEntity<I18nResult<Boolean>> inputScore(@RequestBody DynamicRegisterInfoEditParam editParam) {
        return dynamicRegisterInfoService.inputScore(editParam).toResponseEntity();
    }

    /**
     * 审批不通过(企业端)
     */
    @PutMapping("/approve_not")
    public ResponseEntity<I18nResult<Boolean>> approveNot(@RequestBody DynamicRegisterInfoEditParam editParam) {
        return dynamicRegisterInfoService.approveNot(editParam).toResponseEntity();
    }

    /**
     * 分页条件查询(企业端)
     */
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<Map<String,Object>>>> queryByPageAndCondition(DynamicRegisterInfoQueryParam queryParam) {
        return dynamicRegisterInfoService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 报名申请（用户端）
     */
    @PostMapping("/client_apply_info")
    public ResponseEntity<I18nResult<Boolean>> clientApplyInfo(@RequestBody DynamicRegisterInfoEditParam editParam) {
        return dynamicRegisterInfoService.clientApplyInfo(editParam).toResponseEntity();
    }

    /**
     * 查询个人报名(用户端)
     */
    @GetMapping("/client_get_apply_info")
    public ResponseEntity<I18nResult<Map<String,Object>>> clientGetApplyInfo(DynamicRegisterInfoQueryParam queryParam) {
        return dynamicRegisterInfoService.clientGetApplyInfo(queryParam).toResponseEntity();
    }
}
