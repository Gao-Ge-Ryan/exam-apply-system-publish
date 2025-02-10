package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.model.FormTemplateModel;
import top.gaogle.pojo.param.FormTemplateEditParam;
import top.gaogle.pojo.param.FormTemplateQueryParam;
import top.gaogle.service.FormTemplateService;

/**
 * 表单模板
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/form_template")
public class FormTemplateController {

    private final FormTemplateService formTemplateService;

    @Autowired
    public FormTemplateController(FormTemplateService formTemplateService) {
        this.formTemplateService = formTemplateService;
    }

    /**
     * 新增表单
     */
    @PostMapping
    public ResponseEntity<I18nResult<Boolean>> add(@RequestBody FormTemplateEditParam editParam) {
        return formTemplateService.add(editParam).toResponseEntity();
    }

    /**
     * 修改表单
     */
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> put(@RequestBody FormTemplateEditParam editParam) {
        return formTemplateService.put(editParam).toResponseEntity();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<FormTemplateModel>>> queryByPageAndCondition(FormTemplateQueryParam queryParam) {
        return formTemplateService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 根据id查询详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<I18nResult<FormTemplateModel>> queryOneById(@PathVariable("id") String id) {
        return formTemplateService.queryOneById(id).toResponseEntity();
    }

    /**
     * 根据id删除表单
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<I18nResult<Boolean>> deleteById(@PathVariable("id") String id) {
        return formTemplateService.deleteById(id).toResponseEntity();
    }


}
