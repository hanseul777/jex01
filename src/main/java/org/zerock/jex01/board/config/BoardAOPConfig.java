package org.zerock.jex01.board.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//냄새없는 포장을 여기저기 적용을 해야하는데 이 기능을 자동으로 생성해주는지 정해주는 것 (p.456)
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.zerock.jex01.board.aop")
public class BoardAOPConfig {
}
