package org.zerock.jex01.board.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.common.config.RootConfig;
import org.zerock.jex01.security.config.SecurityConfig;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.mapper.MemberMapper;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {RootConfig.class, SecurityConfig.class})
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    MemberMapper memberMapper;

    @Test
    public void testMember(){

        try {

            log.warn("-------------------------------");
            log.warn(memberMapper);

            String mid = "admin0";

            Member member = memberMapper.findByMid(mid);

            log.warn("--------------------------------");
            log.warn(member);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    @Test
    public void testEncode(){
        String str = "member1";
        String enStr = passwordEncoder.encode(str);
        log.warn(enStr);
    }

    @Test
    public void testDecode(){
        String str = "$2a$10$/DcehKwNofj/OupeswdAD.9SupaVS9tOLtm17iWzeKl6OlF/V7iW2";

        //제대로 암호화가 된건지만 확인이 가능
        boolean match = passwordEncoder.matches("member1",str); //member1의 암호가 str이랑 맞아??
        log.warn(match);
    }

    @Test
    public void insertMember(){
        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname');

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for(int i = 0; i < 10; i++){
            String mid = "user"+i; //user0
            String mpw = passwordEncoder.encode("pw"+i);//pw0 -> 암호화(Bcrypt 된 암호화를 사용)
            String mname = "유저"+i; //유저0

            String result = query;

            result = result.replace("v1",mid);
            result = result.replace("v2",mpw);
            result = result.replace("v3",mname);

            System.out.println(result);

        }
    }

    @Test
    public void insertAdmin(){
        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname');

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for(int i = 0; i < 10; i++){
            String mid = "admin"+i; //user0
            String mpw = passwordEncoder.encode("pw"+i);//pw0 -> 암호화(Bcrypt 된 암호화를 사용)
            String mname = "관리자"+i; //관리자0

            String result = query;

            result = result.replace("v1",mid);
            result = result.replace("v2",mpw);
            result = result.replace("v3",mname);

            System.out.println(result);

        }
    }
    @Test
    public void insertMemberRole(){
        String sql = "insert into tbl_member_role (mid,role) values ('%s','%s');";
        //sql뽑아서 바로 사용할거라서 ;붙이기

        for(int i = 0; i<10; i++){
            String result = String.format(sql, "user"+i, "ROLE_MEMBER");

            System.out.println(result);
        }
    }

    @Test
    public void insertAdminRole(){
        String sql = "insert into tbl_member_role (mid,role) values ('%s','%s');";
        //sql뽑아서 바로 사용할거라서 ;붙이기

        for(int i = 0; i<10; i++){
            String result = String.format(sql, "admin"+i, "ROLE_MEMBER");
            System.out.println(result);

            String result2 = String.format(sql, "admin"+i, "ROLE_ADMIN");
            System.out.println(result2);
        }
    }
}

