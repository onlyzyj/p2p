package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.Userinfo;
import java.util.List;
import java.util.Map;

public interface UserinfoMapper {

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Userinfo record);

    List<Map<String, Object>> autoComplate(String keyword);
}