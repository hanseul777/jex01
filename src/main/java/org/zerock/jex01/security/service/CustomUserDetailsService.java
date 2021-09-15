package org.zerock.jex01.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.dto.MemberDTO;
import org.zerock.jex01.security.mapper.MemberMapper;

import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override//로그인할 때 사용하게 된다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //반환해주는 UserDetails안에 사용자에 대한 정보가 다 들어있다.

        log.warn("CustomUserDetailService------------------------");//작동확인
        log.warn("CustomUserDetailService------------------------");
        // 사용자가 있는지 확인하고 없다면
        // 없다면 -> 예외를 던지는 것(UsernameNotFoundException)
        // / 비밀번호 틀리면 : badCredentials(자격을 증명할 수 없음)
        // / 권한 없으면 : access Denied
        log.warn(username);
        log.warn(memberMapper);
        log.warn("CustomUserDetailService------------------------");
        log.warn("CustomUserDetailService------------------------");
        log.warn("CustomUserDetailService------------------------");

        Member member = memberMapper.findByMid(username);

        log.warn(member);

        if(member == null){
            throw new UsernameNotFoundException("NOT EXIST"); //사용자 없으면 사용자 없다고 던지기
        }

        //새로운 String의 배열을 만들어 주는 것
//        String[] authorities = member.getRoleList().stream().map(memberRole -> memberRole.getRole()).toArray(String[]::new);

        User result = new MemberDTO(member); //memberDTO에 member를 넣으면 그 타입을 바꿔주는 것
//        User result = (User) User.builder()
//                .username(username)
//                .password(member.getMpw())
//                .accountExpired(false)//만료된계정이야?
//                .accountLocked(false)//잠긴계정이야?
//                .authorities(authorities)// 권한뭐야?-> ROLE_을 붙여서 명시적으로 권한을 줘야한다.
//                .build(); //다운캐스팅해주기

//        return null; // 반환 없음 : 무조건 로그인 안되게 하는 것
        return result;
    }
}
