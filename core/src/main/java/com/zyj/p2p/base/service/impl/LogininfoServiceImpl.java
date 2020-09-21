package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Iplog;
import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.IplogMapper;
import com.zyj.p2p.base.mapper.LogininfoMapper;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.LogininfoService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.MD5;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author onlyzyj
 * @date 2020/9/15-21:09
 */
@Service
public class LogininfoServiceImpl implements LogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IplogMapper iplogMapper;

    @Override
    public void register(String username, String password) {
        int count = logininfoMapper.getCountByUsername(username);

        if (count <= 0){
            Logininfo li = new Logininfo();
            li.setPassword(MD5.encode(password));
            li.setUsername(username);
            li.setState(Logininfo.STATE_NORMAL);
            logininfoMapper.insert(li);

            //初始化账户信息和userinfo
            Account account = new Account();
            account.setId(li.getId());
            System.out.println(account.toString()+account.getId());
            accountService.add(account);
            Userinfo userinfo = new Userinfo();
            userinfo.setId(li.getId());
            System.out.println(userinfo.toString());
            userinfoService.add(userinfo);
        }else{
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public int getCountByUsername(String username) {
        return logininfoMapper.getCountByUsername(username);
    }

    @Override
    public Logininfo login(String username, String password, String remoteAddr) {
        Logininfo current = logininfoMapper.login(username,MD5.encode(password));
        Iplog iplog = new Iplog();
        iplog.setIp(remoteAddr);
        iplog.setLoginTime(new Date());
        iplog.setUserName(username);
        if (current != null){
            UserContext.putCurrent(current);
            iplog.setState(Iplog.STATE_SUCCESS);
        }else{
            iplog.setState(Iplog.STATE_FAILED);
        }
        iplogMapper.insert(iplog);
        return current;
    }


}
