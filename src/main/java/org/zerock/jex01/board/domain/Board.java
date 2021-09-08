package org.zerock.jex01.board.domain;

import lombok.*;
import org.zerock.jex01.board.dto.BoardDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter //Board는 읽기전용클래스
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
//domain클래스 또는 entity클래스라고 부름 -> DB와 1:1함
public class Board { //읽기전용 -> getter만 사용

    private Long bno;
    private String title,content,writer;
    private LocalDateTime regDate,modDate;

    @Builder.Default
    private List<BoardAttach> attachList = new ArrayList<>();

    public BoardDTO getDTO(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build();
        return boardDTO;
    }

    public void setBno(Long bno) {
        this.bno = bno;
    }

    public void addAttach(BoardAttach attach){
        attachList.add(attach);
    }
}
