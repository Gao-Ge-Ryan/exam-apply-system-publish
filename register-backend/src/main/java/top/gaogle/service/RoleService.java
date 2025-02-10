package top.gaogle.service;


import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import top.gaogle.dao.master.RoleAuthorityMapper;
import top.gaogle.dao.master.RoleMapper;
import top.gaogle.dao.master.UserRoleMapper;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.model.RoleModel;
import top.gaogle.pojo.param.RoleEditParam;
import top.gaogle.pojo.param.RoleQueryParam;

import java.util.List;

@Service
public class RoleService extends SuperService {

    private final RoleMapper roleMapper;
    private final RoleAuthorityMapper roleAuthorityMapper;
    private final UserRoleMapper userRoleMapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RoleService(RoleMapper roleMapper, RoleAuthorityMapper roleAuthorityMapper, UserRoleMapper userRoleMapper, @Qualifier("transactionTemplate") TransactionTemplate transactionTemplate) {
        this.roleMapper = roleMapper;
        this.roleAuthorityMapper = roleAuthorityMapper;
        this.userRoleMapper = userRoleMapper;

        this.transactionTemplate = transactionTemplate;
    }

    public I18nResult<Boolean> insertRole(RoleEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String name = editParam.getName();
            if (StringUtils.isAnyEmpty(editParam.getDescription(), name)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            Integer existSize = roleMapper.querySizeByName(name);
            if (existSize > 0) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "角色名称已存在");
            }
            String roleId = UniqueUtil.getUniqueId();
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setId(roleId);
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);
            String username = SecurityUtil.getLoginUsername();
            editParam.setCreateBy(username);
            editParam.setUpdateBy(username);
            roleMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("新增角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "新增角色发生异常");
        }
        return result;
    }


    public I18nResult<PageModel<RoleModel>> queryByPageAndCondition(RoleQueryParam queryParam) {
        I18nResult<PageModel<RoleModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<RoleModel> roleModels = roleMapper.queryByPageAndCondition(queryParam);
            PageModel<RoleModel> rolePageModel = new PageModel<>(roleModels);
            result.succeed().setData(rolePageModel);
        } catch (Exception e) {
            log.error("分页条件查询角色发生异常：", e);
            result.failed().setMessage("分页条件查询角色发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> patchRole(RoleEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String name = editParam.getName();
            if (StringUtils.isAnyEmpty(editParam.getDescription(), name)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            Integer existSize = roleMapper.querySizeExcludeSelfByName(name, editParam.getId());
            if (existSize > 0) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "角色名称已存在");
            }
            editParam.setUpdateAt(DateUtil.currentTimeMillis());
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setUpdateBy(loginUsername);
            roleMapper.patchRole(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("修改角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "修改角色发生异常");
        }
        return result;

    }

    public I18nResult<Boolean> deleteRole(String roleId) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    roleMapper.deleteRole(roleId);
                    roleAuthorityMapper.deleteByRoleId(roleId);
                    userRoleMapper.deleteByRoleId(roleId);
                }
            });

            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("删除角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "删除角色发生异常");
        }
        return result;
    }

    public I18nResult<List<RoleModel>> queryRolesByUserId(String userId) {
        I18nResult<List<RoleModel>> result = I18nResult.newInstance();
        try {
            List<RoleModel> roleModels = roleMapper.queryAll();
            List<String> roleIds = userRoleMapper.queryRoleIdByUserId(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                for (RoleModel roleModel : roleModels) {
                    if (roleIds.contains(roleModel.getId()))
                        roleModel.setAuthorized(true);
                }
            }
            result.succeed().setData(roleModels);
        } catch (Exception e) {
            log.error("根据用户id查询角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据用户id查询角色发生异常");
        }
        return result;
    }

    public I18nResult<RoleModel> queryDetailByRoleId(String roleId) {
        I18nResult<RoleModel> result = I18nResult.newInstance();
        try {
            RoleModel roleModel = roleMapper.queryDetailByRoleId(roleId);
            result.succeed().setData(roleModel);
        } catch (Exception e) {
            log.error("根据id查询角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据id查询角色发生异常");
        }
        return result;
    }

    public I18nResult<List<RoleModel>> queryAll() {
        I18nResult<List<RoleModel>> result = I18nResult.newInstance();
        try {
            List<RoleModel> roleModels = roleMapper.queryAll();
            result.succeed().setData(roleModels);
        } catch (Exception e) {
            log.error("查询所有角色发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询所有角色发生异常");
        }
        return result;
    }
}
