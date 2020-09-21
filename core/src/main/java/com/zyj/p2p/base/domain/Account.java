package com.zyj.p2p.base.domain;

import com.zyj.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

/**
 * 用户对应的账户信息
 */

@Getter@Setter@ToString
public class Account extends BaseDomian {

    private int version;// 版本号
    private String tradePassword;
    private BigDecimal usableAmount = BidConst.ZERO;
    private BigDecimal freezedAmount = BidConst.ZERO;
    private BigDecimal unReceiveInterest = BidConst.ZERO;
    private BigDecimal unReceivePrincipal = BidConst.ZERO;
    private BigDecimal unReturnAmount = BidConst.ZERO;
    private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT;
    private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT;

    private String verifyCode;// 做数据校验的

    public BigDecimal getTotalAmount(){
        return usableAmount.add(freezedAmount).add(unReceivePrincipal);
    }

}
