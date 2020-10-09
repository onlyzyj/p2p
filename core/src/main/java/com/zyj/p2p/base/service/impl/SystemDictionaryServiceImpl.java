package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.SystemDictionary;
import com.zyj.p2p.base.domain.SystemDictionaryItem;
import com.zyj.p2p.base.mapper.SystemDictionaryItemMapper;
import com.zyj.p2p.base.mapper.SystemDictionaryMapper;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.SystemDictionaryQueryObject;
import com.zyj.p2p.base.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/9/25-15:58
 */
@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public PageResult queryDics(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryMapper.queryForCount(qo);
        if (count != 0){
            List<SystemDictionary> data = systemDictionaryMapper.query(qo);
            return new PageResult(data,count,qo.getCurrentPage(),qo.getPageSize());
        }else{
            return new PageResult(new ArrayList<SystemDictionary>(),0,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    @Override
    public void updataAndAdd(SystemDictionary systemDictionary) {
        if (systemDictionary.getId()!=null){
            systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        }else {
            systemDictionaryMapper.insert(systemDictionary);
        }
    }

    @Override
    public PageResult queryItems(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryItemMapper.queryForCount(qo);
        if (count != 0){
            List<SystemDictionaryItem> data = systemDictionaryItemMapper.query(qo);
            return new PageResult(data,count,qo.getCurrentPage(),qo.getPageSize());
        }else{
            return new PageResult(new ArrayList<SystemDictionaryItem>(),0,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    @Override
    public List<SystemDictionary> listAllDics() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public void saveAndUpdateItem(SystemDictionaryItem systemDictionaryItem) {
        if (systemDictionaryItem.getId()!=null){
            systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
        }else {
            systemDictionaryItemMapper.insert(systemDictionaryItem);
        }
    }

    @Override
    public List<SystemDictionaryItem> listByParentSn(String parentSn) {
        return systemDictionaryItemMapper.listByParentSn(parentSn);
    }
}
