package com.zyj.p2p.business.service;

import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.business.domain.PlatformBankInfo;
import com.zyj.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/11/2-11:09
 */
public interface PlatformBankInfoService {
    PageResult query(PlatformBankInfoQueryObject qo);

    void saveOrUpdate(PlatformBankInfo bankInfo);

    List<PlatformBankInfo> listAll();
}
