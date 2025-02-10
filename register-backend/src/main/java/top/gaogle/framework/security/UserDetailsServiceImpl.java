package top.gaogle.framework.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.gaogle.dao.master.*;
import top.gaogle.pojo.enums.AuthorityEnum;
import top.gaogle.pojo.model.EnterpriseModel;
import top.gaogle.pojo.model.RoleAuthorityModel;
import top.gaogle.pojo.model.RoleModel;
import top.gaogle.pojo.model.UserModel;

import java.util.*;

/**
 * 自定义登录信息查询
 *
 * @author goge
 * @since 1.0.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleAuthorityMapper roleAuthorityMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final EnterpriseMapper enterpriseMapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper, RoleAuthorityMapper roleAuthorityMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper, EnterpriseMapper enterpriseMapper) {
        this.userMapper = userMapper;
        this.roleAuthorityMapper = roleAuthorityMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.enterpriseMapper = enterpriseMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> authorities = new ArrayList<>();
        Map<String, Long> moduleNumMap = new HashMap<>();
        UserModel userModel = userMapper.selectByUsername(username);
        if (userModel == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }
        List<RoleModel> roleModels = roleMapper.queryRoleByUserId(userModel.getId());
        List<String> roleNames = new ArrayList<>();
        List<String> roleIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleModels)) {
            for (RoleModel roleModel : roleModels) {
                roleNames.add(roleModel.getName());
                roleIds.add(roleModel.getId());
            }
        }
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<RoleAuthorityModel> roleAuthorityModels = roleAuthorityMapper.queryByRoleIds(roleIds);

            for (RoleAuthorityModel roleAuthorityModel : roleAuthorityModels) {
                String authorityModule = roleAuthorityModel.getAuthorityModule();
                Long num = moduleNumMap.getOrDefault(authorityModule, 0L);
                moduleNumMap.put(authorityModule, num | roleAuthorityModel.getAuthorityNum());
            }
            for (AuthorityEnum authorityEnum : AuthorityEnum.values()) {
                Long shift = authorityEnum.shift();
                if (Objects.equals(shift, moduleNumMap.getOrDefault(authorityEnum.module(), 0L) & shift)) {
                    authorities.add(authorityEnum.value());
                }
            }
        }
        String enterpriseId = null;
        EnterpriseModel enterpriseModel = enterpriseMapper.queryByAccountBy(username);
        if (enterpriseModel !=null){
            enterpriseId =enterpriseModel.getId();
        }
        return new UserDetailsCustomizer(userModel.getId(), userModel.getUsername(), userModel.getPassword(), enterpriseId, roleNames, moduleNumMap, AuthorityUtils.createAuthorityList(authorities.toArray(new String[0])));
    }
}