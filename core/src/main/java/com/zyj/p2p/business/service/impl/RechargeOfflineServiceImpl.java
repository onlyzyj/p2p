package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.RechargeOffline;
import com.zyj.p2p.business.mapper.RechargeOfflineMapper;
import com.zyj.p2p.business.query.RechargeOfflineQueryObject;
import com.zyj.p2p.business.service.AccountFlowService;
import com.zyj.p2p.business.service.RechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/11/2-15:49
 */
@Service
public class RechargeOfflineServiceImpl implements RechargeOfflineService {

    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountFlowService accountFlowService;

    @Override
    public void apply(RechargeOffline recharge) {
        recharge.setApplier(UserContext.getCurrent());
        recharge.setApplyTime(new Date());
        recharge.setState(RechargeOffline.STATE_NORMAL);
        this.rechargeOfflineMapper.insert(recharge);
    }

    @Override
    public PageResult query(RechargeOfflineQueryObject qo) {
        int count = this.rechargeOfflineMapper.queryForCount(qo);
        if (count > 0) {
            List<RechargeOffline> list = this.rechargeOfflineMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(),
                    qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        // 查询线下充值对象;设置相关属性;
        RechargeOffline r = this.rechargeOfflineMapper.selectByPrimaryKey(id);
        if (r != null && r.getState() == RechargeOffline.STATE_NORMAL) {
            // 审核通过
            r.setAuditor(UserContext.getCurrent());
            r.setAuditTime(new Date());
            r.setRemark(remark);
            r.setState(state);
            if (state == RechargeOffline.STATE_AUDIT) {
                // **1,得到申请人的账户对象;
                Account applierAccount = this.accountService.get(r.getApplier()
                        .getId());
                // **2,增加账户的可用余额;
                applierAccount.setUsableAmount(applierAccount.getUsableAmount()
                        .add(r.getAmount()));
                // **3,生成一条充值流水
                this.accountFlowService.rechargeFlow(r, applierAccount);
                this.accountService.update(applierAccount);
            }
            this.rechargeOfflineMapper.updateByPrimaryKey(r);
        }
    }
}
