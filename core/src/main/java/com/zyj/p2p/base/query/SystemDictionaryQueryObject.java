package com.zyj.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 数据字典查询对象
 * 
 * @author Administrator
 * 
 */
@Getter
@Setter
public class SystemDictionaryQueryObject extends QueryObject {

	private String keyword;
	private Long parentId;

	public String getKeyword() {
		return StringUtils.hasLength(keyword) ? keyword : null;
	}
}
