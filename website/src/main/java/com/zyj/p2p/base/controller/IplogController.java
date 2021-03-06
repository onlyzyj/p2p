package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.query.IplogQueryObject;
import com.zyj.p2p.base.service.IplogService;
import com.zyj.p2p.base.util.RequireLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登陆日志
 * @author onlyzyj
 * @date 2020/9/21-15:44
 */
@Controller
public class IplogController {

    @Autowired
    private IplogService iplogService;

    @RequireLogin
    @RequestMapping("ipLog")
    public String ipLogList(@ModelAttribute("qo")IplogQueryObject qo, Model model){
        model.addAttribute("pageResult",iplogService.query(qo));
        return "iplog_list";
    }
}
