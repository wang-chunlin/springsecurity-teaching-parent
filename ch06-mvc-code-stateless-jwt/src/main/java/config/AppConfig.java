package config;

import config.security.MultiChainSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
@ComponentScan({"com.service","com.security"})
//@Import(SessionFixationSecurityConfig.class)
//@Import(SessionCreatePolicySecurityConfig.class)
//@Import(SessionConcurrencySecurityConfig.class)
//@Import(ApiSecurityConfig.class)
@Import(MultiChainSecurityConfig.class)
public class AppConfig {


}
