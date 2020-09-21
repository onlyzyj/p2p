package com.zyj.p2p.base.service;

import com.zyj.p2p.base.query.IplogQueryObject;
import com.zyj.p2p.base.query.PageResult;

/**
 * @author onlyzyj
 * @date 2020/9/21-15:17
 */
public interface IplogService {
    PageResult query(IplogQueryObject qo);
}
