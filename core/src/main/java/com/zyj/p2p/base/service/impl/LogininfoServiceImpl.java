package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.mapper.LogininfoMapper;
import com.zyj.p2p.base.service.LogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/9/15-21:09
 */
@Service
public class LogininfoServiceImpl implements LogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Override
    public void register(String username, String password) {
        int count = logininfoMapper.getCountByUsername(username);

        if (count <= 0){
            Logininfo li = new Logininfo();
            li.setPassword(password);
            li.setUsername(username);
            li.setState(Logininfo.STATE_NORMAL);
            logininfoMapper.insert(li);
        }else{
            throw new RuntimeException("用户名已存在");
        }
    }

}
