package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.mapper.AccountMapper;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:38
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void update(Account account) {
        int ret = this.accountMapper.updateByPrimaryKey(account);
        if (ret == 0){
            throw new RuntimeException("乐观锁失败,Account:"+account.getId());
        }
    }

    @Override
    public void add(Account account) {
        accountMapper.insert(account);
    }

    @Override
    public Account get(Long id) {
        Account account = this.accountMapper.selectByPrimaryKey(id);
        if (!account.checkVerifyCode()) {
            throw new RuntimeException("账户 :" + id + " 资金数据异常,阻止业务继续进行");
        }
        return account;
    }

    @Override
    public Account getCurrent() {
        return get(UserContext.getCurrent().getId());
    }
}
