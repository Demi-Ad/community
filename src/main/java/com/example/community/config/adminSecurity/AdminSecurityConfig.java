package com.example.community.config.adminSecurity;


import com.example.community.config.adminSecurity.auth.AdminDetailService;
import com.example.community.config.adminSecurity.handler.AdminFailureHandlerCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AdminDetailService adminDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/admin/**")
                .authorizeRequests()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .permitAll()
                .usernameParameter("adminId")
                .passwordParameter("adminPwd")
                .loginProcessingUrl("/admin/login")
                .failureHandler(new AdminFailureHandlerCustom())
                .defaultSuccessUrl("/admin/dashboard")
                .and()
                .csrf().ignoringAntMatchers("/admin/notice/delete/**");
    }

}
