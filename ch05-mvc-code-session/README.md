# 固定会话攻击

> https://www.jianshu.com/p/a5ed607cb48b

spring Security会话固定攻击保护的4个设置
保留默认就够了

- none :不提供会话固定攻击的保护
- newSession:提供保护,创建新的会话,并且不把以前的会话数据拷贝到新的会话中
- migrateSession:提供保护,创建新的会话,并且把以前的会话数据拷贝到新的会话中
- changeSessionId:提供保护,每次登陆后改变id,并且把以前的会话数据拷贝到新的会话中
 这个设置与migrateSession类似,但是需要servlet容器的支持,如果容器不支持会抛出异常
 
 建议:要么不设置,要么设置为migrateSession()
 
 

# 会话创建策略
会话创建策略控制spring security如何使用session信息
并不影响mvc中的控制器对会话数据的使用

创建策略有以下几个
Stateless:无状态,意思是spring security完全不用session
ALWAYS:spring security总是要用,当前没有会话,我也创建会话
NEVER:spring security自己不创建会话,但是你已经有了会话,我就用
IF_REQUIRED:spring security需要用的会话,如果当前没有会话,我就创建一个

实战建议:要么不设置,要么设置成stateless.


# 并发会话控制
意思指同一个账号能登录的数量

