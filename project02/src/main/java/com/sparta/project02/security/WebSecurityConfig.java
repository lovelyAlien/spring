package com.sparta.project02.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/user/**").permitAll() //중요!!
                .antMatchers("/h2-console/**").permitAll() //중요!!
                //board 폴더를 login 없이 허용
                .antMatchers("/board/**").permitAll()
                //common 폴더를 login 없이 허용
                .antMatchers("/common/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated() //모든 요청에 대해서 인증을 한다.
                .and()
                .formLogin()
                .loginPage("/user/login") //로그인 시 위치 지정
                .failureUrl("/user/login/error")//로그인 실패 시 위치 지정
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll();
    }
}
