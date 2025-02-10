package top.gaogle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.gaogle.framework.annotation.Log;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.dto.PutUserRoleDTO;
import top.gaogle.pojo.enums.BusinessTypeEnum;
import top.gaogle.pojo.enums.OperatorTypeEnum;
import top.gaogle.pojo.model.UserModel;
import top.gaogle.pojo.param.UserQueryParam;
import top.gaogle.service.UserService;

/**
 * 用户管理
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 分页条件查询用户
     */
    @PreAuthorize("hasAuthority('user_view')")
    @GetMapping("/page_list")
    public ResponseEntity<I18nResult<PageModel<UserModel>>> getPageList(UserQueryParam userQueryParam) {
        return userService.getPageList(userQueryParam).toResponseEntity();

    }

    /**
     * 修改用户角色
     */
    @Log(title = "修改用户角色", businessType = BusinessTypeEnum.UPDATE, operatorType = OperatorTypeEnum.ADMIN)
    @PreAuthorize("hasAuthority('user_put_role')")
    @PutMapping("/put_role")
    public ResponseEntity<I18nResult<Boolean>> putUserRole(@RequestBody PutUserRoleDTO putUserRoleDTO) {
        return userService.putUserRole(putUserRoleDTO).toResponseEntity();
    }
}
