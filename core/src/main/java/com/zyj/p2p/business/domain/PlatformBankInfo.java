package com.zyj.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.zyj.p2p.base.domain.BaseDomian;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class PlatformBankInfo extends BaseDomian {
    private String bankName;
    private String accountName;//开户人姓名
    private String accountNumber;//银行账号
    private String bankForkName;//开户支行

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("bankName", this.bankName);
        json.put("accountName", accountName);
        json.put("accountNumber", accountNumber);
        json.put("bankForkName", bankForkName);
        return JSONObject.toJSONString(json);
    }
}