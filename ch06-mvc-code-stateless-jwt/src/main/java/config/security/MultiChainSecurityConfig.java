package config.security;

import com.security.JwtAuthenticationFilter;
import config.security.api.JwtAuthEntryPoint;
import config.security.api.JwtAuthFailHandler;
import config.security.api.JwtLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author cj
 * @date 2020/1/3
 */
@Configuration
public class MultiChainSecurityConfig {

    @Configuration
    @EnableWebSecurity
    @Order(95)
    public static  class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .cors().configurationSource(configurationSource())
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin()

                        .loginProcessingUrl("/api/login")
                    .successHandler(new JwtLoginSuccessHandler())
                    .failureHandler(new JwtAuthFailHandler())
                    .and()
                    .authorizeRequests().antMatchers("/").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new JwtAuthEntryPoint())
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication()
                    .withUser("user").password(passwordEncoder().encode("123")).authorities("xxx");
        }


        @Bean
        public PasswordEncoder passwordEncoder(){

            return new BCryptPasswordEncoder();
        }

        private UrlBasedCorsConfigurationSource configurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://127.0.0.1:8848");

            config.addAllowedMethod("GET");
            config.addAllowedMethod("POST");
            config.addAllowedMethod("OPTIONS");
            config.addAllowedMethod("PUT");
            config.addAllowedMethod("DELETE");
            config.addAllowedMethod("HEAD");
            //设置客户端允许的头.jwt的令牌是通过header传递过来的.
            config.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }
    }


    @Configuration
    @EnableWebSecurity
    @Order(96)
    public static class BrowserSecurityConfig extends  WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**")
                    .formLogin()

                    .and()
                    .authorizeRequests().antMatchers("/be").permitAll()
                    .antMatchers("/be/user").permitAll()
                    .antMatchers("/be/**").authenticated();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication()
                    .withUser("user").password(passwordEncoder().encode("123")).authorities("xxx");
        }


        @Bean
        public PasswordEncoder passwordEncoder(){

            return new BCryptPasswordEncoder();
        }


    }
}
