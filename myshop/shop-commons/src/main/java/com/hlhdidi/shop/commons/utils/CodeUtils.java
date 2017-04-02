package com.hlhdidi.shop.commons.utils;

import java.util.UUID;

public class CodeUtils {
	/**
	 * 生成验证码
	 * @return
	 */
	public static String genCode() {
		String uuid = UUID.randomUUID().toString();                //ok
		return uuid.replaceAll("-", "").substring(0, 6);
	}
}
