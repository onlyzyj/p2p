package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.MoneyWithdraw;
import java.util.List;

public interface MoneyWithdrawMapper {
    int insert(MoneyWithdraw record);

    MoneyWithdraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithdraw record);

//    int queryForCount(MoneyWithdrawQueryObject qo);
//
//    List<MoneyWithdraw> query(MoneyWithdrawQueryObject qo);
}