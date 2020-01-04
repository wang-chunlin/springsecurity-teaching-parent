package com.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author cj
 * @date 2020/1/3
 */

@Service
public class MyUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //这个地方是通过查询数据获取用户的密码以及角色信息,这里就暂时写死
        return new User(username, "123",
                AuthorityUtils.createAuthorityList("asdf"));
    }
}
