package com.zyj.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Logininfo extends BaseDomian {

    public static final int STATE_NORMAL = 0;   //正常
    public static final int STATE_LOCK = 1;     //锁定

    public static final int USER_MANAGER = 0;
    public static final int USER_CLIENT = 1;

    private String username;

    private String password;

    private int state;

    private int userType;
}