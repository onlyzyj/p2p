package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.Iplog;
import com.zyj.p2p.base.query.IplogQueryObject;

import java.util.List;

public interface IplogMapper {
    int insert(Iplog record);

    List<Iplog> selectAll();

    int queryForCount(IplogQueryObject qo);

    List<Iplog> query(IplogQueryObject qo);
}