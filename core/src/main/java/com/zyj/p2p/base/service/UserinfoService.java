package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.Userinfo;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:41
 */
public interface UserinfoService {
    void update(Userinfo userinfo);

    void add(Userinfo userinfo);

    Userinfo get(Long id);

    void bindPhone(String phoneNumber, String verifyCode);
}

