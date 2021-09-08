package org.zerock.jex01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.service.BoardService;
import org.zerock.jex01.board.service.TimeService;
import org.zerock.jex01.common.dto.PageMaker;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

@Controller
@RequestMapping("/board/*") //getMapping + postMapping
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService; //autowired대신 final-> 인터페이스만 바라보게

    private final BoardService boardService; //postMapping에서 사용

    @GetMapping("/time") //-> /board/time
    public void getTime(int num,Model model){ //에러처리 확인 -> int numm 추가
        log.info("==================controller getTime===================");
        log.info(num % 0);
        model.addAttribute("time", timeService.getNow());
    }

    @GetMapping("/register")
    public void registerGet(){ //항상 똑같은 페이지  -> void
        //자동으로 해당하는 jsp로 감
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){ // 리다이렉트 하려고 String사용

        log.info("boardDTOM           " + boardDTO);

//        Long bno = 111L;
        Long bno = boardService.register(boardDTO);

        log.info("==================c              registerPost=========================");
        log.info(bno);

        redirectAttributes.addFlashAttribute("result",bno);

        return "redirect:/board/list"; // 리다이렉트 했을 때 새롭게 생성한 bno번호를보고 싶음 -> mybatis설정
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model){ //commonExceptionHandler가 예외처리해줘서 따로 신경 X
        //jsp로 담아서 보내줘야 한다. -> model사용(담아서 사용할 떄는 model)
        log.info("c         getList........................................." + pageRequestDTO);//c : controller

        //PageResquestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.getDTOList(pageRequestDTO); // 구조가 변함

        model.addAttribute("dtoList", responseDTO.getDtoList()); //response 안에있는 내용을 model에 담아줌

        // 데이터 가공
        int total = responseDTO.getCount();
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize(); // 이거 세개있으면 pageMaker만들 수 있음

        model.addAttribute("pageMaker", new PageMaker(page,size,total));
    }

    @GetMapping(value = {"/read","/modify","/read2"})
    //파라미터 수집 자동으로 해줌 -> 바로 입력해도 상관없음
    //pageRequestDTO를 생성해서 파라미터를 더 많이 받을 수 있음(커스터마이징이 가능함)
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        //Model : 파라미터데이터를 담아서 .jsp로 넘겨줌

        log.info("c          read" + bno);
        log.info("c          read" + pageRequestDTO); //정보수집확인

        model.addAttribute("boardDTO", boardService.read(bno));
   }

   @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("c                 remove : " + bno);

        if(boardService.remove(bno)){
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result", "success"); //개발자도구로 console창 확인을 위해 success를 넣어줌 -> 실행확인
        }//redirectAttributes가 객체를 전달해 주는 역할
        return "redirect:/board/list";
   }

   @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
        log.info("c              modify : " + boardDTO);
        if(boardService.modify(boardDTO)){
            redirectAttributes.addFlashAttribute("result","modified");
        }
        //게시물로 조회하러 가야함
       redirectAttributes.addAttribute("bno", boardDTO.getBno());
       redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
       redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

       if(pageRequestDTO.getType() != null){
           redirectAttributes.addAttribute("type", pageRequestDTO.getType());
           redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
       }

        return "redirect:/board/read";

   }
}
