package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.service.BidRequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author onlyzyj
 * @date 2020/10/30-11:26
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
public class BidRequestServiceImplTest {
    @Autowired
    private BidRequestService bidRequestService;

    @Test
    public void get() {
        BidRequest bidRequest = bidRequestService.get(new Long(6));
        System.out.println(bidRequest.getPersent());
    }
}