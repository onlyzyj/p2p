package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.RealAuth;

/**
 *
 *
 * @author onlyzyj
 * @date 2020/11/12-10:18
 */
public interface SmsService {
    /**
     * 实名认证成功之后发短信
     * @param realAuth
     */
    void sendSms (RealAuth realAuth);
}
