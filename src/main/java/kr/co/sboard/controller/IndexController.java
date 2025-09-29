package kr.co.sboard.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/","index"})
    public String index(Authentication authentication) {

        //로그인 체크

        // 로그인을 했으면
        if(authentication != null && authentication.isAuthenticated()) {
            return "forward:/article/list";
        }else{
            return "index";
        }

    }
}
