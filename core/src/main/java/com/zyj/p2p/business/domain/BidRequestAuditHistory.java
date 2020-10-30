package com.zyj.p2p.business.domain;

import com.zyj.p2p.base.domain.BaseAuditDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class BidRequestAuditHistory extends BaseAuditDomain {
    public static final int PUBLISH_AUDIT = 0;//发标前审核
    public static final int FULL_AUDIT_1 = 1;//满标一审
    public static final int FULL_AUDIT_2 = 2;//满标二审

    private Long bidRequestId;
    private int auditType;

    public String getAuditTypeDisplay() {
        switch (this.auditType) {
            case PUBLISH_AUDIT:
                return "发标前审核";
            case FULL_AUDIT_1:
                return "满标一审";
            case FULL_AUDIT_2:
                return "满标二审";
            default:
                return "";
        }
    }
}