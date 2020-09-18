package com.zyj.p2p.base.service;

/**
 * @author onlyzyj
 * @date 2020/9/15-21:05
 */
public interface LogininfoService {

    void register(String username,String password);

    int getCountByUsername(String username);

    void login(String username, String password);
}
