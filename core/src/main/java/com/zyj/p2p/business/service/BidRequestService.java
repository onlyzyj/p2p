package com.zyj.p2p.business.service;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.business.domain.BidRequest;

/**
 * 借款相关
 * @author onlyzyj
 * @date 2020/10/28-16:52
 */
public interface BidRequestService {
    void update(BidRequest bidRequest);

    boolean canApplyBidRequest(Long logininfoId);

    void apply(BidRequest bidRequest);
}
