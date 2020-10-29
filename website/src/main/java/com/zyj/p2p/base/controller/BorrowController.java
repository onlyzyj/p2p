package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author onlyzyj
 * @date 2020/9/25-14:21
 */
@Controller
public class BorrowController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BidRequestService bidRequestService;

    @RequestMapping("borrow")
    public String borrow(Model model){
        Logininfo logininfo = UserContext.getCurrent();
        if (logininfo == null){
            return "redirect:borrow.html";
        }else{
            Userinfo userinfo = userinfoService.get(logininfo.getId());
            Account account = accountService.get(logininfo.getId());
            model.addAttribute("account",account);
            model.addAttribute("userinfo",userinfo);
            model.addAttribute("creditBorrowScore", BidConst.BASE_BORROW_SCORE);
            return "borrow";
        }
    }

    /**
     * 导向到借款申请界面
     * @param model
     * @return
     */
    @RequestMapping("borrowInfo")
    @RequireLogin
    public String borrowInfo(Model model){
        Long id = userinfoService.getCurrent().getId();
        if (bidRequestService.canApplyBidRequest(id)){
            //能够申请借款
            model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BIDREQUEST_AMOUNT);
            model.addAttribute("account",accountService.getCurrent());
            model.addAttribute("minBidAmount",BidConst.SMALLEST_BID_AMOUNT);
            return "borrow_apply";
        }else{
            return "borrow_apply_result";
        }
    }

    @RequestMapping("borrow_apply")
    public String borrowApply(BidRequest bidRequest){
        //这里的bidRequest只是用来传值的，在后台最好不要进行赋值修改操作，因为后面可能复用
        bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo.do";
    }
}
