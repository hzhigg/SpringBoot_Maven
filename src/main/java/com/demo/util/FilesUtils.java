package com.demo.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

import com.demo.base.utils.DateUtil;
import com.demo.global.constans.Constant;
import com.demo.global.enums.RtnResultCode;
import com.demo.global.exception.ServerException;

public class FilesUtils {
	public static final Logger logger = LoggerFactory.getLogger(FilesUtils.class);
	/**
	 * 
	 * @title: getBytes
	 * @description: 根据指定文件，转换为byte数组
	 *
	 * @param fileUrl 文件相对路径
	 * @return byte[]
	 * @date 2018年11月8日 下午7:08:45
	 */
	public static byte[] getBytes ( String fileUrl ,ConfigProperties configProperties) {
		byte[] buffer = null;
		if (StringUtils.isBlank(fileUrl)) {
			return buffer;
		}
		// 文件绝对路径
		String filePath = configProperties.getFileLocation() + fileUrl;
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			logger.warn("getBytes, warnInfo：{}", RtnResultCode.WARN_DOWN_FILE_TOO_LARGE.getMsgCN());
			return buffer;
		}
		// 下载文件存在，则进行读取操作
		if (file.exists()) {
			FileInputStream inputStream = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) fileSize);
			try {
				// 1、利用FileInputStream读取文件
				inputStream = new FileInputStream(file);

				// 2、利用ByteArrayOutputStream将FileInputStream中的文件数据读出来
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, len);
				}

				// 3、利用toByteArray()方法得到文件的字节数组
				buffer = outputStream.toByteArray();
			} catch (IOException e) {
				logger.error("getBytes, errorinfo：{}", e);
			} finally {
				try {
					// 关闭流、释放资源
					if (inputStream != null) {
						inputStream.close();
					}
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.warn("url{},getBytes, warnInfo：{}",filePath, RtnResultCode.WARN_FILE_NOT_FOUND.getMsgCN());
		}
		return buffer;
	}

	/**
	 * 通过字节数组保存文件返回相对路径
	 * @param bfile
	 * @param fileName
	 * @param moudleFolder
	 * @param configProperties
	 * @return
	 */
	public static String getFile (byte[] bfile, String fileName,String moudleFolder,ConfigProperties configProperties) {
		String fileUrl = null;
		if (StringUtils.isBlank(fileName)) {
			return fileUrl;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		// 文件后缀
		String prefix = "";
		if (lastIndexOf != -1) {
			prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			prefix = "pdf";
		}

		// 新文件名称（=文件名前缀 +时间戳+ 4位随机数 + "." + 文件后缀）
		String newFileName = UUID.randomUUID().toString()+"." + prefix;
		// 新文件存储目录（相对路径）
		String date= DateUtil.getDateTime("yyyyMMdd");
		String filePath =date + File.separator+moudleFolder+ File.separator;
		// 新文件存储目录（绝对路径）
		String realPath = configProperties.getFileLocation() + filePath;
		File realFile = new File(realPath);
		if (!realFile.exists()) {
			realFile.mkdirs();
		}

		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			// 文件相对路径
			fileUrl = date + Constant.Common.FILE_SEPARATOR+moudleFolder+ Constant.Common.FILE_SEPARATOR + newFileName;
			// 文件绝对路径
			String realFileUrl = realPath + newFileName;
			File localFile = new File(realFileUrl);
			// 保存到指定路径
			fos = new FileOutputStream(localFile);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (IOException e) {
			logger.error("getFile, errorinfo：{}", e);
		} finally {
			try {
				// 关闭流、释放资源
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileUrl;
	}
	
	/**
	 * 根据url下载文件返回相对路径
	 * @param sourceUrl
	 * @param moudleFolder
	 * @param configProperties
	 * @return
	 */
	public static String DownByUrl(String sourceUrl,String moudleFolder,ConfigProperties configProperties){
		String suffix=sourceUrl.substring(sourceUrl.lastIndexOf("."),sourceUrl.length());
		String fileName=UUID.randomUUID().toString()+suffix;
		String date= DateUtil.getDateTime("yyyyMMdd");
		String savePath =date + File.separator+moudleFolder+File.separator;//保存目录
		String relpPath=date + Constant.Common.FILE_SEPARATOR+moudleFolder+Constant.Common.FILE_SEPARATOR;
		URL url;
		try {
			url = new URL(UriUtils.encodePath(sourceUrl, "UTF-8"));
			File f = new File(configProperties.getFileLocation() + savePath+fileName);
			FileUtils.copyURLToFile(url, f);//下载文件到指定目录
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServerException("下载文件异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServerException("下载文件异常");
		}
		
		return relpPath+fileName;
	}

}
