package top.gaogle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.model.SpotInfoModel;
import top.gaogle.pojo.param.SpotInfoEditParam;
import top.gaogle.pojo.param.SpotInfoQueryParam;
import top.gaogle.service.SpotInfoService;

import java.util.List;

/**
 * 考点管理
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/spot_info")
public class SpotInfoController {

    private final SpotInfoService spotInfoService;

    @Autowired
    public SpotInfoController(SpotInfoService spotInfoService) {
        this.spotInfoService = spotInfoService;
    }

    /**
     * 添加考点
     */
    @PostMapping
    public ResponseEntity<I18nResult<Boolean>> insert(@RequestBody SpotInfoEditParam editParam) {
        return spotInfoService.insert(editParam).toResponseEntity();
    }

    /**
     * 修改考点
     */
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> putSpotInfo(@RequestBody SpotInfoEditParam editParam) {
        return spotInfoService.putSpotInfo(editParam).toResponseEntity();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/page")
    public ResponseEntity<I18nResult<PageModel<SpotInfoModel>>> queryByPageAndCondition(SpotInfoQueryParam queryParam) {
        return spotInfoService.queryByPageAndCondition(queryParam).toResponseEntity();
    }

    /**
     * 查询企业下所有可用考点
     */
    @GetMapping("/enterprise/all")
    public ResponseEntity<I18nResult<List<SpotInfoModel>>> enterpriseEnableAll() {
        return spotInfoService.enterpriseEnableAll().toResponseEntity();
    }

    /**
     * 根据id查询详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<I18nResult<SpotInfoModel>> queryOneById(@PathVariable("id") String id) {
        return spotInfoService.queryOneById(id).toResponseEntity();
    }

    /**
     * 根据register_publish_id查询发布对应考点
     */
    @GetMapping("/register_publish")
    public ResponseEntity<I18nResult<List<SpotInfoModel>>> queryByRegisterPublishId(@RequestParam("registerPublishId") String registerPublishId) {
        return spotInfoService.queryByRegisterPublishId(registerPublishId).toResponseEntity();
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<I18nResult<Boolean>> deleteById(@PathVariable("id") String id) {
        return spotInfoService.deleteById(id).toResponseEntity();
    }

}
