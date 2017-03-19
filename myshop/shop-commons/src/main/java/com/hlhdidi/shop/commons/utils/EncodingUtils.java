package com.hlhdidi.shop.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
/**
 * 对密码进行编码
 * @author Administrator
 *
 */
public class EncodingUtils {
	public static String encodePassword(String password) {
		//使用二次编码的形式
		String algorithm="MD5";
		char[] encodeHex=null;
		try {
			MessageDigest digest=MessageDigest.getInstance(algorithm);
			byte[] bytes= digest.digest(password.getBytes());
			encodeHex=Hex.encodeHex(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
}
