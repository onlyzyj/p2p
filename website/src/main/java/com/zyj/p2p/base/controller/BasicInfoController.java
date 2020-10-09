package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.SystemDictionaryService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户个人资料填写
 * @author onlyzyj
 * @date 2020/9/28-20:46
 */
@Controller
public class BasicInfoController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequireLogin
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        model.addAttribute("userinfo",userinfoService.get(UserContext.getCurrent().getId()));
        //添加:所有下拉列表相关内容
        model.addAttribute("educationBackgrounds",this.systemDictionaryService.listByParentSn("educationBackground"));
        model.addAttribute("incomeGrades",this.systemDictionaryService.listByParentSn("incomeGrade"));
        model.addAttribute("marriages",this.systemDictionaryService.listByParentSn("marriage"));
        model.addAttribute("kidCounts",this.systemDictionaryService.listByParentSn("kidCount"));
        model.addAttribute("houseConditions",this.systemDictionaryService.listByParentSn("houseCondition"));
        return "userInfo";
    }

    @RequireLogin
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(Userinfo userinfo){
        this.userinfoService.updateBasicInfo(userinfo);
        return new JSONResult();
    }
}
