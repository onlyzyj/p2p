package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.query.RealAuthQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealAuthMapper {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    /**
     * 分页查询相关
     */
    int queryForCount(@Param("qo") RealAuthQueryObject qo);
    List<RealAuth> query(@Param("qo") RealAuthQueryObject qo);
}