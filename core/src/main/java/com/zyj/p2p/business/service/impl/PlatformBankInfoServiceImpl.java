package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.business.domain.PlatformBankInfo;
import com.zyj.p2p.business.mapper.PlatformBankInfoMapper;
import com.zyj.p2p.business.query.PlatformBankInfoQueryObject;
import com.zyj.p2p.business.service.PlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/11/2-11:09
 */
@Service
public class PlatformBankInfoServiceImpl implements PlatformBankInfoService {

    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int count = this.platformBankInfoMapper.queryForCount(qo);
        if (count > 0) {
            List<PlatformBankInfo> list = this.platformBankInfoMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(),
                    qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo bankInfo) {
        if (bankInfo.getId()!=null){
            platformBankInfoMapper.updateByPrimaryKey(bankInfo);
        }else{
            platformBankInfoMapper.insert(bankInfo);
        }
    }

    @Override
    public List<PlatformBankInfo> listAll() {
        return platformBankInfoMapper.selectAll();
    }
}
