package org.zerock.jex01.board.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    private Long rno; //JPA 기본값이 Long타입이라고 여기도 그렇게 설정한다고 하심 (JPA : mybaits와 비슷한 기능을 하는 것)
    private Long bno;
    private String replyer;
    private String reply;
    private LocalDateTime replyDate;
    private LocalDateTime modDate;
}
