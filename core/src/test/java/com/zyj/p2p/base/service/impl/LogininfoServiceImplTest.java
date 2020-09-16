package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.service.LogininfoService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author onlyzyj
 * @date 2020/9/16-11:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class LogininfoServiceImplTest extends TestCase {

    @Autowired
    private LogininfoService logininfoService;

    @Test
    public void testShow(){
        logininfoService.register("19","12");
    }

}