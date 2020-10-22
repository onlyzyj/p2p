package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.RealAuth;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.RealAuthMapper;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.RealAuthQueryObject;
import com.zyj.p2p.base.service.RealAuthService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/10/14-11:11
 */
@Service
public class RealAuthServiceImpl implements RealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Override
    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    @Override
    public void apply(RealAuth realAuth) {
        Userinfo current = userinfoService.getCurrent();
        //判断当前用户没有实名认证且当前用户不处于待审核状态
        if (!current.getIsRealAuth() && current.getRealAuthId() == null) {
            //保存一个实名认证对象
            realAuth.setState(RealAuth.STATE_NORMAL);
            realAuth.setApplier(UserContext.getCurrent());
            realAuth.setApplyTime(new Date());
            realAuthMapper.insert(realAuth);
            //把实名认证的id设置给userinfo
            current.setRealAuthId(realAuth.getId());
            userinfoService.update(current);
        }
    }

    @Override
    public PageResult query(RealAuthQueryObject qo) {
        int count = realAuthMapper.queryForCount(qo);
        if (count > 0) {
            List<RealAuth> list = realAuthMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        // 根据id得到实名认证对象
        RealAuth ra = this.get(id);
        // 如果对象存在,并且对象处于待审核状态
        if (ra != null && ra.getState() == RealAuth.STATE_NORMAL) {
            // 1,设置通用属性;
            ra.setAuditor(UserContext.getCurrent());
            ra.setAuditTime(new Date());
            ra.setState(state);
            Userinfo applier = this.userinfoService
                    .get(ra.getApplier().getId());
            if (state == RealAuth.STATE_AUDIT) {
                // 3,如果状态是审核通过;
                // 1,保证用户处于未审核状态;
                if (!applier.getIsRealAuth()) {
                    // 2,添加审核的状态码;设置userinfo上面的冗余数据;重新realauthid;
                    applier.addState(BitStatesUtils.OP_REAL_AUTH);
                    applier.setRealName(ra.getRealName());
                    applier.setIdNumber(ra.getIdNumber());
                    applier.setRealAuthId(ra.getId());
                }
            } else {
                // 1,userinfo中的realauthid设置为空
                applier.setRealAuthId(null);
            }
            this.userinfoService.update(applier);
            this.realAuthMapper.updateByPrimaryKey(ra);
        }
    }
}
