package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.PaymentSchedule;
import java.util.List;

public interface PaymentScheduleMapper {
    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentSchedule record);

//    int queryForCount(PaymentScheduleQueryObject qo);
//
//    List<PaymentSchedule> query(PaymentScheduleQueryObject qo);
}