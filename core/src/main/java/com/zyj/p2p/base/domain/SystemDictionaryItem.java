package com.zyj.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典明细
 */
@Setter@Getter
public class SystemDictionaryItem extends BaseDomian {

    private Long parentId;
    private String title;
    private int sequence;
}