package com.blog.realm;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {


    @Autowired
    private BloggerService bloggerService;

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录验证
     * token：令牌，基于用户名和密码的令牌
     *
     * 密码的比对：通过AuthorizingRealm的父类AuthenticatingRealm的credentialsMatcher属性来进行密码的比对
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //AuthenticationToken强转为UsernamePasswordToken
//        UsernamePasswordToken uptoken=(UsernamePasswordToken) token;
        //从传递来的uptoken中获取userName，即页面传来的用户名
        //UsernamePasswordToken中保存的是从页面传来的信息
//      String userName=uptoken.getUsername();
        String userName= (String) token.getPrincipal();
        //根据用户名查询用户信息
        Blogger blogger = bloggerService.findByUserName(userName);
        //如果存在该用户，存放到session中
        if(blogger!=null){
            SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER,blogger);
            /*构建AuthenticationInfo对象，通常使用的实现类为SimpleAuthenticationInfo
            其中保存的数据信息均是从数据库中获取的，以此来和UsernamePasswordToken对象中的密码进行比对
            1.principal：认证的实体信息，可以是用户名，也可以是数据库表对应的实体类对象
            2.credentials：数据库中的密码
            3.realmName：当前Realm类的名称，调用父类的getName方法即可获取*/

            //使用从数据库中查出的userName构建加密的盐值，userName唯一，故盐值也唯一
            ByteSource credentialsSalt=ByteSource.Util.bytes(blogger.getUserName());

            /*在构建AuthenticationInfo对象时，必须要把根据查到的用户名（每次都不一样）构建出的盐值
            作为实参传入构造方法。这样，从前台传来的明文密码正确时，在加密时才能用到这个盐值，才能确保
            与数据库中的暗文密码一致。否则，即使明文密码正确，经过缺少盐值的md5加密后也不会与数据库密码
            记录一致，导致登录失败。当然数据库中的密码是手动经过1024次MD5盐值加密后的结果。

            盐值加密的目的就在于，即使不同用户的明文密码一致，但经MD5盐值加密后存储到数据库中的密码也不会相同，
            因为每次加密的盐值不同，而盐值的值此处取决于用户名（一般用id和随机字符串生成），（数据库中设定用户名不能相同）
            用户名唯一，盐值也唯一，加密后的暗文密码也唯一
            */
            AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),credentialsSalt,getName());
            return authenticationInfo;
        }
        return null;
    }
}
