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
    private int[] bidRequestStates;// 要查询的多个结狂状态

    private String orderBy;// 按照哪个列排序
    private String orderType;// 按照什么顺序排序
}
