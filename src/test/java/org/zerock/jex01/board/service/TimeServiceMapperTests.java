package org.zerock.jex01.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.common.config.RootConfig;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BoardRootConfig.class, RootConfig.class})
public class TimeServiceMapperTests {

    @Autowired
    TimeService timeService;

    @Test
    public void testAdd(){
        String str = "She's my sunshine in the rain\n" +
                "My Tylenol when I'm in pain yeah\n" +
                "Let me tell you what she means to me\n";

        timeService.addString(str); // 글자수가 50이상이라 한테이블에는 제대로 들어가고 하나에는 제대로 못 들어감
    }

}
