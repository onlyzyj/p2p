package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.UserinfoMapper;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.mapper.BidRequestMapper;
import com.zyj.p2p.business.service.BidRequestService;
import com.zyj.p2p.business.util.CalculatetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author onlyzyj
 * @date 2020/10/28-16:53
 */
@Service
public class BidRequestServiceImpl implements BidRequestService {

    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败 bidRequest:" + bidRequest.getId());
        }
    }

    @Override
    public boolean canApplyBidRequest(Long logininfoId) {
        //得到指定用户
        //判断：1.基本资料2.实名认证3.视频认证4风控资料分数5.没有借款在流程
        Userinfo userinfo = userinfoService.get(logininfoId);
        return userinfo != null
                && userinfo.getIsBasicInfo()
                && userinfo.getIsRealAuth()
                && userinfo.getIsVedioAuth()
                && userinfo.getScore() >= BidConst.BASE_BORROW_SCORE
                && !userinfo.getHasBidRequestProcess();
    }

    @Override
    public void apply(BidRequest br) {
        Account account = this.accountService.getCurrent();
        // 首先满足最基本的申请条件;
        if (this.canApplyBidRequest(UserContext.getCurrent().getId())
                && br.getBidRequestAmount().compareTo(
                BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0// 系统最小借款金额<=借款金额
                && br.getBidRequestAmount().compareTo(
                account.getRemainBorrowLimit()) <= 0// 借款金额<=剩余信用额度
                && br.getCurrentRate()
                .compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0// 5<=利息
                && br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 利息<=20
                && br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0// 最小投标金额>=系统最小投标金额
        ) {
            // ==========进入借款申请
            // 1,创建一个新的BidRequest,设置相关参数;
            BidRequest bidRequest = new BidRequest();
            bidRequest.setBidRequestAmount(br.getBidRequestAmount());
            bidRequest.setCurrentRate(br.getCurrentRate());
            bidRequest.setDescription(br.getDescription());
            bidRequest.setDisableDays(br.getDisableDays());
            bidRequest.setMinBidAmount(br.getMinBidAmount());
            bidRequest.setReturnType(br.getReturnType());
            bidRequest.setMonthes2Return(br.getMonthes2Return());
            bidRequest.setTitle(br.getTitle());
            // 2,设置相关值;
            bidRequest.setApplyTime(new Date());
            bidRequest
                    .setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
            bidRequest.setCreateUser(UserContext.getCurrent());
            bidRequest
                    .setTotalRewardAmount(CalculatetUtil.calTotalInterest(
                            bidRequest.getReturnType(),
                            bidRequest.getBidRequestAmount(),
                            bidRequest.getCurrentRate(),
                            bidRequest.getMonthes2Return()));
            // 3,保存;
            this.bidRequestMapper.insert(bidRequest);
            // 4,给借款人添加一个状态码
            Userinfo userinfo = this.userinfoService.getCurrent();
            userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userinfoService.update(userinfo);

//            // 2,设置相关值;
//            br.setApplyTime(new Date());
//            br
//                    .setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
//            br.setCreateUser(UserContext.getCurrent());
//            br
//                    .setTotalRewardAmount(CalculatetUtil.calTotalInterest(
//                            br.getReturnType(),
//                            br.getBidRequestAmount(),
//                            br.getCurrentRate(),
//                            br.getMonthes2Return()));
//            // 3,保存;
//            this.bidRequestMapper.insert(br);
//            // 4,给借款人添加一个状态码
//            Userinfo userinfo = this.userinfoService.getCurrent();
//            userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
//            this.userinfoService.update(userinfo);
        }
    }
}
