package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.dto.ReplyDTO;
import org.zerock.jex01.board.mapper.BoardMapper;
import org.zerock.jex01.board.mapper.ReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper replyMapper; // 자동주입
    private final BoardMapper boardMapper;

    @Override
    public int add(ReplyDTO replyDTO) {
        int count = replyMapper.insert(dtoToEntity(replyDTO));
        boardMapper.updateReplyCnt(replyDTO.getBno(),1);

        return count;
    }

    @Override
    public List<ReplyDTO> getRepliesWithBno(Long bno) {
        return replyMapper.getListWithBoard(bno).stream()
                .map(reply -> entityToDTO(reply))//각각의 reply를 entityToDTO로 바꿔줌
                .collect(Collectors.toList());
    }

    @Override
    public int remove(Long rno) {
        //bno를 삭제하기 -> rno밖에 없어서 할 수 없음
      return replyMapper.delete(rno);
    }

    @Override
    public int modify(ReplyDTO replyDTO) {
        return replyMapper.update(dtoToEntity(replyDTO));
    }
}
