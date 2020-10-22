package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.RealAuthService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import com.zyj.p2p.base.util.UploadUtil;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * @author onlyzyj
 * @date 2020/10/14-10:57
 */
@Controller
public class RealAuthController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("realAuth")
    @RequireLogin
    public String realAuth (Model model){
        //1.得到当前Userinfo
        Userinfo current = this.userinfoService.get(UserContext.getCurrent().getId());
        //2.如果用户已经实名认证
        if (current.getIsRealAuth()){
            //      根据userinfo的realAuthId得到实名认证对象并放到model
            model.addAttribute("realAuth",realAuthService.get(current.getRealAuthId()));
            //      auditing=false
            model.addAttribute("auditing",false);
            return "realAuth_result";
        }else{
            //3.如果用户没有实名认证
            if (current.getRealAuthId() != null){
                //      > userinfo上有realAuthId，auditing=true
                model.addAttribute("auditing",true);
                return "realAuth_result";
            }else{
                //      > userinfo上没有realAuthId
                return "realAuth";
            }
        }
    }


    @RequestMapping("realAuthUpload")
    @ResponseBody
    public String realAuthUpload(MultipartFile file){
        // 先得到basepath
        String basePath = servletContext.getRealPath("/upload");
        String filename = UploadUtil.upload(file,basePath);
        return "upload/"+filename;
    }

    /**
     * 申请实名认证
     */
    @ResponseBody
    @RequireLogin
    @RequestMapping("realAuth_save")
    public JSONResult realAuthSave(RealAuth realAuth){
        realAuthService.apply(realAuth);
        return new JSONResult();
    }
}
