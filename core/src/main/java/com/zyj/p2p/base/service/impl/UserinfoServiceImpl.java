package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.UserinfoMapper;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.service.VerifyCodeService;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/9/19-11:42
 */
@Service
public class UserinfoServiceImpl implements UserinfoService {

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
}
