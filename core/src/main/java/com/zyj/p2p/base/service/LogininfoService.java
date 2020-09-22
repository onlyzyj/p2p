package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.Logininfo;

/**
 * @author onlyzyj
 * @date 2020/9/15-21:05
 */
public interface LogininfoService {

    void register(String username,String password);

    int getCountByUsername(String username);

    Logininfo login(String username, String password, String remoteAddr, int userClient);

    //Spring容器启动时，若没有Admin超级管理员则生成一个超级管理员
    void initAdmin();
}
