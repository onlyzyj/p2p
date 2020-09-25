package com.zyj.p2p.base.domain;

/**
 * 邮箱验证对象
 */

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class MailVerify extends BaseDomian{
    private Long userinfoId;
    private String uuid;
    private String email;
    private Date sendDate;
}