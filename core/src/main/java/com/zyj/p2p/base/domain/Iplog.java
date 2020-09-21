package com.zyj.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登陆日志
 */

@Setter@Getter
public class Iplog extends BaseDomian{

    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAILED = 0;

    private String ip;
    private Date loginTime;
    private String userName;
    private int state;
    private int userType = 0;

    public String getStateDisplay(){
        return state == STATE_SUCCESS ? "登陆成功" : "登陆失败";
    }
}