package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.RealAuthQueryObject;

/**
 * @author onlyzyj
 * @date 2020/10/14-11:10
 */
public interface RealAuthService {
    RealAuth get(Long id);

    /**
     * 实名认证申请
     * @param realAuth
     */
    void apply(RealAuth realAuth);

    PageResult query(RealAuthQueryObject qo);

    void audit(Long id, String remark, int state);
}
