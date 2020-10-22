package com.zyj.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class RealAuth extends BaseDomian{
    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    public static final int STATE_NORMAL = 0;//提交了认证但没审核
    public static final int STATE_AUDIT = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核失败

    private String realName;
    private int sex;
    private String idNumber;// 证件号码
    private String bornDate;// 出生日期
    private String address;// 证件地址
    private String image1;// 身份证正面图片地址
    private String image2;// 身份证反面图片地址

    private String remark;//审核备注
    private  int state;//状态
    private Logininfo applier;//申请人
    private Logininfo auditor;//审核人
    private Date applyTime;//申请时间
    private Date auditTime;//审核时间

    public String getSexDisplay(){
        return sex == SEX_MALE ? "男":"女";
    }

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