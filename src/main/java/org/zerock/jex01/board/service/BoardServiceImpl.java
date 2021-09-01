package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.mapper.BoardMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper; // 자동으로 주입해줌

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = boardDTO.getDomain(); //DTO를 VO로 변경

        boardMapper.insert(board);

        return board.getBno();
    }

    @Override
    public List<BoardDTO> getDTOList() {

        //boardMapper에서 가지고온 List를 한 번에 처리 (entity를 DTO로 바꾸는 기능이 필요한 것 -> function도 이용가능함)
       return boardMapper.getList().stream().map(board -> board.getDTO()).collect(Collectors.toList());
    }

    @Override
    public BoardDTO read(Long bno) {
        Board board = boardMapper.select(bno);

        if(board != null){
            return board.getDTO();
        }
        //return board != null? board.getDTO(): null; //if문을 삼항연산자로 변경
        return null;
    }

    @Override
    public boolean remove(Long bno) {
        return boardMapper.delete(bno) > 0;//bno가 int니까 0보다 크다 -> 한건이상 삭제되면 true 출력
    }

    @Override
    public boolean modify(BoardDTO boardDTO) {
        return boardMapper.update(boardDTO.getDomain()) > 0; // 0 보다 크면 true
    }
}
