package com.zzh.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http
                .formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/user/test")
                .permitAll();
        super.configure(http);
    }

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("Lion").password("123456").roles("ADMIN")
                .and()
                .withUser("Jack").password("456789").roles("USER");
    }
}
