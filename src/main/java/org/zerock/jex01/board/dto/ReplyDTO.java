package org.zerock.jex01.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDTO {

    private Long rno; //다른거 할건데 그거 기본값이 Long타입이라고 여기도 그렇게 설정한다고 하심
    private Long bno;
    private String replyer;
    private String reply;
    private LocalDateTime replyDate;
    private LocalDateTime modDate;
}
