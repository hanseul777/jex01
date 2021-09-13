package org.zerock.jex01.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.jex01.security.handler.CustomLoginSuccessHandler;
import org.zerock.jex01.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity//security를 접목한다는 의미
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    {
//        log.info("SecurityConfig..............");
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //익명을 허용
                .antMatchers("/sample/doAll").permitAll()//어떤 URL에서 익명을 허용하는지
                .antMatchers("/sample/doMember").access("hasRole('ROLE_MEMBER')")
                .antMatchers("/sample/doAdmin").access("hasRole('ROLE_ADMIN')");

        //로그인페이지를 만들어 주는 부분
        http.formLogin().loginPage("/customLogin")
                .loginProcessingUrl("/login"); //원래 스프링시큐리티가 가지고 있던 경로인 /login으로 규정해준다. : post방식으로 실제로 로그인을 처리하는 url을 주는 것
//                .successHandler(customLoginSuccessHandler());

        //로그아웃하는 부분 : default라서 안줘도 상관은 없다
//        http.logout().invalidateHttpSession(true);

        http.csrf().disable(); // SecurityConfig에서 csrf를 사용하지 않겠다.H
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService()); //이걸 통해서 로그인 프로세스를 진행하겠다는 의미

//       auth.inMemoryAuthentication().withUser("member1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
//                .roles("MEMBER","ADMIN");
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }
}
