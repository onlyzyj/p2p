package com.zyj.p2p.business.query;

import com.zyj.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author onlyzyj
 * @date 2020/10/29-11:30
 */
@Setter@Getter
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;//借款状态
}
