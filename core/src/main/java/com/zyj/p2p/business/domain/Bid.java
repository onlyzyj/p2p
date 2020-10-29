package com.zyj.p2p.business.domain;

import com.zyj.p2p.base.domain.BaseDomian;
import com.zyj.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象
 */
@Setter@Getter
public class Bid extends BaseDomian {
    private BigDecimal actualRate;// 年化利率(等同于bidrequest上的currentRate)
    private BigDecimal availableAmount;// 这次投标金额
    private Long bidRequestId;// 关联借款
    private String bidRequestTitle;// 冗余数据,等同于借款标题
    private Logininfo bidUser;// 投标人
    private Date bidTime;// 投标时间
    private int bidRequestState;// 不保存到数据库中,只供查询使用
}