package com.oliver.config;

import com.oliver.filter.JwtFilter;
import com.oliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserService userService;

  @Bean
  public JwtFilter authenticationTokenFilterBean() throws Exception {
    return new JwtFilter();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
    auth.userDetailsService( userService ).passwordEncoder( new BCryptPasswordEncoder() );
  }

  @Override//defines which URL paths should be secured and which should not.
  protected void configure( HttpSecurity httpSecurity ) throws Exception {

    httpSecurity.csrf().disable()// 由于使用的是JWT，这里不需要csrf
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()// 基于token，所以不需要session
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers(HttpMethod.POST, "/auth/**").permitAll() // 对于获取token的rest api要允许匿名访问
        .antMatchers(HttpMethod.POST).authenticated()
        .antMatchers(HttpMethod.PUT).authenticated()
        .antMatchers(HttpMethod.DELETE).authenticated()
        .antMatchers(HttpMethod.GET).authenticated()
        .and().formLogin().permitAll();
    // 允许对于网站静态资源的无授权访问
//      .antMatchers(HttpMethod.GET,
//        "/",
//        "/*.html",
//        "/favicon.ico",
//        "/**/*.html",
//        "/**/*.css",
//        "/**/*.js"
//    ).permitAll()

//    .antMatchers("/admin/**").access("hasAuthority('ROLE_ADMIN')")

//                .anyRequest().authenticated().and().formLogin().loginPage("/login")
//                .failureUrl("/login?error").permitAll().and().logout().permitAll();
    // 除上面外的所有请求全部需要鉴权认证
//				.anyRequest().authenticated();

    httpSecurity
        .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);//添加jwt filter
    httpSecurity.headers().cacheControl();// 禁用缓存
  }

}
