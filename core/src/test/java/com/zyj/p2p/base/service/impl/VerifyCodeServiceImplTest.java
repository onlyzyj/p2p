package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.service.VerifyCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author onlyzyj
 * @date 2020/9/23-21:45
 */
//这两个注释不注释掉的话，每次打包都会运行这些测试
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
public class VerifyCodeServiceImplTest {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Test
    public void showTest(){
        verifyCodeService.sendVerifyCode("sfsdf");
    }

}