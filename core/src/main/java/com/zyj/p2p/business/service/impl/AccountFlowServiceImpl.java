package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.business.domain.AccountFlow;
import com.zyj.p2p.business.domain.Bid;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.domain.RechargeOffline;
import com.zyj.p2p.business.mapper.AccountFlowMapper;
import com.zyj.p2p.business.service.AccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private AccountFlow createBaseFlow(Account account) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setTradeTime(new Date());
        flow.setUsableAmount(account.getUsableAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        return flow;
    }

    @Override
    public void bid(Bid bid, Account account) {
        AccountFlow flow = createBaseFlow(account);
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED);
        flow.setAmount(bid.getAvailableAmount());
        flow.setNote("投标" + bid.getBidRequestTitle() + ",冻结账户余额:"
                + bid.getAvailableAmount());
        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void returnMoney(Bid bid, Account bidAccount) {
        AccountFlow flow = createBaseFlow(bidAccount);
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        flow.setAmount(bid.getAvailableAmount());
        flow.setNote("投标" + bid.getBidRequestTitle() + ",满审拒绝退款:"
                + bid.getAvailableAmount());
        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void borrowSuccess(BidRequest br, Account account) {
        AccountFlow flow = createBaseFlow(account);
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        flow.setAmount(br.getBidRequestAmount());
        flow.setNote("借款" + br.getTitle() + "成功,收到借款金额:"
                + br.getBidRequestAmount());
        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void borrowChargeFee(BigDecimal manageChargeFee, BidRequest br,
                                Account account) {
        AccountFlow flow = createBaseFlow(account);
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_CHARGE);
        flow.setAmount(manageChargeFee);
        flow.setNote("借款" + br.getTitle() + "成功,支付借款手续费:" + manageChargeFee);
        this.accountFlowMapper.insert(flow);
    }

    @Override
    public void bidSuccess(Bid bid, Account account) {
        AccountFlow flow = createBaseFlow(account);
        flow.setAccountType(BidConst.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL);
        flow.setAmount(bid.getAvailableAmount());
        flow.setNote("投标" + bid.getBidRequestTitle() + "成功,取消投标冻结金额:"
                + bid.getAvailableAmount());
        this.accountFlowMapper.insert(flow);
    }
}
