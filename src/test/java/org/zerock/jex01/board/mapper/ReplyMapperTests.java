package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.common.config.RootConfig;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class) // 해당코드가 스프링을 실행한다고 알려줌
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class}) //지정 된 클래스나 문자열을 이용해서 필요한 객체를 등록 (Spring의 Bean으로 등록된다.)
public class ReplyMapperTests {

    @Autowired(required = false)// required = false : spring이 로딩할 때 체크하지 않도록 하는 것
            //의존객체를 주입받지 못하면 에러가 나는데 못하더라도 bean을 생성할 수 있게 해줌(기본값이 true)
    ReplyMapper replyMapper;

    @Test
    public void insertDumies(){

        long[] arr = {223L, 222L, 221L, 217L, 216L};

        //위의 번호중 랜덤한 번호를 따서 댓글을 작성
        //IntStream : 루프
        IntStream.rangeClosed(1,100).forEach(num -> {

            long bno = arr[((int)(Math.random() * 1000)) % 5]; //0,1,2,3,로 떨어짐

            Reply reply = Reply.builder()
                    .bno(bno)
                    .reply("댓글................"+ num)
                    .replyer("user " + (num % 10))
                    .build();

            replyMapper.insert(reply);

        });

    }

    @Test
    public void testList(){
        Long bno = 223L;

        replyMapper.getListWithBoard(bno)
                .forEach(reply -> log.info(reply));
    }
}
