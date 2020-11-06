package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.business.domain.UserBankinfo;
import com.zyj.p2p.business.service.UserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author onlyzyj
 * @date 2020/11/6-16:37
 */
@Controller
public class UserBankinfoController {
    @Autowired
    private UserBankinfoService userBankinfoService;

    @Autowired
    private UserinfoService userinfoService;

    /**
     * 导向到绑定银行卡界面
     */
    @RequireLogin
    @RequestMapping("bankInfo")
    public String bankInfo(Model model) {
        Userinfo current = userinfoService.getCurrent();
        if (!current.getIsBindBank()) {
            // 没有绑定
            model.addAttribute("userinfo", current);
            return "bankInfo";
        } else {
            model.addAttribute("bankInfo",
                    userBankinfoService.getByUser(current.getId()));
            return "bankInfo_result";
        }
    }

    /**
     * 执行绑定
     */
    @RequireLogin
    @RequestMapping("bankInfo_save")
    public String bankInfoSave(UserBankinfo bankInfo) {
        this.userBankinfoService.bind(bankInfo);
        return "redirect:/bankInfo.do";
    }
}
