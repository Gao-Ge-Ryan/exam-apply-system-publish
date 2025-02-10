package top.gaogle.framework.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.gaogle.framework.security.UserDetailsCustomizer;

public class SecurityUtil {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户详情
     **/
    public static UserDetailsCustomizer getLoginUser() {
        return (UserDetailsCustomizer) getAuthentication().getPrincipal();

    }

    /**
     * 获取用户账号
     **/
    public static String getLoginUsername() {
        return ((UserDetailsCustomizer) getAuthentication().getPrincipal()).getUsername();

    }

    /**
     * 获取企业id
     **/
    public static String getEnterpriseId() {
        return ((UserDetailsCustomizer) getAuthentication().getPrincipal()).getEnterpriseId();

    }


}
