package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.PaymentScheduleDetail;
import java.util.List;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentScheduleDetail record);
}