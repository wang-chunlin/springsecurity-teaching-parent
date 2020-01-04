package config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import util.JwtUtil;
import vo.ResponseVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2020/1/3
 */
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ResponseVO vo = new ResponseVO();
        vo.setCode("200");
        vo.setMsg("login success");
        JwtUtil jwtUtil = new JwtUtil();
        String username = authentication.getName();
        String jwtToken = null;
        try {
            jwtToken = jwtUtil.createJWT(username, 100000*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        vo.setData(jwtToken);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), vo);
    }
}
