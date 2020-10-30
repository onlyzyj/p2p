package com.zyj.p2p.business.service;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.domain.BidRequestAuditHistory;
import com.zyj.p2p.business.query.BidRequestQueryObject;

import java.util.List;

/**
 * 借款相关
 * @author onlyzyj
 * @date 2020/10/28-16:52
 */
public interface BidRequestService {
    void update(BidRequest bidRequest);

    boolean canApplyBidRequest(Long logininfoId);

    void apply(BidRequest bidRequest);

    PageResult query(BidRequestQueryObject qo);

    void publishAudit(Long id, String remark, int state);

    BidRequest get(Long id);

    /**
     * 根据一个标查询该标的审核历史
     *
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id);
}
