package org.zerock.jex01.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Override//로그인할 때 사용하게 된다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.warn("CustomUserDetailService------------------------");//작동확인
        log.warn("CustomUserDetailService------------------------");
        // 사용자가 있는지 확인하고 없다면
        // 없다면 -> 예외를 던지는 것(UsernameNotFoundException)
        // / 비밀번호 틀리면 : badCredentials(자격을 증명할 수 없음)
        // / 권한 없으면 : access Denied
        log.warn(username);
        log.warn("CustomUserDetailService------------------------");
        log.warn("CustomUserDetailService------------------------");
        log.warn("CustomUserDetailService------------------------");

        User result = (User) User.builder()
                .username(username)
                .password("$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2")
                .accountExpired(false)//만료된계정이야?
                .accountLocked(false)//잠긴계정이야?
                .authorities("ROLE_MEMBER","ROLE_ADMIN")// 권한뭐야?-> ROLE_을 붙여서 명시적으로 권한을 줘야한다.
                .build(); //다운캐스팅해주기

//        return null; // 반환 없음 : 무조건 로그인 안되게 하는 것
        return result;
    }
}
