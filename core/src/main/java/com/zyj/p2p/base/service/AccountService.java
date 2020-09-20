package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.Account;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:36
 */
public interface AccountService {
    /**
     * 写完mapper之后立刻写service，因为这个update是支持乐观锁的
     */
    void update(Account account);

    void add(Account account);
}
