package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.business.service.MoneyWithdrawService;
import com.zyj.p2p.business.service.UserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author onlyzyj
 * @date 2020/11/6-21:06
 */
@Controller
public class MoneyWithdrawController {
    @Autowired
    private MoneyWithdrawService moneyWithdrawService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private UserBankinfoService userBankinfoService;

    @Autowired
    private AccountService accountService;

    /**
     * 导向到提现申请界面
     */
    @RequireLogin
    @RequestMapping("moneyWithdraw")
    public String moenyWithdraw(Model model) {
        Userinfo current = this.userinfoService.getCurrent();
        model.addAttribute("haveProcessing", current.getHasWithdrawProcess());
        model.addAttribute("bankInfo",
                this.userBankinfoService.getByUser(current.getId()));
        model.addAttribute("account", this.accountService.getCurrent());
        return "moneyWithdraw_apply";
    }

    /*8
     * 提现申请
     */
    @RequireLogin
    @RequestMapping("moneyWithdraw_apply")
    @ResponseBody
    public JSONResult apply(BigDecimal moneyAmount){
        this.moneyWithdrawService.apply(moneyAmount);
        return new JSONResult();
    }
}
