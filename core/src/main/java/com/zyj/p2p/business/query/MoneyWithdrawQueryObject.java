package com.zyj.p2p.business.query;

import com.zyj.p2p.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author onlyzyj
 * @date 2020/11/6-21:23
 */
@Getter
@Setter
public class MoneyWithdrawQueryObject extends AuditQueryObject {

    private Long applierId;
}
