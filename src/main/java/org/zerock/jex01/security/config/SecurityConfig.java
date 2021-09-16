package org.zerock.jex01.security.config;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.jex01.security.handler.CustomAccessDeniedHandler;
import org.zerock.jex01.security.handler.CustomAuthenticationEntryPoint;
import org.zerock.jex01.security.handler.CustomLoginSuccessHandler;
import org.zerock.jex01.security.service.CustomUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity//security를 접목한다는 의미
@Log4j2
@MapperScan(basePackages = "org.zerock.jex01.security.mapper")
@ComponentScan(basePackages = "org.zerock.jex01.security.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    {
//        log.info("SecurityConfig..............");
//    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

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


        http.formLogin().loginPage("/customLogin")//로그인페이지를 만들어 주는 부분
                .loginProcessingUrl("/login"); //원래 스프링시큐리티가 가지고 있던 경로인 /login으로 규정해준다. : post방식으로 실제로 로그인을 처리하는 url을 주는 것
//                .successHandler(customLoginSuccessHandler());

        //로그아웃하는 부분 : default라서 안줘도 상관은 없다
//        http.logout().invalidateHttpSession(true);

        http.csrf().disable(); // SecurityConfig에서 csrf를 사용하지 않겠다.

        http.rememberMe().tokenRepository(persistentTokenRepository())
                .key("zerock")//암호화 된 문자열을 만들어주는데 그 때 뭘 사용할건지 지정
                .tokenValiditySeconds(60*60*24*30); //한달

//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint());
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint(); //많은 인증에러를 거의 다 잡아낸다.
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService);

        //이걸 통해서 로그인 프로세스를 진행하겠다는 의미 : 프로그램을 통해서 인증이 가능하도록 만들어줌
//        auth.userDetailsService(customUserDetailsService());

//       auth.inMemoryAuthentication().withUser("member1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
//                .roles("MEMBER","ADMIN");
    }

//    @Bean
//    public CustomUserDetailsService customUserDetailsService(){
//        return new CustomUserDetailsService();
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
}
