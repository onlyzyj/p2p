package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.UserinfoMapper;
import com.zyj.p2p.base.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:42
 */
@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Override
    public void update(Userinfo userinfo) {
        int ret = userinfoMapper.updateByPrimaryKey(userinfo);
        if (ret == 0){
            throw new RuntimeException("乐观锁失败，Userinfo:"+userinfo.getId());
        }
    }

    @Override
    public void add(Userinfo userinfo) {
        userinfoMapper.insert(userinfo);
    }

    @Override
    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }
}
