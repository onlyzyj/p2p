package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.business.domain.AccountFlow;
import com.zyj.p2p.business.domain.RechargeOffline;
import com.zyj.p2p.business.mapper.AccountFlowMapper;
import com.zyj.p2p.business.service.AccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author onlyzyj
 * @date 2020/11/2-19:18
 */
@Service
public class AccountFlowServiceImpl implements AccountFlowService {

    @Autowired
    private AccountFlowMapper accountFlowMapper;

    @Override
    public void rechargeFlow(RechargeOffline r, Account account) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        flow.setAmount(r.getAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setNote("线下充值成功,充值金额:" + r.getAmount());
        flow.setTradeTime(new Date());
        flow.setUsableAmount(account.getUsableAmount());
        this.accountFlowMapper.insert(flow);
    }
}
