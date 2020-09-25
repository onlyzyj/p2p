package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
