package com.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.ResponseVO;

/**
 * @author cj
 * @date 2020/1/3
 */
@RestController
public class ApiDemoController {


    @RequestMapping("/api/admin")
    public ResponseVO admin(){
        return new ResponseVO("200","api/admin",null);
    }
}
