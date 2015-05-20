package com.mx.demo.util;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.util.Base64;

public class DES {
	//密钥,可自行定义.
	public String DESKey = ""; // 字节数必须是8的倍数
	private static byte[] iv1 = { (byte) 0x12, (byte) 0x34, (byte) 0x56,
			(byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	public DES(){
//		String date_ = DateFormat.getDateInstance(DateFormat.DEFAULT,Locale.CHINA).format(new Date());
		String date_ = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		date_ = date_.replaceAll("-", "");
		DESKey = date_;
	}
	
	
	public DES(String key){

		DESKey = key;
	}
	
	public String getDESKey(){
		return DESKey;
	}
	
	private byte[] desEncrypt(byte[] plainText) throws Exception {
		//byte[] keyBytes = GetKeyAsBytes(DESKey);
		
		//使用自定义DES密钥进行密钥对象的创建和初始化
		DESKeySpec dks = new DESKeySpec(GetKeyAsBytes(DESKey));
		
		//创建适用于DES加密算法的密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		
		//创建符合密钥规则的密钥对象
		SecretKey key = keyFactory.generateSecret(dks);
		
		//创建以DES加密算法的加密解密器,使用PKCS5Padding填充方法
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding");
		 SecureRandom sr = new SecureRandom(); 
		//进行初始化,此为加密器的初始化方法
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte data[] = plainText;
		
		//加密,返回密文
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}
	public byte[] GetKeyAsBytes(String key) {
	    byte[] keyBytes = new byte[8]; // a Triple DES key is a byte[24] array

	    for (int i = 0; i < key.length() && i < keyBytes.length; i++) 
	        keyBytes[i] = (byte) key.charAt(i);

	    return keyBytes;
	}

	/**
	 * 加密函数
	 * @param 需要加密的字符串.
	 * @return 加密后的密文字符串,如果加密失败返回"encryptError".
	 */
	public String encrypt(String input) {
		String result = "";
		try {
			//将明文转换为UTF-8的字节数组进行加密,并将密文转换为ASCII码进行存储
			result = base64Encode(desEncrypt(input.getBytes("UTF-8")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private byte[] desDecrypt(byte[] plainText) throws Exception {
		// SecureRandom sr = new SecureRandom();
		// sr.setSeed(iv);

		// IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		//创建并初始化向量
		IvParameterSpec iv = new IvParameterSpec(iv1);
		//使用自定义DES密钥进行密钥对象的创建和初始化
		DESKeySpec dks = new DESKeySpec(DESKey.getBytes());
		//创建适用于DES加密算法的密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		//创建符合密钥规则的密钥对象
		SecretKey key = keyFactory.generateSecret(dks);
		//创建以DES加密算法的加密解密器,使用PKCS5Padding填充方法
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		//进行初始化,此为解密器的初始化方法
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte data[] = plainText;
		//对密文进行解密
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}
	
	/**
	 * 解密函数
	 * @param 需要解密的密文
	 * @return 解密后的原文,如果解密失败返回"decryptError",注意密文一位都不可少.
	 */
	public String decrypt(String input) {
		String result = "decryptError";
		try {
			//对密文转换为二进制字节数组,进行解密后以转换为UTF-8编码形式的字符串显示明文信息
			result = new String(desDecrypt(base64Decode(input)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String base64Encode(byte[] s) {
		if (s == null)
			return null;
		//返回以ASCII形式的密文
		return Base64.encodeToString(s, Base64.DEFAULT);

	}

	private byte[] base64Decode(String str) {
		if (str == null)
			return null;
		//返回以字节数组形式的明文,将此数据以UTF-8形式转码即可得到明文.
		return Base64.decode(str, Base64.DEFAULT);
	}
}
