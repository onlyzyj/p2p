package com.zyj.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.zyj.p2p.base.domain.BaseAuditDomain;
import com.zyj.p2p.base.domain.BaseDomian;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter@Setter
public class RechargeOffline extends BaseAuditDomain {
    private PlatformBankInfo bankInfo;
    private String tradeCode;//交易号
    private BigDecimal amount;//充值金额
    private String note;//充值说明
    private Date tradeTime;//充值时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("username", this.applier.getUsername());
        json.put("tradeCode", tradeCode);
        json.put("amount", amount);
        json.put("tradeTime", tradeTime);
        return JSONObject.toJSONString(json);
    }
}