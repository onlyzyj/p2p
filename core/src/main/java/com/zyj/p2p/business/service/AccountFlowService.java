package com.zyj.p2p.business.service;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.business.domain.Bid;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.domain.MoneyWithdraw;
import com.zyj.p2p.business.domain.RechargeOffline;

import java.math.BigDecimal;

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

    void bid(Bid bid, Account currentAccount);

    void returnMoney(Bid bid, Account bidAccount);

    void borrowSuccess(BidRequest br, Account borrowAccount);

    void borrowChargeFee(BigDecimal manageChargeFee, BidRequest br, Account borrowAccount);

    void bidSuccess(Bid bid, Account bidAccount);

    void moneyWithDrawApply(MoneyWithdraw m, Account account);

    void withDrawChargeFee(MoneyWithdraw m, Account account);

    void withDrawSuccess(BigDecimal realWithdrawFee, Account account);

    void withDrawFailed(MoneyWithdraw m, Account account);
}
