package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.event.RealAuthSuccessEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/11/12-10:19
 */
@Service
public class SmsServiceImpl implements SmsService,
        ApplicationListener<RealAuthSuccessEvent> {
    @Override
    public void sendSms(RealAuth realAuth) {
        System.out.println("用户"+realAuth.getApplier().getUsername()+"实名认证成功");
    }

    @Override
    public void onApplicationEvent(RealAuthSuccessEvent realAuthSuccessEvent) {
        this.sendSms(realAuthSuccessEvent.getRealAuth());
    }
}
