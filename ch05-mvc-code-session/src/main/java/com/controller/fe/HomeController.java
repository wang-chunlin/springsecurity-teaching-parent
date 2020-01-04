package com.controller.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cj
 * @date 2019/12/26
 */
@Controller
@RequestMapping("/fe")
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
}
