package com.zyj.p2p.base.service;

import com.zyj.p2p.base.domain.UserFile;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.UserFileQueryObject;

import java.util.List;

/**
 * 风控资料服务
 *
 * @author onlyzyj
 * @date 2020/10/24-16:51
 */
public interface UserFileService {
    void apply(String fileName);

    /**
     * 列出用户没有选择类别的风控材料
     * @param logininfoId
     * @return
     */
    List<UserFile> listFilesByHasType(Long logininfoId,boolean hasType);

    void batchUpdateFileType(Long[] id, Long[] fileType);

    PageResult query(UserFileQueryObject qo);

    List<UserFile> queryForList(UserFileQueryObject qo);

    void audit(Long id, int state, int score, String remark);
}
