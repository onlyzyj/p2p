package com.zyj.p2p.business.service;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.business.domain.RechargeOffline;

/**
 * @author onlyzyj
 * @date 2020/11/2-19:18
 */
public interface AccountFlowService {
    /**
     * 账户充值流水
     *
     * @param r
     * @param applierAccount
     */
    void rechargeFlow(RechargeOffline r, Account applierAccount);
}
