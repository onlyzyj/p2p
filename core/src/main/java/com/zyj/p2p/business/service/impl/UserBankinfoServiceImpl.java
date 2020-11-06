package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.UserBankinfo;
import com.zyj.p2p.business.mapper.UserBankinfoMapper;
import com.zyj.p2p.business.service.UserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author onlyzyj
 * @date 2020/11/6-16:35
 */
@Service
public class UserBankinfoServiceImpl implements UserBankinfoService {

    @Autowired
    private UserBankinfoMapper userBankinfoMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Override
    public UserBankinfo getByUser(Long id) {
        return this.userBankinfoMapper.selectByUser(id);
    }

    @Override
    public void bind(UserBankinfo bankInfo) {
        // 判断当前用户没有绑定;
        Userinfo current = this.userinfoService.getCurrent();
        if (!current.getIsBindBank() && current.getIsRealAuth()) {
            // 创建一个UserBankinfo,设置相关属性;
            UserBankinfo b = new UserBankinfo();
            b.setAccountName(current.getRealName());
            b.setAccountNumber(bankInfo.getAccountNumber());
            b.setBankForkName(bankInfo.getBankForkName());
            b.setBankName(bankInfo.getBankName());
            b.setLogininfo(UserContext.getCurrent());
            this.userBankinfoMapper.insert(b);
            // 修改用户状态码
            current.addState(BitStatesUtils.OP_BIND_BANKINFO);
            this.userinfoService.update(current);
        }
    }

}
