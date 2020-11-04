package com.zyj.p2p.business.service;

import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.business.domain.RechargeOffline;
import com.zyj.p2p.business.query.RechargeOfflineQueryObject;

import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/11/2-15:49
 */
public interface RechargeOfflineService {
    void apply(RechargeOffline recharge);

    PageResult query(RechargeOfflineQueryObject qo);

    void audit(Long id, String remark, int state);
}
