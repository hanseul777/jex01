package org.zerock.jex01.board.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.domain.BoardAttach;
import org.zerock.jex01.common.dto.PageRequestDTO;

import java.util.List;

public interface BoardMapper {

    //@insert로 만들지 말기 -> xml로 빠짐
    void insert(Board board);

//    List<Board> getList(); //첫버전은 페이징처리없어서 파라미터 없음
    List<Board> getList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);

    Board select (Long bno);

    int delete(Long bno); //'몇 건이 삭제되었다' 이런식으로 출력하기 위해

    int update(Board board);

    //mybatis는 기본적으로 파라미터의 수가 하나밖에 안들어감. : map을 만들어서 처리 or DTO로 처리 or param 어노테이션을 이용
    int updateReplyCnt(@Param("bno") Long bno, @Param("num") int num);

    int insertAttach(BoardAttach attach);
}
