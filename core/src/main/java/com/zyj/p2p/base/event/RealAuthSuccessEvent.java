package com.zyj.p2p.base.event;

import com.zyj.p2p.base.domain.RealAuth;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author onlyzyj
 * @date 2020/11/12-11:31
 */
@Getter
public class RealAuthSuccessEvent extends ApplicationEvent {

    /**
     * 事件关联的对象
     */
    private RealAuth realAuth;

    public RealAuthSuccessEvent(Object source,RealAuth RealAuth) {
        super(source);
        this.realAuth = RealAuth;
    }
}
