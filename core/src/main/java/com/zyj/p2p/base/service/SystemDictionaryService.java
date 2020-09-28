package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.SystemDictionary;
import com.zyj.p2p.base.domain.SystemDictionaryItem;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/9/25-15:56
 */
public interface SystemDictionaryService {
    /**
     * 数据字典分类分页查询
     *
     * @return
     */
    PageResult queryDics(SystemDictionaryQueryObject qo);

    void updataAndAdd(SystemDictionary systemDictionary);

    PageResult queryItems(SystemDictionaryQueryObject qo);

    List<SystemDictionary> listAllDics();

    void saveAndUpdateItem(SystemDictionaryItem systemDictionaryItem);
}
