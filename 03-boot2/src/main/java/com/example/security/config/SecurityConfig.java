package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;

@EnableWebSecurity
// TODO 5-11 @ComponentScanアノテーションのみ削除する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        http.httpBasic();
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .mvcMatchers("/edit/*").hasRole("ADMIN")
                .mvcMatchers("/remove/*").hasRole("ADMIN")
                .mvcMatchers("/insert*").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.logout()
                .invalidateHttpSession(true)
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
