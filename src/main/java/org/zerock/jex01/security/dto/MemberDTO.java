package org.zerock.jex01.security.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.jex01.security.domain.Member;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String mid;
    private String mpw;
    private String mname;
    private boolean enabled;

    public MemberDTO(Member member){//생성자와 관련해서 문제가 있다. -> 타입을 바꿔주는 과정이 필요하다 (member를 넣으면 memberDTO가 나가는 방식)
       super(member.getMid(),
                member.getMpw(),
                member.getRoleList().stream().map(memberRole ->
                        new SimpleGrantedAuthority(memberRole.getRole())).collect(Collectors.toList()));
    this.mid = member.getMid();
    this.mpw = member.getMpw();
    this.mname = member.getMname();
    this.enabled = member.isEnabled();
    }

    public MemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) { //Collection : 권한정보
        super(username, password, authorities);
    }
}
