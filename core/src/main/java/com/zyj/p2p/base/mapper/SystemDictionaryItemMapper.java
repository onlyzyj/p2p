package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.SystemDictionaryItem;
import com.zyj.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SystemDictionaryItem record);

    int queryForCount(SystemDictionaryQueryObject qo);
    List<SystemDictionaryItem> query(SystemDictionaryQueryObject qo);
}