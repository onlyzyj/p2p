package com.zyj.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author onlyzyj
 * @date 2020/9/23-17:08
 */
@Setter@Getter
public class VerifyCodeVO {

    private String verifyCode;//验证码
    private String phoneNumber;//发送验证码的手机号
    private Date lastSendTime;//最近成功发送时间

}
