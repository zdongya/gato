package com.caiyi.financial.jzadmin.util;

import java.util.UUID;

public class IdUtils {
	
	public static String getId(){
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().replaceAll("-", "");
		System.out.println(id);
		return id;
	}
	
	public static void main(String[] args) {
		getId();
	}

}
