package com.zyj.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础的审核对象
 *
 * @author onlyzyj
 * @date 2020/10/23-10:07
 */
@Setter
@Getter
abstract public class BaseAuditDomain  extends BaseDomian{
    public static final int STATE_NORMAL = 0;//提交了认证但没审核
    public static final int STATE_AUDIT = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核失败

    protected String remark;//审核备注
    protected  int state;//状态
    protected Logininfo applier;//申请人
    protected Logininfo auditor;//审核人
    protected Date applyTime;//申请时间
    protected Date auditTime;//审核时间

    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL:
                return "待审核";
            case STATE_AUDIT:
                return "审核通过";
            case STATE_REJECT:
                return "审核拒绝";
            default:
                return "";
        }
    }
}
