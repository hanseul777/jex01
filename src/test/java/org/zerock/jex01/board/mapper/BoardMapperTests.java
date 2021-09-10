package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.common.config.RootConfig;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class) // 해당코드가 스프링을 실행한다고 알려줌
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class}) //지정 된 클래스나 문자열을 이용해서 필요한 객체를 등록 (Spring의 Bean으로 등록된다.)
public class BoardMapperTests {

    @Autowired
    BoardMapper boardMapper;

    @Test
    public void testDummies(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("user" + (i % 10))
                    .build();

            boardMapper.insert(board);

        });
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .type("C")
//                .keyword("AA")
                .build();

        log.info(pageRequestDTO);

        boardMapper.getList(pageRequestDTO).forEach(board -> {log.info(board);});

//        List<BoardDTO> boardDTOList = boardMapper.getList(pageRequestDTO).stream().map(board -> board.getDTO()).collect(Collectors.toList());
//
//        int count = boardMapper.getCount(pageRequestDTO);

//        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>builder()
//                .dtoList(boardDTOList)
//                .count(count)
//                .build();
    }

    @Test
    public void testSelect(){

        Board board = boardMapper.select(227L);

        log.info("-----------------------------------------------------");
        log.info(board);
        board.getAttachList().forEach(attach -> log.info(attach));
    }

    @Test
    public void testDelete(){
        Long bno = 220L;

        log.info("delete...................");
        log.info(boardMapper.delete(bno)); //1 : 한 건 삭제완성
    }

    @Test
    public void testUpdate(){
        Board board = Board.builder()
                .bno(213L)
                .title("수정된 제목")
                .content("수정된 내용...213")
                .build(); //가짜Board 만들어서 일단 값을 채워줌
        log.info(boardMapper.update(board)); //1 출력이면 제대로 완료 된 것 -> db확인해보기
    }
}
