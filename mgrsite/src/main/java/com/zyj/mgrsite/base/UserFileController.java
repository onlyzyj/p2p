package com.zyj.mgrsite.base;

import com.zyj.p2p.base.query.UserFileQueryObject;
import com.zyj.p2p.base.service.UserFileService;
import com.zyj.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 风控资料审核
 *
 * @author onlyzyj
 * @date 2020/10/26-15:16
 */
@Controller
public class UserFileController {
    @Autowired
    private UserFileService userFileService;

    @RequestMapping("userFileAuth")
    public String userFileAuth(@ModelAttribute("qo")UserFileQueryObject qo, Model model){
        model.addAttribute("pageResult",userFileService.query(qo));
        return "userFileAuth/list";
    }

    @RequestMapping("userFile_audit")
    @ResponseBody
    public JSONResult userFileAudit(Long id,int state,int score,String remark){
        userFileService.audit(id,state,score,remark);
        return new JSONResult();
    }
}
