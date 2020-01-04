package config.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.JwtUtil;
import com.vo.ResponseVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2020/1/3
 */
/*public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
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
            jwtToken = jwtUtil.createJWT(username, 10*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        vo.setData(jwtToken);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), vo);
    }
}*/



public class JwtLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(isAjaxRequest(request)){
            response.setContentType("application/json;charset=UTF-8");
            ResponseVO vo = new ResponseVO();
            vo.setCode("200");
            vo.setMsg("login success");
            JwtUtil jwtUtil = new JwtUtil();
            String username = authentication.getName();
            String jwtToken = null;
            try {
                jwtToken = jwtUtil.createJWT(username, 10*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            vo.setData(jwtToken);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), vo);
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }

    public  boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}