package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.Account;
import java.util.List;

public interface AccountMapper {

    int insert(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Account record);

    List<Account> selectAll();

}