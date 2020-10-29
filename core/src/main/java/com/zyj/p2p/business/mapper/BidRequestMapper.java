package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.BidRequest;
import java.util.List;

public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);
}