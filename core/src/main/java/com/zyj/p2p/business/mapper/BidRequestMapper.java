package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);

    int queryForCount(BidRequestQueryObject qo);

    List<BidRequest> query(BidRequestQueryObject qo);
}