/*
 * Copyright (c) chaser Corporation 2016. All rights reserved.
 *
 * @Title ReflectUtil.java
 *
 * @Package com.sldt.activiti.process.common
 */
package com.sldt.activiti.process.common;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ReflectUtil
 * @Description: 反射工具类
 * @author caohb
 * @date 2016年7月22日 下午12:27:32
 * @version V1.0
 */
public class ReflectUtil {

	/**
	 * @MethodName: getBaseInfoList
	 * @Description: 返回基类的字段信息
	 * @param baseClzzList	基类类型集合
	 * @param list	具体实现类的对象集合
	 * @return
	 */
	public static List getBaseInfoList(List dataList, Class... baseClzzList) {
		List baseInfoList = new ArrayList<HashMap<String, Object>>();
		try {
			List<PropertyDescriptor> pdList = new ArrayList<PropertyDescriptor>();
			
			//获取所有基本类型的属性描述符
			for (Class baseClzz : baseClzzList) {
				//使用内省机制获取bean的属性描述符
				pdList.addAll(Arrays.asList(Introspector.getBeanInfo(baseClzz)
						.getPropertyDescriptors()));
			}
			
			
			//bean属性名和属性值
			String propName = null;
			Object value = null;
			
			for (Object obj : dataList) {
				Map<String, Object> baseInfo = new HashMap<String, Object>();
				
				for (PropertyDescriptor pd : pdList) {
					propName = pd.getName();
					//如果属性名为这空，则将对应的值放入Map
					if (StringUtils.isNotEmpty(propName)) {
						Method getter = pd.getReadMethod();
						value = getter.invoke(obj);
						baseInfo.put(propName, value);
					}
				}
				baseInfoList.add(baseInfo);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseInfoList;
	}
	
	/**
	 * add by liumeng 2016/08/02
	 * @param obj
	 * @param baseClzzList
	 * @return
	 */
	public static Map<String,Object> getBaseInfoForObj(Object obj, Class... baseClzzList) {
		Map<String, Object> baseInfo = new HashMap<String, Object>();
		try {
			List<PropertyDescriptor> pdList = new ArrayList<PropertyDescriptor>();
			//获取所有基本类型的属性描述符
			for (Class baseClzz : baseClzzList) {
				//使用内省机制获取bean的属性描述符
				pdList.addAll(Arrays.asList(Introspector.getBeanInfo(baseClzz)
						.getPropertyDescriptors()));
			}
			//bean属性名和属性值
			String propName = null;
			Object value = null;
			
			for (PropertyDescriptor pd : pdList) {
				propName = pd.getName();
				//如果属性名为这空，则将对应的值放入Map
				if (StringUtils.isNotEmpty(propName)) {
					Method getter = pd.getReadMethod();
					value = getter.invoke(obj);
					baseInfo.put(propName, value);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseInfo;
	}
}
