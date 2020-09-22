package com.zyj.mgrsite.base;

import com.zyj.p2p.base.query.IplogQueryObject;
import com.zyj.p2p.base.service.IplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author onlyzyj
 * @date 2020/9/22-19:08
 */
@Controller
public class IplogController {

    @Autowired
    private IplogService iplogService;

    @RequestMapping("ipLog")
    public String ipLog(@ModelAttribute("qo")IplogQueryObject qo, Model model){
        System.out.println("##########################################");
        System.out.println(qo.getUsername());
        System.out.println("##########################################");
        model.addAttribute("pageResult",iplogService.query(qo));
        return "ipLog/list";
    }

}
