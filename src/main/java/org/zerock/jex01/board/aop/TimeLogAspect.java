package org.zerock.jex01.board.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component//spring의 bean으로 등록
public class TimeLogAspect {

    {//객체가 생성되자마자 실행하기 위해서 디폴트블록으로 만들어 준다.(거의 사용하지않음)
        log.info("TimeLogAspect.......................");
        log.info("TimeLogAspect.......................");
        log.info("TimeLogAspect.......................");
        log.info("TimeLogAspect.......................");
        log.info("TimeLogAspect.......................");
        log.info("TimeLogAspect.......................");
    }

    @Before("execution(* org.zerock.jex01.board.service.*.*(..))") // 이 기능을 누구랑 합칠겨나를 말해주는 것(치킨이랑? 족발이랑?
    // .*.*(..) : * -> 모든 클래스 / (..) -> 모든메서드
//    public void logBefore(){ // 냄새없는 포장
    public void logBefore(JoinPoint joinPoint){
        log.info("logBefore................");

        log.info(joinPoint.getTarget());//실제 객체
        log.info(Arrays.toString(joinPoint.getArgs()));

        log.info("logBefore...............END");
    }

    @AfterReturning("execution(* org.zerock.jex01.board.service.*.*(..))") // 이 기능을 누구랑 합칠겨나를 말해주는 것(치킨이랑? 족발이랑?
    public void logAfter(){
        log.info("logAfter................");
    }
}
