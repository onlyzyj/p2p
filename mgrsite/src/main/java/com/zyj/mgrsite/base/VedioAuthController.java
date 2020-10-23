package com.zyj.mgrsite.base;

import com.zyj.p2p.base.query.VedioAuthQueryObject;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.service.VedioAuthService;
import com.zyj.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author onlyzyj
 * @date 2020/10/23-11:19
 */
@Controller
public class VedioAuthController {

    @Autowired
    private VedioAuthService vedioAuthService;

    @Autowired
    private UserinfoService userinfoService;

    @RequestMapping("vedioAuth")
    public String vedioAuth(@ModelAttribute("qo")VedioAuthQueryObject qo, Model model){
        model.addAttribute("pageResult",vedioAuthService.query(qo));
        return "vedioAuth/list";
    }

    /**
     * 完成视频审核
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuthAudhit(Long loginInfoValue,String remark,int state){
        vedioAuthService.audit(loginInfoValue,remark,state);
        return new JSONResult();
    }

    /**
     * 完成自动补全
     */
    @RequestMapping("vedioAuth_autocomplate")
    @ResponseBody
    public List<Map<String,Object>> autoComplate(String keyword){
        return userinfoService.autoComplate(keyword);
    }
}
