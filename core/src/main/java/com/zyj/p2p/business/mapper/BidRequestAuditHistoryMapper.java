package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.domain.BidRequestAuditHistory;
import com.zyj.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestAuditHistoryMapper {
    int insert(BidRequestAuditHistory record);

    BidRequestAuditHistory selectByPrimaryKey(Long id);

    List<BidRequestAuditHistory> listByBidRequest(Long id);
}