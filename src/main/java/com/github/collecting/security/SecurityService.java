package com.github.collecting.security;

import com.github.collecting.dao.DeptDao;
import com.github.collecting.dao.UserDao;
import com.github.collecting.dao.UserRoleDao;
import com.github.collecting.dto.UserSession;
import com.github.collecting.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.github.collecting.jpa.base.Conditions.where;

@Service
@Transactional
public class SecurityService implements UserDetailsService {


    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    DeptDao deptDao;

    @Deprecated
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Boolean swithUser = (Boolean) request.getAttribute("switchUser");
        if (swithUser != null && swithUser) {
            username = (String) request.getAttribute("userName");
        }
        //获取账号下多个租户下的用户
        List<User> users = userDao.findAll(
                where("userName").is(username).and("recordStatus").is(1)
        );
        if (users != null && users.size() > 0) {
            String tenantCode = (String) request.getAttribute("tenantCode");
            //指定登录的租户用户
            if (tenantCode != null && !"".equals(tenantCode)) {
                Optional<User> first = users.stream().filter(user -> user.getTenantCode().equals(tenantCode)).findFirst();
                if (first.isPresent()) {
                    return createSession(first.get());
                }
            }
            //取第一个可用的租户用户
            return createSession(users.get(0));
        }
        //没有可用的租户用户
        throw new UsernameNotFoundException("账号或密码错误");
    }

    private UserSession createSession(User user) {
        //加载用户权限
        UserSession userSession = new UserSession(user.getUserName(),
                user.getPassword(),
                user.getRecordStatus() == 1,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(StringUtils.split(user.getRoleName(), ","))
        );
        //权限集合
        userSession.setUserCode(user.getUserCode());
        userSession.setTenantCode(user.getTenantCode());
        return userSession;
    }

}
