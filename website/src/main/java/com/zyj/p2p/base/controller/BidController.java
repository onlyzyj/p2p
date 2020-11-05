package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投标
 *
 * @author onlyzyj
 * @date 2020/11/5-10:56
 */
@Controller
public class BidController {

    @Autowired
    private BidRequestService bidRequestService;

    @RequireLogin
    @RequestMapping("borrow_bid")
    @ResponseBody
    public JSONResult bid(Long bidRequestId, BigDecimal amount) {
        this.bidRequestService.bid(bidRequestId, amount);
        return new JSONResult();
    }

}
