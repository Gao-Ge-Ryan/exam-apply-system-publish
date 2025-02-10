package top.gaogle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.annotation.Log;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.AuthorityEnumModel;
import top.gaogle.pojo.dto.RoleAuthorityDTO;
import top.gaogle.pojo.enums.BusinessTypeEnum;
import top.gaogle.pojo.enums.OperatorTypeEnum;
import top.gaogle.service.AuthorityService;

import java.util.List;

/**
 * 权限
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {
    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }


    /**
     * 获取权限列表
     */
    @PreAuthorize("hasAuthority('authority_view')")
    @GetMapping("/authority_enum")
    public ResponseEntity<I18nResult<List<AuthorityEnumModel>>> getUserAuthorityEnums() {
        return authorityService.getUserAuthorityEnums().toResponseEntity();
    }

    /**
     * 根据角色id查询权限
     */
    @PreAuthorize("hasAuthority('authority_view')")
    @GetMapping("/{role_id}")
    public ResponseEntity<I18nResult<List<AuthorityEnumModel>>> getAuthorityByRoleId(@PathVariable("role_id") String roleId) {
        return authorityService.getAuthorityByRoleId(roleId).toResponseEntity();
    }

    /**
     * 根据角色id编辑权限
     */
    @Log(title = "根据角色id编辑权限", businessType = BusinessTypeEnum.UPDATE, operatorType = OperatorTypeEnum.ADMIN)
    @PreAuthorize("hasAuthority('role_put_authority')")
    @PutMapping
    public ResponseEntity<I18nResult<Boolean>> patchAuthorityByRoleId(@RequestBody RoleAuthorityDTO authorityDTO) {
        return authorityService.patchAuthorityByRoleId(authorityDTO).toResponseEntity();
    }


}
