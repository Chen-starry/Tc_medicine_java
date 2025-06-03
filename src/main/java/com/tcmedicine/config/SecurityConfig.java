package com.tcmedicine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                // 允许访问所有HTML页面
                .antMatchers("/", "/index.html", "/login.html", "/register.html", "/predict.html", 
                           "/dashboard.html", "/medicine.html", "/diseases.html", "/diagnosis.html", "/user-dashboard.html").permitAll()
                // 允许访问路由路径
                .antMatchers("/login", "/register", "/predict", "/dashboard", "/medicine", "/diseases", "/diagnosis", "/logout").permitAll()
                // 允许访问所有静态资源
                .antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/UI/**", "/uploads/**").permitAll()
                // 允许访问地理数据文件
                .antMatchers("/china_provinces.geojson").permitAll()
                // 允许访问公开API接口
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/medicines/**").permitAll()
                .antMatchers("/api/herbal-medicines/**").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/categories").permitAll()
                .antMatchers("/api/syndromes/**").permitAll()
                .antMatchers("/api/predict/**").permitAll()
                .antMatchers("/api/admin/**").permitAll()
                // 允许提交反馈
                .antMatchers("/submit_feedback").permitAll()
                // 允许AI聊天
                .antMatchers("/chat").permitAll()
                // 允许图像识别
                .antMatchers("/predict").permitAll()
                .antMatchers("/predict/**").permitAll()
                .antMatchers("/health").permitAll()
                .antMatchers("/error").permitAll()
                // 用户后台API需要认证
                .antMatchers("/api/user/**").authenticated()
                .anyRequest().authenticated()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
            .and()
            // 添加JWT认证过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

} 