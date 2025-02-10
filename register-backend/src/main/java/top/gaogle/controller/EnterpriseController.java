package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.annotation.RateLimiter;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.enums.EnterpriseStatusEnum;
import top.gaogle.pojo.enums.LimitType;
import top.gaogle.pojo.model.EnterpriseModel;
import top.gaogle.pojo.model.TestEnumModel;
import top.gaogle.pojo.param.EnterpriseEditParam;
import top.gaogle.pojo.param.EnterpriseQueryParam;
import top.gaogle.service.EnterpriseService;

/**
 * 企业管理
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }


    /**
     * 申请企业
     */
    @PostMapping("/apply")
    public ResponseEntity<I18nResult<Boolean>> apply(@RequestBody EnterpriseEditParam editParam) {
        return enterpriseService.apply(editParam).toResponseEntity();
    }

    /**
     * 审批企业
     */
    @PutMapping("/approve/{enterpriseId}")
    public ResponseEntity<I18nResult<Boolean>> approve(EnterpriseStatusEnum statusEnum, @PathVariable("enterpriseId") String enterpriseId) {
        return enterpriseService.approve(statusEnum, enterpriseId).toResponseEntity();
    }

    /**
     * 添加企业
     */
    @PreAuthorize("hasAuthority('enterprise_insert')")
    @PostMapping
    public ResponseEntity<I18nResult<Boolean>> insert(@RequestBody EnterpriseEditParam editParam) {
        return enterpriseService.insert(editParam).toResponseEntity();
    }

    /**
     * 修改企业
     */
    @PreAuthorize("hasAuthority('enterprise_put')")
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> putEnterprise(@RequestBody EnterpriseEditParam editParam) {
        return enterpriseService.putEnterprise(editParam).toResponseEntity();
    }

    /**
     * 修改企业信息（企业端）
     */
    @PutMapping("/enterprise_put")
    public ResponseEntity<I18nResult<Boolean>> enterprisePutEnterprise(@RequestBody EnterpriseEditParam editParam) {
        return enterpriseService.enterprisePutEnterprise(editParam).toResponseEntity();
    }

    /**
     * 查询企业信息（用户端）
     */
    @GetMapping("/client_query")
    public ResponseEntity<I18nResult<EnterpriseModel>> clientQueryEnterprise(@RequestParam String enterpriseId) {
        return enterpriseService.clientQueryEnterprise(enterpriseId).toResponseEntity();
    }

    /**
     * 查询企业信息（企业端）
     */
    @GetMapping("/enterprise_query")
    public ResponseEntity<I18nResult<EnterpriseModel>> enterpriseQueryEnterprise() {
        return enterpriseService.enterpriseQueryEnterprise().toResponseEntity();
    }

    /**
     * 获取企业余额（企业端）
     */
    @GetMapping("/enterprise_query_balance")
    public ResponseEntity<I18nResult<String>> enterpriseQueryEnterpriseBalance() {
        return enterpriseService.enterpriseQueryEnterpriseBalance().toResponseEntity();
    }

    /**
     * 分页查询企业（客户端）
     */
    @Anonymous
    @GetMapping("/client_query_page")
    public ResponseEntity<I18nResult<PageModel<EnterpriseModel>>> clientQueryByPage(EnterpriseQueryParam queryParam) {
        return enterpriseService.clientQueryByPage(queryParam).toResponseEntity();
    }

    /**
     * 分页条件查询
     */
    @PreAuthorize("hasAuthority('enterprise_view')")
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<EnterpriseModel>>> queryByPageAndCondition(EnterpriseQueryParam queryParam) {
        return enterpriseService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 根据id查询详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<I18nResult<EnterpriseModel>> queryOneById(@PathVariable("id") String id) {
        return enterpriseService.queryOneById(id).toResponseEntity();
    }

    /**
     * 根据id删除企业
     */
    @PreAuthorize("hasAuthority('enterprise_view')")
    @DeleteMapping("/{id}")
    public ResponseEntity<I18nResult<Boolean>> deleteById(@PathVariable("id") String id) {
        return enterpriseService.deleteById(id).toResponseEntity();
    }

    /**
     * 测试数据
     */
    @Anonymous
    @RateLimiter(time = 60,count=5,limitType = LimitType.IP)
    @GetMapping("/test_data")
    public ResponseEntity<I18nResult<TestEnumModel>> testData() {
        return enterpriseService.testData().toResponseEntity();
    }

}
