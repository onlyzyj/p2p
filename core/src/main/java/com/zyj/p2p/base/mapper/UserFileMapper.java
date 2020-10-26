package com.zyj.p2p.base.mapper;

import com.zyj.p2p.base.domain.UserFile;
import com.zyj.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    List<UserFile> selectAll();

    int updateByPrimaryKey(UserFile record);

    List<UserFile> listFilesByHasType(@Param("logininfoId") Long logininfoId,@Param("hasType") boolean hasType);

    int queryForCount(UserFileQueryObject qo);
    List<UserFile> query(UserFileQueryObject qo);
}