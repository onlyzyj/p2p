package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.service.VerifyCodeService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.RequireLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author onlyzyj
 * @date 2020/9/23-16:39
 */
@Controller
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequireLogin
    @ResponseBody
    @RequestMapping("sendVerifyCode")
    public JSONResult phoneVerify(String phoneNumber){
        JSONResult result = new JSONResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
        }catch (RuntimeException exception){
            result.setSuccess(false);
            result.setMsg(exception.getMessage());
            System.out.println("****************false***********************");
        }
        return result;
    }

}
