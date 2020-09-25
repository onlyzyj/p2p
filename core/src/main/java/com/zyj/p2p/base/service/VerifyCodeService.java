package com.zyj.p2p.base.service;

/**
 * @author onlyzyj
 * @date 2020/9/23-17:09
 */
public interface VerifyCodeService {
    void sendVerifyCode(String phoneNumber);

    boolean verify(String phoneNumber, String verifyCode);

}
