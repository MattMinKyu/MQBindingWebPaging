package com.ytn.mq.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.headers().frameOptions().sameOrigin();
    	
    	http.authorizeRequests()
                // 페이지 권한 설정
                //.antMatchers("/captions/captionEdit").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
            .and() // 로그인 설정
                .formLogin()
                .loginPage("/ytn/member/login")
                //.defaultSuccessUrl("/user/login/result")
                .permitAll()
            .and()
                .csrf() // csrf 사용
                .ignoringAntMatchers("/ytn/captions/**") // csrf 제외 url
                .ignoringAntMatchers("/ytn/member/auth/**") // csrf 제외 url
                //.ignoringAntMatchers("**/stomp/captions/**") // csrf 제외 url
            .and() // 로그아웃 설정
                .logout()
                //.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                //.logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true);
            /*
            .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    		*/
    }
    
    /*
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
    */
}
