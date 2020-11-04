package com.zyj.mgrsite.base;

import com.zyj.p2p.business.domain.PlatformBankInfo;
import com.zyj.p2p.business.query.PlatformBankInfoQueryObject;
import com.zyj.p2p.business.service.PlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author onlyzyj
 * @date 2020/11/2-11:18
 */
@Controller
public class PlatformBankInfoController {

    @Autowired
    private PlatformBankInfoService platformBankInfoService;

    @RequestMapping("companyBank_list")
    public String platformCompanyBankList(@ModelAttribute("qo")PlatformBankInfoQueryObject qo, Model model){
        model.addAttribute("pageResult",platformBankInfoService.query(qo));
        return "platformbankinfo/list";
    }

    /**
     * 修改或保存
     */
    @RequestMapping("companyBank_update")
    public String update(PlatformBankInfo bankInfo) {
        this.platformBankInfoService.saveOrUpdate(bankInfo);
        return "redirect:/companyBank_list.do";
    }

}
