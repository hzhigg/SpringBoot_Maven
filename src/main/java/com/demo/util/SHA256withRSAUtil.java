package com.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/** *//**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * 生成rsa公钥密钥网址
 * RSA密钥对-->密钥格式PKCS#8(适用于Java格式) 
 * http://web.chacuo.net/netrsakeypair
 *
 * @author huangzhi
 * @date 2019-09-12
 * @version 1.0
 */
public class SHA256withRSAUtil {

    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private String priKey;//内部私钥
    private String pubKey;//内部公钥
    private String fqcrPubKey;//分期超人公钥

    public SHA256withRSAUtil() throws IOException {
        Resource priPath=new ClassPathResource("rsa/pri.key");
        Resource pubPath=new ClassPathResource("rsa/pub.key");
        Resource fqcrPubPath=new ClassPathResource("rsa/fqcr_pub.key");
        priKey = readStringFromFile(priPath.getURI().getPath());
        pubKey = readStringFromFile(pubPath.getURI().getPath());
        fqcrPubKey = readStringFromFile(fqcrPubPath.getURI().getPath());
    }


    /** *//**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     *
     * @return
     * @throws Exception
     */
    public String sign(byte[] data) throws Exception {
        byte[] keyBytes = Base64Utils.decode(priKey.trim());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public boolean verify(byte[] data , String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(pubKey.trim());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception
     */
    public byte[] decryptByPrivateKey(byte[] encryptedData)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(priKey.trim());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception
     */
    public byte[] decryptByPublicKey(byte[] encryptedData)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(pubKey.trim());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @return
     * @throws Exception
     */
    public byte[] encryptByPublicKey(byte[] data)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(pubKey.trim());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** *//**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @return
     * @throws Exception
     */
    public byte[] encryptByPrivateKey(byte[] data)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(priKey.trim());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 使用分期超人公钥加密
     * @Param data
     */
    public String encryptByFqcrPublicKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pubbyte = Base64Utils.decode(fqcrPubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.ENCRYPT_MODE, rsaPubKey);
            str = Base64Utils.encode(c1.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /** *//**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** *//**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
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


    public static void main(String[] args) throws Exception {

        SHA256withRSAUtil rsa=new SHA256withRSAUtil();
        //公钥加密
        byte [] encry=rsa.encryptByPublicKey("123456".getBytes());
        //私钥解密
        byte [] descr=rsa.decryptByPrivateKey(encry);
        System.out.println("公钥加密结果："+Base64Utils.encode(encry));
        System.out.println("私钥解密结果:"+new String(descr));
        //私钥签名
        String sign=rsa.sign(encry);
        System.out.println("签名结果："+sign);
        //公钥验签
        boolean verify=rsa.verify(encry,sign);
        System.out.println("验签结果："+verify);


        //私钥加密
        byte[] priEncry= rsa.encryptByPrivateKey("你好".getBytes());
        System.out.println("私钥加密结果："+Base64Utils.encode(priEncry));
        //公钥解密
        byte[] pubDesc=rsa.decryptByPublicKey(priEncry);
        System.out.println("公钥解密结果："+new String(pubDesc));


        //==============与第三方公司加密例子================
        JSONObject json=new JSONObject();
        json.put("date","2019-08-30");
        String data=rsa.encryptByFqcrPublicKey(json.toString());//用第三方公司公钥加密请求参数
        String reqTime="1568020864454";
        String dataEncry="data="+data+"&req_time="+reqTime;//将加密后的参数与时间戳拼接
        System.out.println("签名字符串:"+dataEncry);

        String crfqsign=rsa.sign(dataEncry.getBytes());//用内部私钥签名
        System.out.println("sing :"+crfqsign);
        System.out.println("data :"+data);

        //用内部公钥验签
        System.out.println(rsa.verify(dataEncry.getBytes(),crfqsign));
        //==============end================
    }

}