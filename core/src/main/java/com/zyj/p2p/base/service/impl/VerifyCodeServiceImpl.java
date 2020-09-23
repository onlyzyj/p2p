package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.service.VerifyCodeService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.DateUtil;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.base.vo.VerifyCodeVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author onlyzyj
 * @date 2020/9/23-17:09
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Override
    public void sendVerifyCode(String phoneNumber) {
        // 判断当前是否能够发送短信
        // 从session中获取最后一次发送短信的时间
        VerifyCodeVO vc = UserContext.getVerifyCodeVO();
        if(vc == null || DateUtil.secondsBetween(new Date(),vc.getLastSendTime())>90){
            String verifyCode = UUID.randomUUID().toString().substring(0,4);
            vc = new VerifyCodeVO();
            vc.setLastSendTime(new Date());
            vc.setPhoneNumber(phoneNumber);
            vc.setVerifyCode(verifyCode);
            UserContext.putVerifyCodeVO(vc);
            System.out.println("###########################################");
            System.out.println(verifyCode);
            System.out.println("###########################################");
        }else{
            throw new RuntimeException("操作过于频繁");
        }
    }

    @Override
    public boolean verify(String phoneNumber, String verifyCode) {
        VerifyCodeVO vc = UserContext.getVerifyCodeVO();
        if (vc != null
                && vc.getPhoneNumber().equals(phoneNumber)
                && vc.getVerifyCode().equalsIgnoreCase(verifyCode)
                && DateUtil.secondsBetween(new Date(),vc.getLastSendTime()) <= BidConst.VERIFY_VALIDATE_SECOND
        ){
         return true;
        }else{
            return false;
        }
    }
}
