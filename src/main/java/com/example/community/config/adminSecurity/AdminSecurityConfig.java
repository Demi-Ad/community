package com.example.community.config.adminSecurity;


import com.example.community.config.adminSecurity.auth.AdminDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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
                .antMatchers("/admin/login/**").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .permitAll()
                .usernameParameter("adminId")
                .passwordParameter("adminPwd")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/dashboard")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/admin/dashboard"))
                .failureHandler((request, response, exception) -> response.sendRedirect("/admin/login?err=" + exception.getClass().getSimpleName()))
                .permitAll()
                .and()
                .csrf().ignoringAntMatchers("/admin/notice/delete/**","/admin/accountManage/unblock","/admin/guestBook/**","/admin/postDelete/**","/admin/commentDelete/**");
    }

}
