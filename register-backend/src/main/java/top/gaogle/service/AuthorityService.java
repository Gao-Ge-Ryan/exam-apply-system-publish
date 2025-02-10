package top.gaogle.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import top.gaogle.dao.master.RoleAuthorityMapper;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.pojo.enums.AuthorityEnum;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.AuthorityEnumModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.dto.RoleAuthorityDTO;
import top.gaogle.pojo.entity.RoleAuthorityEntity;
import top.gaogle.pojo.model.RoleAuthorityModel;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorityService extends SuperService {

    private final RoleAuthorityMapper roleAuthorityMapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public AuthorityService(RoleAuthorityMapper roleAuthorityMapper, @Qualifier("transactionTemplate") TransactionTemplate transactionTemplate) {
        this.roleAuthorityMapper = roleAuthorityMapper;
        this.transactionTemplate = transactionTemplate;
    }

    public I18nResult<List<AuthorityEnumModel>> getUserAuthorityEnums() {
        I18nResult<List<AuthorityEnumModel>> result = I18nResult.newInstance();
        try {
            List<AuthorityEnumModel> allParentEnum = AuthorityEnum.getAllParentEnumModel();
            result.succeed().setData(allParentEnum);
        } catch (Exception e) {
            log.error("查询权限枚举发生异常", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询权限枚举发生异常");
        }
        return result;
    }

    public I18nResult<List<AuthorityEnumModel>> getAuthorityByRoleId(String roleId) {
        I18nResult<List<AuthorityEnumModel>> result = I18nResult.newInstance();
        try {
            List<RoleAuthorityModel> roleAuthorityModels = roleAuthorityMapper.queryByRoleId(roleId);
            Map<String, Long> moduleNumMap = roleAuthorityModels.stream().collect(Collectors.toMap(RoleAuthorityModel::getAuthorityModule, RoleAuthorityModel::getAuthorityNum));
            List<AuthorityEnumModel> authorityEnumModels = new ArrayList<>();
            for (AuthorityEnum parentEnum : AuthorityEnum.getAllParentEnum()) {
                authorityEnumModels.add(new AuthorityEnumModel(parentEnum, moduleNumMap.getOrDefault(parentEnum.module(), 0L)));
            }
            result.succeed().setData(authorityEnumModels);
        } catch (Exception e) {
            log.error("根据角色ID查询权限枚举发生异常", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据角色ID查询权限枚举发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> patchAuthorityByRoleId(RoleAuthorityDTO authorityDTO) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            List<AuthorityEnum> authorityEnums = authorityDTO.getAuthorityEnums();
            if (!CollectionUtils.isEmpty(authorityEnums)) {
                Map<AuthorityEnum, Long> enumLongMap = new EnumMap<>(AuthorityEnum.class);
                for (AuthorityEnum authorityEnum : authorityEnums) {
                    if (AuthorityEnum.Type.UNIT.equals(authorityEnum.type())) {
                        Long authorityNum = enumLongMap.getOrDefault(authorityEnum.parent(), 1L);
                        enumLongMap.put(authorityEnum.parent(), authorityNum | authorityEnum.shift());
                    }
                }
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        String roleId = authorityDTO.getRoleId();
                        Long timeMillis = DateUtil.currentTimeMillis();
                        roleAuthorityMapper.deleteByRoleId(roleId);
                        for (Map.Entry<AuthorityEnum, Long> enumLongEntry : enumLongMap.entrySet()) {
                            RoleAuthorityEntity roleAuthority = new RoleAuthorityEntity();
                            roleAuthority.setId(UniqueUtil.getUniqueId());
                            roleAuthority.setRoleId(roleId);
                            roleAuthority.setAuthorityModule(enumLongEntry.getKey().module());
                            roleAuthority.setAuthorityNum(enumLongEntry.getValue());
                            roleAuthority.setCreateAt(timeMillis);
                            roleAuthority.setUpdateAt(timeMillis);
                            String loginUsername = SecurityUtil.getLoginUsername();
                            roleAuthority.setCreateBy(loginUsername);
                            roleAuthority.setUpdateBy(loginUsername);
                            roleAuthorityMapper.insert(roleAuthority);
                        }
                    }
                });
            }
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("根据角色ID编辑权限发生异常", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据角色ID编辑权限发生异常");
        }
        return result;
    }
}
