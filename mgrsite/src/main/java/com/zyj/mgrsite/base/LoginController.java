package com.zyj.mgrsite.base;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.service.LogininfoService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author onlyzyj
 * @date 2020/9/22-14:39
 */
@Controller
public class LoginController {
    @Autowired
    private LogininfoService logininfoService;

    @ResponseBody
    @RequestMapping("login")
    public JSONResult login(String username, String password, HttpServletRequest request){
        Logininfo current = logininfoService.login(username,password, request.getRemoteAddr(), Logininfo.USER_MANAGER);
        JSONResult result = new JSONResult();
        if (current == null){
            result.setSuccess(false);
            result.setMsg("用户名或密码错误");
            return result;
        }else{
            return result;
        }
    }
}
