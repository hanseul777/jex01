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

        http.formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
                .roles("MEMBER");
        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
                .roles("MEMBER","ADMIN");
    }
}
