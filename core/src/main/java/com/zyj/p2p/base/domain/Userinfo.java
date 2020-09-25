package com.zyj.p2p.base.domain;

import com.zyj.p2p.base.util.BitStatesUtils;
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

    public boolean getIsBindPhone(){
        return phoneNumber != null;
    }

    public boolean getIsBindEmail(){
        return email != null;
    }

    /**
     * 添加状态码
     * @param opBindPhone
     */
    public void addState(Long opBindPhone) {
        setBitState(BitStatesUtils.addState(bitState,opBindPhone));
    }

    /**
     * 返回用户是否已经填写了基本资料
     *
     * @return
     */
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 返回用户是否已经实名认证
     *
     * @return
     */
    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 返回用户是否视频认证
     *
     * @return
     */
    public boolean getIsVedioAuth() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_VEDIO_AUTH);
    }

}