package com.github.collecting.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.collecting.dto.UserSession;
import com.github.collecting.entity.Resource;
import com.github.collecting.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityService securityService;
    @Autowired
    ObjectMapper objectMapper;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return provider;
    }

    @Bean
    public SwitchLoginUserFilter switchUserFilter() {
        SwitchLoginUserFilter switchUserFilter = new SwitchLoginUserFilter();
        switchUserFilter.setUserDetailsService(securityService);
        switchUserFilter.setUsernameParameter("tenantCode");
        switchUserFilter.setSuccessHandler(this::onLoginSuccess);
        return switchUserFilter;
    }

    private void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (isAjax(request)) {
            writeResponse(response, true, "SUCCESS", null, "登录成功");
        } else {
            SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
            handler.setDefaultTargetUrl("/");
            handler.setAlwaysUseDefaultTargetUrl(true);
            handler.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private void onLoginFail(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isAjax(request)) {
            writeResponse(response, false, "FAIL", null, exception.getMessage());
        } else {
            SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/login?error");
            failureHandler.onAuthenticationFailure(request, response, exception);
        }
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();

        //初始化全局权限对应表
        List<Resource> resources = securityService.findResources();
        for (Resource resource : resources) {
            if (resource.getPermissionUrl() != null && !"".equals(resource.getPermissionUrl())) {
                log.info("permission: [url:{} , code:{}]", resource.getPermissionUrl(), resource.getResourceCode());
                http.authorizeRequests()
                        .antMatchers(resource.getPermissionUrl())
                        .hasAuthority(resource.getResourceCode());
            }
        }

        http.csrf()
                .disable();

        http.formLogin()
                .loginPage("/login")
                .failureHandler(this::onLoginFail)
                .successHandler(this::onLoginSuccess)
                .permitAll();

        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/static/**",
                        "/login",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security"
                )
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilter(switchUserFilter())
                .addFilterAfter(new GenericFilterBean() {
                    @Override
                    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
                        UserSession loginUser = UserSession.getUser();
                        HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(httpRequest);
                        wrapper.addHeader("userName", loginUser.getUsername());
                        wrapper.addHeader("userCode", loginUser.getUserCode());
                        wrapper.addHeader("tenantCode", loginUser.getTenantCode());
                        filterChain.doFilter(wrapper, servletResponse);
                    }
                }, SwitchUserFilter.class);

        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    if (isAjax(request)) {
                        writeResponse(response, false, accessDeniedException.getMessage(), "ACCESS_DENIED", "没有权限");
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                accessDeniedException.getMessage());
                    }
                })
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login") {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        if (isAjax(request)) {
                            writeResponse(response, false, null, "UN_LOGIN", "用户验证失败");
                        } else {
                            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
                            super.commence(request, response, authException);
                        }
                    }
                });

        http.logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessHandler((request, response, authentication) -> {
                    if (isAjax(request)) {
                        writeResponse(response, true, "SUCCESS", null, null);
                    } else {
                        SimpleUrlLogoutSuccessHandler urlLogoutHandler = new SimpleUrlLogoutSuccessHandler();
                        urlLogoutHandler.setDefaultTargetUrl("/login");
                        urlLogoutHandler.onLogoutSuccess(request, response, authentication);
                    }
                });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    private boolean isAjax(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept == null) {
            accept = request.getContentType();
        }
        return accept.contains("application/json");
    }

    private void writeResponse(HttpServletResponse response, boolean success, Object data, String errCode, String errMsg) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("data", data);
        map.put("errCode", errCode);
        map.put("errMsg", errMsg);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }


}
