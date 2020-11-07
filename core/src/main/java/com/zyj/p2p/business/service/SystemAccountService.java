package com.zyj.p2p.business.service;

import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.domain.MoneyWithdraw;
import com.zyj.p2p.business.domain.PaymentScheduleDetail;
import com.zyj.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * @author onlyzyj
 * @date 2020/11/5-16:23
 */
public interface SystemAccountService {
    void update(SystemAccount systemAccount);

    /**
     * 检查并初始化系统账户
     */
    void initAccount();

    /**
     * 系统账户收到借款管理费
     *
     * @param br
     * @param manageChargeFee
     */
    void chargeBorrowFee(BidRequest br, BigDecimal manageChargeFee);


    /**
     * 系统账户收取提现手续费
     *
     * @param charge
     */
    void chargeWithdrawFee(MoneyWithdraw m);

    /**
     * 系统账户手续利息管理费
     *
     * @param psd
     * @param interestChargeFee
     */
    void chargeInterestFee(PaymentScheduleDetail psd,
                           BigDecimal interestChargeFee);
}
