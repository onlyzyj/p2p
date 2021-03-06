package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.Userinfo;

import java.util.List;
import java.util.Map;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:41
 */
public interface UserinfoService {
    void update(Userinfo userinfo);

    void add(Userinfo userinfo);

    Userinfo get(Long id);

    void bindPhone(String phoneNumber, String verifyCode);

    void sendEmail(String email);

    void bindEmail(String uuid);

    void updateBasicInfo(Userinfo userinfo);

    Userinfo getCurrent();

    List<Map<String, Object>> autoComplate(String keyword);
}

