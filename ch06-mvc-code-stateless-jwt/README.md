# jwt
> http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html
> https://jwt.io/

## jjwt

> https://juejin.im/post/5a03e98c5188253d6817078f
> https://my.oschina.net/xiaominmin/blog/2999894

# 无状态服务的会话与验证管理
比如访问/api/admin这个需要验证的资源
经过spring security安全链的过程
1.经过SecurityContextPersistenceFilter
因为是无状态,所以不会使用默认行为(就是从会话读取Authentication)

2.经过UsernamePasswordAuthenticationFilter,因为此时不是一个验证请求
所以此过滤器直接放行(此过滤器主要是针对url是/login,
方法是post的时候才生效,他主要是
是做登录验证,验证通过之后会产生Authentication,
并放置到SecurityContextHolder中)

3.ExceptionTranslationFilter,由于此时没有异常,所以什么都不干
4.FilterSecurityInterceptor开始做权限处理
    4.1 依据你配置的信息:antMatchers("/api/admin").authenticated()
    4.2 依据获取的Authentication(此时由于没有Authentication),此时
    这个Authentication里面是有账号,密码以及权限信息的
5 又回到ExceptionTranslationFilter,捕获AccessDeniedException
开始调用AuthenticationEntryPoint的commence方法,发起验证流程
它的默认行为是会重定向到登录页面

基于前面的理解,要结合jwt来实现无状态服务下的会话管理,就需要添加一个过滤器
到安全链中.
因为会话的数据已经通过jwt生成的token发给客户端,然后客户端每次请求
都会带过来这个token

## 生成TOKEN
时机应该是客户端登录成功之后发送一个TOKEN给客户端
做法:
在登录成功Handler里面发一个token,在此token中可以放入任何数据,
主要是用户名

## 解析TOKEN
添加一个过滤器到安全链中,添加到链的哪个位置?此过滤器改干什么?

依据前面几个步骤的分析,应该是在最后一个过滤器之前的某个位置,一定要产生
一个Authentication并放置到SecurityContextHolder.

所以我们添加的过滤器的作用主要是
1. 从客户端传递过来的token中获取需要的信息,比如用户名
2. 想办法把这个username转换成一个Authentication
    2.1 因为按照规范,客户端不应该传用户的密码过来
    所以可以利用UserDetailService,加载一个用户
    此用户信息是包含用户名,密码,角色(授权)
    2.2 可以直接实例化一个UsernamePasswordAuthentication()
3. 把Authentication放入到SecurityContextHolder


过滤器放在UsernamePasswordAuthenticationFilter之前的理由如下:
1. 先从令牌中获取到了Authentication,那么UsernamePasswordAuthenticationFilter
就不需要重复验证

建议:把jwt验证过滤器放在前面




