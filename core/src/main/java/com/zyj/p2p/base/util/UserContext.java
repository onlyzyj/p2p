package com.zyj.p2p.base.util;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用于存放当前用户的上下文
 * @author onlyzyj
 * @date 2020/9/18-15:18
 */
public class UserContext {

    public static final String USER_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "verifycode";

    private static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static void putCurrent(Logininfo current){
        getSession().setAttribute(USER_IN_SESSION,current);
    }

    public static Logininfo getCurrent(){
        return (Logininfo) getSession().getAttribute(USER_IN_SESSION);
    }

    public static void putVerifyCodeVO(VerifyCodeVO verifyCodeVO){
        getSession().setAttribute(VERIFYCODE_IN_SESSION,verifyCodeVO);
    }

    public static VerifyCodeVO getVerifyCodeVO(){
        return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }
}
