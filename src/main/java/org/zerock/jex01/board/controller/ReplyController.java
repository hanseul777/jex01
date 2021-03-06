package org.zerock.jex01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.board.dto.ReplyDTO;
import org.zerock.jex01.board.service.ReplyService;

import java.util.List;

@Log4j2
//@ResponseBody : RestController를 걸면 자동으로 됨
@RestController //리턴값이 전부다 JSON으로 처리된다 : 기본자료형은 text로 받고 객체타입은 전부 JSON으로 반환하도록 설정
@RequestMapping("/replies") // 보통 rest방식은 복수형으로 잡는다
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("")
    public String[] doA(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new String[]{"AAA","BBB","CCC"};
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    // JSON으로 보낸다 -> JSON으로 데이터를 보내는데 이거를 자바의 객체(DTO)로 변경해 줘야 한다. : @RequestBody의 역할
    public int add(@RequestBody ReplyDTO replyDTO){

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        log.info("-----------------------------------------");
        log.info(replyDTO);//제대로 데이터 수집된건지 확인

        return replyService.add(replyDTO);
    }

    @DeleteMapping("/{rno}") //resource는 특정한 id를 가진다 -> 이 id역할을 하는 특정한 rno를 들어오게한다.
    public String remove(@PathVariable(name="rno") Long rno){ //JSON으로 받을건지 그냥 파라미터로 던져서 받는건지 정해야 한다.
        //@PathVariable(name="rno") : 내가 경로에 줬던 애가 파라미터에 자동으로 수집
        log.info("----------------reply remove----------------");

        log.info("rno : " + rno);

        replyService.remove(rno);

        return "success";
    }

    @PutMapping("/{rno}")
    public String modify(@PathVariable(name="rno") Long rno,
                         @RequestBody ReplyDTO replyDTO){

        log.info("---------------------reply modify---------------------" + rno);
        log.info(replyDTO);

        replyService.modify(replyDTO);

        return "success";
    }

    @GetMapping("/list/{bno}") //url이 'replies/list/230' 처럼 나오게
    public List<ReplyDTO> getBoardreplies(@PathVariable(name = "bno") Long bno){
        //서비스 계층 호출
        return replyService.getRepliesWithBno(bno);
    }
}