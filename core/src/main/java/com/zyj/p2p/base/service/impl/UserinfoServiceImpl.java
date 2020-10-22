package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.MailVerify;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.MailVerifyMapper;
import com.zyj.p2p.base.mapper.UserinfoMapper;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.service.VerifyCodeService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.DateUtil;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:42
 */
@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Value("${mail.hostUrl}")
    private String hostUrl;

    @Autowired
    private MailVerifyMapper mailVerifyMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Override
    public void update(Userinfo userinfo) {
        int ret = userinfoMapper.updateByPrimaryKey(userinfo);
        if (ret == 0){
            throw new RuntimeException("乐观锁失败，Userinfo:"+userinfo.getId());
        }
    }

    @Override
    public void add(Userinfo userinfo) {
        userinfoMapper.insert(userinfo);
    }

    @Override
    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
        // 如果用户没有绑定验证码:
        Userinfo current = get(UserContext.getCurrent().getId());
        if (!current.getIsBindPhone()){
            //验证验证码
            boolean ret = verifyCodeService.verify(phoneNumber,verifyCode);
            //合法,给用户绑定数据
            if (ret){
                current.addState(BitStatesUtils.OP_BIND_PHONE);
                current.setPhoneNumber(phoneNumber);
                update(current);
            }else{
                throw  new RuntimeException("绑定手机失败");
            }
        }
    }

    @Override
    public void sendEmail(String email) {
        //判断当前用户绑没绑定邮箱
        Userinfo current = get(UserContext.getCurrent().getId());
        if (!current.getIsBindEmail()){
            String uuid = UUID.randomUUID().toString();
            try {
                StringBuilder content = new StringBuilder(100)
                        .append("点击<a href='").append(this.hostUrl)
                        .append("bindEmail.do?uuid=").append(uuid)
                        .append("'>这里</a>完成邮箱绑定,有效期为")
                        .append(BidConst.VERIFYEMAIL_VAILDATE_DAY).append("天");
                System.out.println("发送邮件"+email+"发送内容"+content);
                MailVerify mailVerify = new MailVerify();
                mailVerify.setEmail(email);
                mailVerify.setSendDate(new Date());
                mailVerify.setUserinfoId(current.getId());
                mailVerify.setUuid(uuid);
                mailVerifyMapper.insert(mailVerify);
            }catch (Exception re){
                re.printStackTrace();
                throw new RuntimeException("邮件发送失败");
            }
        }
    }

    @Override
    public void bindEmail(String uuid) {
        MailVerify mailVerify = mailVerifyMapper.selectByUUID(uuid);
        if (mailVerify != null){
            //判断当前用户绑没绑定邮箱
            Userinfo current = get(mailVerify.getUserinfoId());
            if (!current.getIsBindEmail()){
                //判断有效期
                if (DateUtil.secondsBetween(new Date(),mailVerify.getSendDate())<= BidConst.VERIFYEMAIL_VAILDATE_DAY * 24 * 3600){
                    current.setEmail(mailVerify.getEmail());
                    current.addState(BitStatesUtils.OP_BIND_EMAIL);
                    update(current);
                    return;
                }
            }
        }
        throw new RuntimeException("绑定失败");
    }

    @Override
    public void updateBasicInfo(Userinfo userinfo) {
        Userinfo old = get(UserContext.getCurrent().getId());
        // 拷贝这次要修改的内容
        old.setEducationBackground(userinfo.getEducationBackground());
        old.setHouseCondition(userinfo.getHouseCondition());
        old.setIncomeGrade(userinfo.getIncomeGrade());
        old.setKidCount(userinfo.getKidCount());
        old.setMarriage(userinfo.getMarriage());
        // 判断用户是否第一次完善用户信息，是的话给其添加状态码
        if (!old.getIsBasicInfo()) {
            old.addState(BitStatesUtils.OP_BASIC_INFO);
        }
        update(old);
    }

    @Override
    public Userinfo getCurrent() {
        return get(UserContext.getCurrent().getId());
    }
}
