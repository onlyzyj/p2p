package com.zyj.p2p.base.service;

import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.VedioAuthQueryObject;

/**
 * 视频认证服务
 *
 * @author onlyzyj
 * @date 2020/10/23-11:10
 */
public interface VedioAuthService {
    PageResult query(VedioAuthQueryObject qo);

    void audit(Long loginInfoValue, String remark, int state);
}
