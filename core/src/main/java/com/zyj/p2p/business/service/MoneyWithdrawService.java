package com.zyj.p2p.business.service;

import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.business.query.MoneyWithdrawQueryObject;

import java.math.BigDecimal;

/**
 * @author onlyzyj
 * @date 2020/11/6-20:43
 */
public interface MoneyWithdrawService {
    void apply(BigDecimal moneyAmount);

    PageResult query(MoneyWithdrawQueryObject qo);

    void audit(Long id, String remark, int state);
}
