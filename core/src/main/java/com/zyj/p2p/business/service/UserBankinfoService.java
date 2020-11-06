package com.zyj.p2p.business.service;

import com.zyj.p2p.business.domain.UserBankinfo;

/**
 * @author onlyzyj
 * @date 2020/11/6-16:34
 */
public interface UserBankinfoService {
    /**
     * 得到当前用户绑定的银行卡信息
     *
     * @param id
     * @return
     */
    UserBankinfo getByUser(Long id);

    /**
     * 绑定银行卡
     *
     * @param bankInfo
     */
    void bind(UserBankinfo bankInfo);
}
