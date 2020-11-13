package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.mapper.AccountMapper;
import com.zyj.p2p.base.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author onlyzyj
 * @date 2020/11/13-17:25
 */
//这两个注释不注释掉的话，每次打包都会运行这些测试
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:applicationContext.xml")
public class AccountServiceImplTest {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testOne(){
        List<Account> accounts = accountMapper.selectAll();
        for (Account account : accounts) {
            accountMapper.updateByPrimaryKey(account);
        }
    }


}