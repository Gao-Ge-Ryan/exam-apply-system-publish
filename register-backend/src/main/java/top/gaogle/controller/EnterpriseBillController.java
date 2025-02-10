package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.model.EnterpriseBillModel;
import top.gaogle.pojo.param.EnterpriseBillQueryParam;
import top.gaogle.service.EnterpriseBillService;

/**
 * 企业账单管理
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/enterprise_bill")
public class EnterpriseBillController {

    private final EnterpriseBillService enterpriseBillService;

    @Autowired
    public EnterpriseBillController(EnterpriseBillService enterpriseBillService) {
        this.enterpriseBillService = enterpriseBillService;
    }

    /**
     * 分页查询企业账单信息（企业端）
     */
    @Anonymous
    @GetMapping("/enterprise_query_page")
    public ResponseEntity<I18nResult<PageModel<EnterpriseBillModel>>> enterpriseQueryByPage(EnterpriseBillQueryParam queryParam) {
        return enterpriseBillService.enterpriseQueryByPage(queryParam).toResponseEntity();
    }

}
