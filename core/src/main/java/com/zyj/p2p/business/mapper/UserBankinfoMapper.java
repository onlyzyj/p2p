package com.zyj.p2p.business.mapper;

import com.zyj.p2p.business.domain.UserBankinfo;
import java.util.List;

public interface UserBankinfoMapper {

    int insert(UserBankinfo record);

    UserBankinfo selectByUser(Long userid);
}