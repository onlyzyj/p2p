package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Logininfo;
import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.domain.VedioAuth;
import com.zyj.p2p.base.mapper.VedioauthMapper;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.VedioAuthQueryObject;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.service.VedioAuthService;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/10/23-11:13
 */
@Service
public class VedioAuthServiceImpl implements VedioAuthService {

    @Autowired
    private VedioauthMapper vedioauthMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Override
    public PageResult query(VedioAuthQueryObject qo) {
        int count = vedioauthMapper.queryForCount(qo);
        if (count > 0) {
            List<VedioAuth> list = vedioauthMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long loginInfoValue, String remark, int state) {
        // 判断用户没有视频认证
        Userinfo user = this.userinfoService.get(loginInfoValue);
        if (user != null && !user.getIsVedioAuth()) {
            // 添加一个视频认证对象,设置相关属性
            VedioAuth va = new VedioAuth();

            Logininfo applier = new Logininfo();
            applier.setId(loginInfoValue);
            va.setApplier(applier);
            va.setApplyTime(new Date());
            va.setAuditor(UserContext.getCurrent());
            va.setAuditTime(new Date());
            va.setRemark(remark);
            va.setState(state);
            vedioauthMapper.insert(va);

            if (state == VedioAuth.STATE_AUDIT) {
                // 如果状态审核通过,修改用户状态码
                user.addState(BitStatesUtils.OP_VEDIO_AUTH);
                this.userinfoService.update(user);
            }
        }
    }
}
