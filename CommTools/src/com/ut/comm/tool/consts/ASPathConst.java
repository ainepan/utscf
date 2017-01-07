package com.ut.comm.tool.consts;

import java.io.File;

import com.ut.comm.tool.string.StringUtil;

public class ASPathConst {
	private static final String INPUT_DIR = System.getProperty("scf.input.dir");
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String OUTPUT_DIR =System.getProperty("scf.output.dir");
	public static final String USER_DIR_PATH = getUserDirPath();	
	public static final String SCF_OUTPUT_DIR = getOutputPath();
	private static final String USER_LIB = System.getProperty("user.lib");
	private static final String AP_PARA_DEFAULT_PATH = "platform";
	
	public static final String getUserDirPath(){
		
		String path = INPUT_DIR;
		if (path == null || path.trim().length() <= 0 )
			return USER_DIR;
		
		return path;
	}
	
	public static final String getUserOutputPath(){
		
		String path = SCF_OUTPUT_DIR;
		if (path == null || path.trim().length() <= 0 )
			return USER_DIR;
		
		return path;
	}
	
	private static String getOutputPath() {
		String path = OUTPUT_DIR;
		if (path == null || path.trim().length() <= 0)
			return USER_DIR;
		return path;
	}
	
	public static final String getUserLibPath(){
		String path = USER_LIB;
		if (StringUtil.isTrimEmpty(path))
			return getUserDirPath()+File.separator+"lib";
		
		return path;
	}
	
	public static String getDefaultBuName(){
		return AP_PARA_DEFAULT_PATH;
	}
}
