package com.github.collecting.security;

import com.github.collecting.dto.UserSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by serv on 2017/3/29.
 */
public class SwitchLoginUserFilter extends SwitchUserFilter {

    @Override
    protected Authentication attemptSwitchUser(HttpServletRequest request) throws AuthenticationException {
        String userName = UserSession.getUser().getUsername();
        request.setAttribute("switchUser", true);
        request.setAttribute("userName", userName);
        request.setAttribute("tenantCode", request.getParameter("tenantCode"));
        return super.attemptSwitchUser(request);
    }
}
