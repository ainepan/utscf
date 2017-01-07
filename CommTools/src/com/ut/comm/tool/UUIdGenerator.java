package com.ut.comm.tool;

import java.util.UUID;

public class UUIdGenerator {
	
	public static synchronized String getUUId(){
		UUID uuid  =  UUID.randomUUID(); 
		String id = uuid.toString().replace("-", "");
		return id;
	}
}
