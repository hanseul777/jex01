package org.zerock.jex01.board.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

import java.util.List;

@Transactional
//등록하는 기능을 추가 -> Controller는 화면에서 입력받는 것을 처리 : DTO를 처리하려고 함. Mapper에는 VO여서 DTO를 VO로 바꿔주는 작업이 필요함
public interface BoardService {

    Long register(BoardDTO boardDTO);

    //서비스계층은 항상 dto로 주고받음 : 파라미터를 받을 때도 리턴값도 dto -> requestDTO와 responseDTO를 나눌 수는 있음

//    List<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);

    BoardDTO read(Long bno);

    boolean remove(Long bno);

    boolean modify(BoardDTO boardDTO);
}
