package org.zerock.jex01.security.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class LoginController {

    @GetMapping("/customLogin")
    public void loginCustom(){
        log.warn("custom login...get");
    }
}
