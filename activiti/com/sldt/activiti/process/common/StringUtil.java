package com.sldt.activiti.process.common;

import java.util.UUID;

public class StringUtil {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

}
