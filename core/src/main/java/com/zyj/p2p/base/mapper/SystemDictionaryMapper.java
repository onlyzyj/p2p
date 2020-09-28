package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.SystemDictionary;
import com.zyj.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    /**
     * 分页的方法
     */
    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> query(SystemDictionaryQueryObject qo);

}