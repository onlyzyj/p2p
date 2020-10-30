package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.query.UserFileQueryObject;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.RealAuthService;
import com.zyj.p2p.base.service.UserFileService;
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

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private UserFileService userFileService;

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

    /**
     * 前端借款明细
     */
    @RequestMapping("borrow_info")
    public String borrowInfo(Long id,Model model){
        //bidRequest
        BidRequest bidRequest = bidRequestService.get(id);
        if (bidRequest!=null){
            //userInfo
            Userinfo applier = userinfoService.get(bidRequest.getCreateUser().getId());
            //realAuth
            model.addAttribute("realAuth",realAuthService.get(applier.getRealAuthId()));
            //userFiles
            UserFileQueryObject qo = new UserFileQueryObject();
            qo.setApplierId(applier.getId());
            qo.setPageSize(-1);
            qo.setCurrentPage(1);
            model.addAttribute("userFiles",userFileService.queryForList(qo));

            model.addAttribute("bidRequest",bidRequest);
            model.addAttribute("userInfo",applier);

            if (UserContext.getCurrent() != null) {
                // self:当前用户是否是借款人自己
                if (UserContext.getCurrent().getId().equals(applier.getId())) {
                    model.addAttribute("self", true);
                } else {
                    // account
                    model.addAttribute("self", false);
                    model.addAttribute("account",
                            this.accountService.getCurrent());
                }
            } else {
                model.addAttribute("self", false);
            }
        }

        return "borrow_info";
    }
}
