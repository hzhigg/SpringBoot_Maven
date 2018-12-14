
package com.demo.util;

import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具
 * @author hzhigg
 * 2018年12月14日
 */
public class EncryptUtil
{

	/**
	 * 获取加密盐
	 * @param key
	 * @return
	 */
	public static String getSalt(String key){
		Md5Hash hash = new Md5Hash(UUID.randomUUID() + key);
		return hash.toHex();
	}
	/**
	 * 加密
	 * @param plaintext 明文
	 * @param salt 盐
	 * @return 密文
	 */
	public static String encrypt(String plaintext, String salt){
		return new Md5Hash(plaintext, salt, 3).toBase64();
	}
	
	

	public static void main(String[] args) {
		String salt = EncryptUtil.getSalt("123456");
		String encryptPassword = EncryptUtil.encrypt("123456", salt);
		System.out.println(salt+"----"+encryptPassword);
	}
}
