package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.service.LogininfoService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author onlyzyj
 * @date 2020/9/16-16:45
 */
@Controller
public class RegisterController {

    @Autowired
    private LogininfoService logininfoService;

    @RequestMapping("/register")
    @ResponseBody
    public JSONResult register(String username, String password) {
        JSONResult result = new JSONResult();
        try {
            logininfoService.register(username, password);
        } catch (RuntimeException re) {
            result.setSuccess(false);
            result.setMsg(re.getMessage());
        }
        return result;
    }

    @RequestMapping("checkUsername")
    @ResponseBody
    public boolean checkUsername(String username) {
        int count = logininfoService.getCountByUsername(username);
        return count < 1;
    }

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password, HttpServletRequest request) {
        JSONResult result = new JSONResult();
        Logininfo current = logininfoService.login(username, password, request.getRemoteAddr(),Logininfo.USER_CLIENT);
        if (current == null) {
            result.setSuccess(false);
            result.setMsg("用户名或者密码错误");
        }
        return result;
    }

}
