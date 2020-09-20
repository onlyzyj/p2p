package com.zyj.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户相关信息
 *
 */

@Setter@Getter@ToString
public class Userinfo extends BaseDomian {
    private int version;// 版本号
    private long bitState = 0;// 用户状态码
    private String realName;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private int score;// 风控累计分数;
    private Long realAuthId;// 该用户对应的实名认证对象id

    private SystemDictionaryItem incomeGrade;// 收入
    private SystemDictionaryItem marriage;//
    private SystemDictionaryItem kidCount;//
    private SystemDictionaryItem educationBackground;//
    private SystemDictionaryItem houseCondition;//

}