package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.VedioAuth;
import com.zyj.p2p.base.query.VedioAuthQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VedioauthMapper {
    int insert(VedioAuth record);

    VedioAuth selectByPrimaryKey(Long id);

    /**
     * 分页查询相关
     */
    int queryForCount(VedioAuthQueryObject qo);
    List<VedioAuth> query(VedioAuthQueryObject qo);
}