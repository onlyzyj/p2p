package com.zyj.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class RealAuth extends BaseAuditDomain{
    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    private String realName;
    private int sex;
    private String idNumber;// 证件号码
    private String bornDate;// 出生日期
    private String address;// 证件地址
    private String image1;// 身份证正面图片地址
    private String image2;// 身份证反面图片地址

    public String getSexDisplay(){
        return sex == SEX_MALE ? "男":"女";
    }

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("applier", this.applier.getUsername());
        json.put("realName", realName);
        json.put("idNumber", idNumber);
        json.put("sex", getSexDisplay());
        json.put("bornDate", bornDate);
        json.put("address", address);
        json.put("image1", image1);
        json.put("image2", image2);
        return JSONObject.toJSONString(json);
    }
}