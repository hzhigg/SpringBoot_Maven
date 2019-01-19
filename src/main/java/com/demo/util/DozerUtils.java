package com.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;

/**
 * @description: DTO/VO/DO等之间的转换
 * @author hzhigg
 * 2019年1月19日
 */
public class DozerUtils {
	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer;
	//public static String path = HttpConnector.class.getClassLoader().getResource("").getPath();

	static {
		if (dozer == null) {
			dozer = new DozerBeanMapper();
			 List<String> mappingFileUrls = Arrays.asList("dozer/dozer-date.xml");
			 dozer.setMappingFiles(mappingFileUrls);
		}
	}
	/**
	 * 
	 * @title: map
	 * @description: 单个对象相互转换
	 *
	 * @param source 源对象
	 * @param destinationClass 目标对象
	 * @return
	 * @date 2017年11月8日 下午6:08:54
	 */
	public static <T> T map ( Object source, Class<T> destinationClass ) {
	
		return dozer.map(source, destinationClass);
	}

	/**
	 * 
	 * @title: mapList
	 * @description: 集合对象相互转换
	 *
	 * @param sourceList
	 * @param destinationClass
	 * @return
	 * @date 2017年11月8日 下午6:09:41
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> mapList ( Collection sourceList, Class<T> destinationClass ) {
		List<T> destinationList = new ArrayList<T>();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		
		return destinationList;
	}
	
	
}
