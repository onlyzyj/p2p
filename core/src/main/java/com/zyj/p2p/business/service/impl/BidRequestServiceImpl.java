package com.zyj.p2p.business.service.impl;

import com.zyj.p2p.base.domain.Account;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.BitStatesUtils;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.*;
import com.zyj.p2p.business.mapper.*;
import com.zyj.p2p.business.query.BidRequestQueryObject;
import com.zyj.p2p.business.service.AccountFlowService;
import com.zyj.p2p.business.service.BidRequestService;
import com.zyj.p2p.business.service.SystemAccountService;
import com.zyj.p2p.business.util.CalculatetUtil;
import com.zyj.p2p.business.util.DecimalFormatUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    @Autowired
    private BidMapper bidMapper;

    @Autowired
    private AccountFlowService accountFlowService;

    @Autowired
    private SystemAccountService systemAccountService;

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;

    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

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

    @Override
    public PageResult query(BidRequestQueryObject qo) {
        int count = bidRequestMapper.queryForCount(qo);
        if (count > 0) {
            List<BidRequest> list = bidRequestMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void publishAudit(Long id, String remark, int state) {
        // 查出bidrqeuest;
        BidRequest br = this.bidRequestMapper.selectByPrimaryKey(id);
        // 判断状态
        if (br != null
                && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
            // 创建一个审核历史对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(br.getCreateUser());
            history.setApplyTime(br.getApplyTime());
            history.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setRemark(remark);
            history.setState(state);
            history.setBidRequestId(br.getId());
            this.bidRequestAuditHistoryMapper.insert(history);

            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 如果审核通过:修改标的状态,设置风控意见;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
                br.setDisableDate(DateUtils.addDays(new Date(),
                        br.getDisableDays()));
                br.setPublishTime(new Date());
                br.setNote(remark);
            } else {
                // 如果审核失败:修改标的状态,用户去掉状态码;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                Userinfo applier = this.userinfoService.get(br.getCreateUser()
                        .getId());
                applier.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
                this.userinfoService.update(applier);
            }
            this.update(br);
        }
    }

    @Override
    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id) {
        return bidRequestAuditHistoryMapper.listByBidRequest(id);
    }

    @Override
    public List<BidRequest> listIndex(int size) {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setBidRequestStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,
                BidConst.BIDREQUEST_STATE_PAYING_BACK,
                BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
        qo.setPageSize(size);
        qo.setCurrentPage(1);
        qo.setOrderBy("bidRequestState");
        qo.setOrderType("ASC");
        return bidRequestMapper.query(qo);
    }

    @Override
    public void bid(Long bidRequestId, BigDecimal amount) {
        // 检查,得到借款信息
        BidRequest br = this.get(bidRequestId);
        Account currentAccount = this.accountService.getCurrent();
        if (br != null// 1,借款存在;
                && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_BIDDING// 2,借款状态为招标中;
                && !br.getCreateUser().getId()
                .equals(UserContext.getCurrent().getId())// 3,当前用户不是借款的借款人;
                && currentAccount.getUsableAmount().compareTo(amount) >= 0// 4,当前用户账户余额>=投标金额;
                && amount.compareTo(br.getMinBidAmount()) >= 0// 5,投标金额>=最小投标金额;
                && amount.compareTo(br.getRemainAmount()) <= 0// 6,投标金额<=借款剩余投标金额;
        ) {
            // 执行投标操作
            // 1,创建一个投标对象;设置相关属性;
            Bid bid = new Bid();
            bid.setActualRate(br.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(br.getId());
            bid.setBidRequestTitle(br.getTitle());
            bid.setBidTime(new Date());
            bid.setBidUser(UserContext.getCurrent());
            bidMapper.insert(bid);

            // 2,得到投标人账户,修改账户信息;
            currentAccount.setUsableAmount(currentAccount.getUsableAmount()
                    .subtract(amount));
            currentAccount.setFreezedAmount(currentAccount.getFreezedAmount()
                    .add(amount));
            // 3,生成一条投标流水;
            this.accountFlowService.bid(bid, currentAccount);
            // 4,修改借款相关信息;
            br.setBidCount(br.getBidCount() + 1);
            br.setCurrentSum(br.getCurrentSum().add(amount));
            // 判断当前标是否投满:
            if (br.getBidRequestAmount().equals(br.getCurrentSum())) {
                // 1,修改标的状态;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
            }

            this.accountService.update(currentAccount);
            this.update(br);
        }
    }

    @Override
    public void fullAudit1(Long id, String remark, int state) {
        // 得到借款对象,判断状态;
        BidRequest br = this.get(id);
        if (br != null
                && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1) {
            // 创建一个借款审核流程对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(br.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(br.getId());
            history.setRemark(remark);
            history.setState(state);
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_1);
            this.bidRequestAuditHistoryMapper.insert(history);

            // 判断审核状态:
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 1,如果审核通过:修改借款状态为满标二审;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
            } else {
                // 2,如果审核不通过:
                // 2.1:修改借款状态;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
                // 2.2:退钱
                returnMoney(br);
                // 2.3:移除借款人借款的状态码;
                Userinfo borrowUser = this.userinfoService.get(br
                        .getCreateUser().getId());
                borrowUser
                        .removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
                this.userinfoService.update(borrowUser);
            }
            this.update(br);
        }
    }

    @Override
    public void fullAudit2(Long id, String remark, int state) {
        // 得到借款对象,判断状态
        BidRequest br = this.get(id);
        if (br != null
                && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2) {
            // 创建一个借款的审核流程对象,并设置相关参数;
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setApplier(br.getCreateUser());
            history.setApplyTime(new Date());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setBidRequestId(br.getId());
            history.setRemark(remark);
            history.setState(state);
            history.setAuditType(BidRequestAuditHistory.FULL_AUDIT_2);
            this.bidRequestAuditHistoryMapper.insert(history);
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 审核通过
                // 1,对借款要做什么事情?
                // **1.1修改借款状态(还款中)
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
                // 2,对借款人要做什么事情?
                // **2.1借款人收款操作
                Account borrowAccount = this.accountService.get(br
                        .getCreateUser().getId());
                // ***2.1.1账户余额增加,
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount()
                        .add(br.getBidRequestAmount()));
                // ***2.1.2生成收款流水;
                this.accountFlowService.borrowSuccess(br, borrowAccount);
                // ***2.1.3修改待还本息;
                borrowAccount.setUnReturnAmount(borrowAccount
                        .getUnReturnAmount().add(br.getBidRequestAmount())
                        .add(br.getTotalRewardAmount()));
                // ***2.1.4修改可用信用额度;
                borrowAccount.setRemainBorrowLimit(borrowAccount
                        .getRemainBorrowLimit().subtract(
                                br.getBidRequestAmount()));
                // **2.2移除借款人借款进行中状态码;
                Userinfo borrowUser = this.userinfoService.get(br
                        .getCreateUser().getId());
                borrowUser
                        .removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
                this.userinfoService.update(borrowUser);
                // **2.3支付借款手续费
                // ***2.3.1可用余额减少
                BigDecimal manageChargeFee = CalculatetUtil
                        .calAccountManagementCharge(br.getBidRequestAmount());
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount()
                        .subtract(manageChargeFee));
                // ***2.3.2生成支付借款手续费流水;
                this.accountFlowService.borrowChargeFee(manageChargeFee, br,
                        borrowAccount);
                // ***2.3.3平台收取借款手续费;
                this.systemAccountService.chargeBorrowFee(br, manageChargeFee);

                // 3,对投资人要做什么事情?
                // **3.1遍历投标;
                Map<Long, Account> updates = new HashMap<Long, Account>();
                // 汇总利息,用于最后一个投标的用户的利息计算
                BigDecimal totalBidInterest = BidConst.ZERO;
                for (int i = 1; i <= br.getBids().size(); i++) {
                    Bid bid = br.getBids().get(i - 1);

                    // **3.2根据投标减少投资人的冻结金额;
                    Long bidUserId = bid.getBidUser().getId();
                    Account bidAccount = updates.get(bidUserId);
                    //根据id获取account，没有就添加，这样处理是因为一个账户可能投多次标
                    if (bidAccount == null) {
                        bidAccount = this.accountService.get(bidUserId);
                        updates.put(bidUserId, bidAccount);
                    }
                    bidAccount.setFreezedAmount(bidAccount.getFreezedAmount()
                            .subtract(bid.getAvailableAmount()));
                    // **3.3生成成功投标流水
                    this.accountFlowService.bidSuccess(bid, bidAccount);
                    // **3.4计算待收利息和待收本金
                    // 待收本金==这次的投标金额
                    bidAccount.setUnReceivePrincipal(bidAccount
                            .getUnReceivePrincipal().add(
                                    bid.getAvailableAmount()));
                    // 如果当前投标是整个投标列表中的最后一个投标;这个投标的利息=借款总回报利息-累加的投标利息|
                    BigDecimal bidInterest = BidConst.ZERO;
                    if (i < br.getBids().size()) {
                        // 待收利息=投标金额/借款总金额*借款总回报利息
                        bidInterest = bid
                                .getAvailableAmount()
                                .divide(br.getBidRequestAmount(),
                                        BidConst.CAL_SCALE,
                                        RoundingMode.HALF_UP)
                                .multiply(br.getTotalRewardAmount());

                        bidInterest = DecimalFormatUtil.formatBigDecimal(
                                bidInterest, BidConst.STORE_SCALE);
                        // 累加
                        totalBidInterest = totalBidInterest.add(bidInterest);
                    } else {
                        bidInterest = br.getTotalRewardAmount().subtract(
                                totalBidInterest);
                    }
                    bidAccount.setUnReceiveInterest(bidAccount
                            .getUnReceiveInterest().add(bidInterest));
                }
                // 4,思考满标二审之后的流程(还款)对满标二审有什么影响
                // **4生成还款对象和回款对象
                createPaymentSchedules(br);

                this.accountService.update(borrowAccount);
                for (Account account : updates.values()) {
                    this.accountService.update(account);
                }
            } else {
                // 审核拒绝
                // **1,修改借款状态;
                // **2,退款
                // **3,移除借款人正在借款状态码
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
                returnMoney(br);
                Userinfo borrowUser = this.userinfoService.get(br
                        .getCreateUser().getId());
                borrowUser
                        .removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
                this.userinfoService.update(borrowUser);
            }
            this.update(br);
        }
    }

    /**
     * 创建还款计划对象
     *
     * @param br
     */
    private void createPaymentSchedules(BidRequest br) {
        Date now = new Date();
        BigDecimal totalInterest = BidConst.ZERO;
        BigDecimal totalPrincipal = BidConst.ZERO;
        for (int i = 0; i < br.getMonthes2Return(); i++) {
            // 针对每一期创建一个还款计划对象
            PaymentSchedule ps = new PaymentSchedule();
            ps.setBidRequestId(br.getId());
            ps.setBidRequestTitle(br.getTitle());
            ps.setBidRequestType(br.getBidRequestType());
            ps.setBorrowUser(br.getCreateUser());
            ps.setDeadLine(DateUtils.addMonths(now, i + 1));

            if (i < br.getMonthes2Return() - 1) {
                // 计算这一期的总还款金额
                ps.setTotalAmount(CalculatetUtil.calMonthToReturnMoney(
                        br.getReturnType(), br.getBidRequestAmount(),
                        br.getCurrentRate(), i + 1, br.getMonthes2Return()));
                // 计算这一期的利息
                ps.setInterest(CalculatetUtil.calMonthlyInterest(
                        br.getReturnType(), br.getBidRequestAmount(),
                        br.getCurrentRate(), i + 1, br.getMonthes2Return()));
                // 计算这一期的本金
                ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));
                totalInterest = totalInterest.add(ps.getInterest());
                totalPrincipal = totalPrincipal.add(ps.getPrincipal());
            } else {
                // 这一期利息
                ps.setInterest(br.getTotalRewardAmount()
                        .subtract(totalInterest));
                ps.setPrincipal(br.getBidRequestAmount().subtract(
                        totalPrincipal));
                ps.setTotalAmount(ps.getPrincipal().add(ps.getInterest()));
            }
            ps.setMonthIndex(i + 1);
            ps.setReturnType(br.getReturnType());
            // 处于待还状态
            ps.setState(BidConst.PAYMENT_STATE_NORMAL);
            paymentScheduleMapper.insert(ps);

            // 生成还款明细
            this.createPaymentScheduleDetail(ps, br);
        }
    }


    private void createPaymentScheduleDetail(PaymentSchedule ps, BidRequest br) {
        BigDecimal totalAmount = BidConst.ZERO;
        for (int i = 0; i < br.getBids().size(); i++) {
            Bid bid = br.getBids().get(i);
            // 针对每一个投标创建一个还款明细
            PaymentScheduleDetail psd = new PaymentScheduleDetail();
            psd.setBidAmount(bid.getAvailableAmount());
            psd.setBidId(bid.getId());
            psd.setBidRequestId(br.getId());
            psd.setDeadline(ps.getDeadLine());
            psd.setFromLogininfo(br.getCreateUser());

            if (i < br.getBids().size() - 1) {
                // 计算利息
                BigDecimal interest = DecimalFormatUtil.formatBigDecimal(
                        bid.getAvailableAmount()
                                .divide(br.getBidRequestAmount(),
                                        BidConst.CAL_SCALE,
                                        RoundingMode.HALF_UP)
                                .multiply(ps.getInterest()),
                        BidConst.STORE_SCALE);
                // 计算本金
                BigDecimal principal = DecimalFormatUtil.formatBigDecimal(
                        bid.getAvailableAmount()
                                .divide(br.getBidRequestAmount(),
                                        BidConst.CAL_SCALE,
                                        RoundingMode.HALF_UP)
                                .multiply(ps.getPrincipal()),
                        BidConst.STORE_SCALE);
                psd.setInterest(interest);
                psd.setPrincipal(principal);
                psd.setTotalAmount(interest.add(principal));
                totalAmount = totalAmount.add(psd.getTotalAmount());
            } else {
                psd.setTotalAmount(ps.getTotalAmount().subtract(totalAmount));
                // 计算利息
                BigDecimal interest = DecimalFormatUtil.formatBigDecimal(
                        bid.getAvailableAmount()
                                .divide(br.getBidRequestAmount(),
                                        BidConst.CAL_SCALE,
                                        RoundingMode.HALF_UP)
                                .multiply(ps.getInterest()),
                        BidConst.STORE_SCALE);
                psd.setInterest(interest);
                psd.setPrincipal(psd.getTotalAmount().subtract(
                        psd.getInterest()));
            }
            psd.setMonthIndex(ps.getMonthIndex());
            psd.setPaymentScheduleId(ps.getId());
            psd.setReturnType(br.getReturnType());
            // 投资人
            psd.setToLogininfoId(bid.getBidUser().getId());

            this.paymentScheduleDetailMapper.insert(psd);
        }
    }

    /**
     * 退钱
     *
     * @param br
     */
    private void returnMoney(BidRequest br) {
        Map<Long, Account> updates = new HashMap<Long, Account>();
        // 遍历投标列表;
        for (Bid bid : br.getBids()) {
            // 针对每一个bid进行退款;
            // 1,找到投标人对应的账户;
            Long accountId = bid.getBidUser().getId();
            Account bidAccount = updates.get(accountId);
            if (bidAccount == null) {
                bidAccount = this.accountService.get(bid.getBidUser().getId());
                updates.put(accountId, bidAccount);
            }
            // 2,账户可用余额增加,冻结金额减少(投标的钱)
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(
                    bid.getAvailableAmount()));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(
                    bid.getAvailableAmount()));
            // 3,生成退款流水
            this.accountFlowService.returnMoney(bid, bidAccount);
        }
        for (Account account : updates.values()) {
            this.accountService.update(account);
        }
    }
}
