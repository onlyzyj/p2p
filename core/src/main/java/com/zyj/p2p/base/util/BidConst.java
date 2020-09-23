package com.zyj.p2p.base.util;

import java.math.BigDecimal;

/**
 * 系统中的常量
 * 
 * @author Administrator
 * 
 */
public class BidConst {

	/**
	 * 定义存储精度
	 */
	public static final int STORE_SCALE = 4;
	/**
	 * 定义运算精度
	 */
	public static final int CAL_SCALE = 8;
	/**
	 * 定义显示精度
	 */
	public static final int DISPLAY_SCALE = 2;

	/**
	 * 定义系统级别的0
	 */
	public static final BigDecimal ZERO = new BigDecimal("0.0000");

	/**
	 * 定义初始授信额度
	 */
	public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal(
			"5000.0000");

	public static final String INIT_ADMIN_NAME = "admin";

	public static final String INIT_ADMIN_PASSWORD = "1234";

	/**
	 * 手机短信验证有效时间
	 */
	public static final int VERIFY_VALIDATE_SECOND = 300;
}
