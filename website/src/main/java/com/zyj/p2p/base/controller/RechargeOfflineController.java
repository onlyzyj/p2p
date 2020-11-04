package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.RechargeOffline;
import com.zyj.p2p.business.query.RechargeOfflineQueryObject;
import com.zyj.p2p.business.service.PlatformBankInfoService;
import com.zyj.p2p.business.service.RechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台充值
 *
 * @author onlyzyj
 * @date 2020/11/2-15:30
 */
@Controller
public class RechargeOfflineController {

    @Autowired
    private PlatformBankInfoService platformBankInfoService;

    @Autowired
    private RechargeOfflineService rechargeOfflineService;

    @RequestMapping("recharge")
    @RequireLogin
    public String recharge(Model model){
        model.addAttribute("banks",platformBankInfoService.listAll());
        return "recharge";
    }

    /**
     * 提交线下充值单
     *
     * @return
     */
    @RequireLogin
    @RequestMapping("recharge_save")
    @ResponseBody
    public JSONResult rechargeApply(RechargeOffline recharge) {
        this.rechargeOfflineService.apply(recharge);
        return new JSONResult();
    }


    @RequireLogin
    @RequestMapping("recharge_list")
    public String rechargeList(
            @ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model) {
        qo.setApplierId(UserContext.getCurrent().getId());
        model.addAttribute("pageResult", this.rechargeOfflineService.query(qo));
        return "recharge_list";
    }
}
