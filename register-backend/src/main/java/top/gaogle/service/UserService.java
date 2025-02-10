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
import top.gaogle.dao.master.RoleMapper;
import top.gaogle.dao.master.UserMapper;
import top.gaogle.dao.master.UserRoleMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.dto.PutUserRoleDTO;
import top.gaogle.pojo.model.RoleModel;
import top.gaogle.pojo.model.UserModel;
import top.gaogle.pojo.param.UserQueryParam;
import top.gaogle.pojo.param.UserRoleEditParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService extends SuperService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public UserService(UserMapper userMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, @Qualifier("transactionTemplate") TransactionTemplate transactionTemplate) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.transactionTemplate = transactionTemplate;
    }

    public I18nResult<PageModel<UserModel>> getPageList(UserQueryParam queryParam) {
        I18nResult<PageModel<UserModel>> result = I18nResult.newInstance();
        try {
            String roleId = queryParam.getRoleId();
            List<String> roleUserIds = null;
            if (!StringUtils.isEmpty(roleId)) {
                roleUserIds = userRoleMapper.queryUserIdsByRoleId(roleId);
            }
            queryParam.setUserIds(roleUserIds);
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<UserModel> userModels = userMapper.selectMultiple(queryParam);
            PageModel<UserModel> userPageModel = new PageModel<>(userModels);
            List<String> userIds = userModels.stream().map(UserModel::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userIds)) {
                List<RoleModel> roleModels = roleMapper.queryRolesByUserIds(userIds);
                Map<String, List<RoleModel>> userIdGroup = roleModels.stream()
                        .collect(Collectors.groupingBy(RoleModel::getUserId));
                for (UserModel userModel : userModels) {
                    userModel.setRoleModels(userIdGroup.getOrDefault(userModel.getId(), null));
                }
            }
            result.succeed().setData(userPageModel);
        } catch (Exception e) {
            log.error("分页查询用户失败:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页查询用户失败");
        }
        return result;
    }

    public I18nResult<Boolean> putUserRole(PutUserRoleDTO putUserRoleDTO) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String userId = putUserRoleDTO.getUserId();
            List<String> roleIds = putUserRoleDTO.getRoleIds();
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    userRoleMapper.deleteByUserId(userId);
                    Long timeMillis = DateUtil.currentTimeMillis();
                    for (String roleId : roleIds) {
                        UserRoleEditParam editParam = new UserRoleEditParam();
                        editParam.setId(UniqueUtil.getUniqueId());
                        editParam.setUserId(userId);
                        editParam.setRoleId(roleId);
                        editParam.setCreateAt(timeMillis);
                        editParam.setUpdateAt(timeMillis);
                        String loginUsername = SecurityUtil.getLoginUsername();
                        editParam.setCreateBy(loginUsername);
                        editParam.setUpdateBy(loginUsername);
                        userRoleMapper.insert(editParam);
                    }
                }
            });
        } catch (Exception e) {
            log.error("分页条件查询角色发生异常：", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页条件查询角色发生异常");
        }
        return result;
    }
}
