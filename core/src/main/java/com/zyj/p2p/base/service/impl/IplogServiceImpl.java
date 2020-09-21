package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Iplog;
import com.zyj.p2p.base.mapper.IplogMapper;
import com.zyj.p2p.base.query.IplogQueryObject;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.service.IplogService;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/9/21-15:18
 */
@Service
public class IplogServiceImpl implements IplogService {
    @Autowired
    private IplogMapper iplogMapper;

    @Override
    public PageResult query(IplogQueryObject qo) {
        qo.setUsername(UserContext.getCurrent().getUsername());
        int count = this.iplogMapper.queryForCount(qo);
        if (count > 0){
            List<Iplog> list = this.iplogMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }
}
