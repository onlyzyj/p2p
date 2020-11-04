package com.zyj.p2p.business.query;

import com.zyj.p2p.base.query.AuditQueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @author onlyzyj
 * @date 2020/11/2-16:16
 */
@Setter@Getter
public class RechargeOfflineQueryObject extends AuditQueryObject {
    private Long applierId;
    private long bankInfoId = -1;// 按照开户行查询
    private String tradeCode;

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode) ? tradeCode : null;
    }
}
