package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.mapper.AccountMapper;
import com.zyj.p2p.base.mapper.UserinfoMapper;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author onlyzyj
 * @date 2020/9/20-20:13
 */
@Controller
public class PersonalController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @RequireLogin
    @RequestMapping("personal")
    public String personal(Model model){
        Logininfo current = UserContext.getCurrent();
        model.addAttribute("userinfo",userinfoService.get(current.getId()));
        model.addAttribute("account",accountService.get(current.getId()));
        return "personal";
    }

}
