package com.sldt.activiti.process.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map 工具类
 * @author tinkpad
 */
public class MapUtil {

	@SuppressWarnings("unchecked")
	/**
	 * 目前仅仅支持String类型的处理，其他的对象或者类型暂时不支持
	 * @param objclass
	 * @param list
	 * @return
	 */
	public static <T> List<T> toEntityList(Class<?> objclass, List<Map<String, Object>> list) {
		List<T> newList = new ArrayList<T>();
		try {

			for (Map<String, Object> map : list) {// 遍历数据
				Set<String> keySet = map.keySet();
				Object newobj = objclass.newInstance();// 新建一个对象
				for (String key : keySet) {

					String methodName = "set" + MapUtil.captureName(key);
					Method method = objclass.getMethod(methodName,String.class);
					method.invoke(newobj, map.get(key));
				}
				newList.add((T) newobj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (List<T>) newList;
	}

	// 首字母大写
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		if (cs[0] >= 97 && cs[0] < 123)
			cs[0] -= 32;
		return String.valueOf(cs);
	}

}
