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

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {RootConfig.class, SecurityConfig.class})
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

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
            String mname = "관리자"+i; //유저0

            String result = query;

            result = result.replace("v1",mid);
            result = result.replace("v2",mpw);
            result = result.replace("v3",mname);

            System.out.println(result);

        }
    }
}
