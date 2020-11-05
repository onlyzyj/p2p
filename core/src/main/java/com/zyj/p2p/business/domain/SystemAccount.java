package com.zyj.p2p.business.domain;

import com.zyj.p2p.base.domain.BaseDomian;
import com.zyj.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter@Getter
public class SystemAccount extends BaseDomian {
    private int version;// 版本
    private BigDecimal usableAmount = BidConst.ZERO;// 平台账户剩余金额
    private BigDecimal freezedAmount = BidConst.ZERO;// 平台账户冻结金额
}