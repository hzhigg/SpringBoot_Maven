package com.demo.util;

import javax.crypto.Cipher;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/** 
 * RSA256非对称加密工具
 * 
 * 生成rsa公钥密钥网址
 * RSA密钥对-->密钥格式PKCS#8(适用于Java格式) 
 * http://web.chacuo.net/netrsakeypair
 * @author Alan
 *
 */
public class SHA256withRSAUtil {


    private String priKey;//内部私钥
    private String pubKey;//内部公钥

    public SHA256withRSAUtil() throws IOException{
    	Resource priPath = new ClassPathResource("rsa/pri.key");
    	Resource pubPath = new ClassPathResource("rsa/pub.key");
        priKey = readStringFromFile(priPath.getURI().getPath());
        pubKey = readStringFromFile(pubPath.getURI().getPath());
    }

    /**
     * 使用私钥加密
     * @Param data
     */
    public String encryptByPrivateKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.ENCRYPT_MODE, privateKey);
            str = base64encode(c1.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /**
     * 使用内部私钥解密
     * @Param data
     */
    public String decryptByPrivateKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] temp = c1.doFinal(base64decode(data));
            str = new String(temp);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }


    /**
     * 使用内部公钥加密
     * @Param data
     */
    public String encryptByPublicKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.ENCRYPT_MODE, rsaPubKey);
            str = base64encode(c1.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /**
     * 使用公钥解密
     * @Param data
     */
    public String decryptByPublicKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.DECRYPT_MODE, rsaPubKey);
            byte[] temp = c1.doFinal(base64decode(data));
            str = new String(temp);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
    /**
     * 本方法使用SHA1withRSA签名算法产生签名
     * @param  src 签名的原字符串
     * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
     */
    public String signByPrivateKey(String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes());
            byte[] signature = sigEng.sign();
            return base64encode(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用共钥验证签名
     * @param sign
     * @param src
     * @return
     */
    public boolean verifyByPublicKey(String sign, String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            sigEng.initVerify(rsaPubKey);
            sigEng.update(src.getBytes());
            byte[] sign1 = base64decode(sign);
            return sigEng.verify(sign1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  base64加密
     * @param bstr
     * @return
     */
    @SuppressWarnings("restriction")
    private String base64encode(byte[] bstr) {
        String str =  new sun.misc.BASE64Encoder().encode(bstr);
        str = str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        return str;
    }

    /**
     * base64解密
     * @param str
     * @return byte[]
     */
    @SuppressWarnings("restriction")
    private byte[] base64decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }

    /**
     * 从文件中读取所有字符串
     * @param fileName
     * @return	String
     */
    private String readStringFromFile(String fileName){
        StringBuffer str = new StringBuffer();
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            char[] temp = new char[1024];
            while (fr.read(temp) != -1) {
                str.append(temp);
            }
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return str.toString();
    }


        public static void main(String[] args) throws IOException {
            SHA256withRSAUtil rsa = new SHA256withRSAUtil();
            String str = "sign";
            String encry=rsa.encryptByPublicKey(str);
            System.out.println("公钥加密加密:"+encry);

            String decry=rsa.decryptByPrivateKey(encry);
            System.out.println("私钥解密:"+decry);
            
            String validate = "我要签名这段文字。";
            System.out.println("原文："+validate);
            String str1 = rsa.signByPrivateKey(validate);
            System.out.println("私钥签名结果："+str1);
            if(rsa.verifyByPublicKey(str1, validate)){
                System.out.println("成功");
            } else {
                System.out.println("失败");
            }
        }
}
