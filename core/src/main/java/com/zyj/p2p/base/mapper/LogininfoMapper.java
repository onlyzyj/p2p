package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogininfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    List<Logininfo> selectAll();

    int updateByPrimaryKey(Logininfo record);

    int getCountByUsername(String username);

    Logininfo login(@Param("username") String username, @Param("password") String password,@Param("userType")int userType);

    //查找是否有超级管理员的账号
    int getCountOfAmin();
}