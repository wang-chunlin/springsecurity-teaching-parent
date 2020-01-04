package com.controller.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * @author cj
 * @date 2019/12/30
 */
@Controller
@RequestMapping("/fe")
public class SessionController {
    @RequestMapping("/sw")
    public void sessionWrite(HttpServletResponse response,HttpSession session) throws IOException {
        session.setAttribute("s", new Date().toString());
        response.getWriter().println("session write ok");
    }

    @RequestMapping("/sr")
    public void sessionRead(HttpServletResponse response,HttpSession session) throws IOException {
        Object data = session.getAttribute("s");
        response.getWriter().println(data);

    }
}
