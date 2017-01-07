package com.ut.comm.tool;

import net.sf.cglib.core.Converter;

public class IgnoreNullConverter implements Converter{

	 @Override  
	    public Object convert(Object value, Class target, Object context) {  
		 if(value==null){
			 return target;
		 }
	        System.out.println(value.getClass() + " " + value); // from类中的value对象  
	        System.out.println(target); // to类中的定义的参数对象  
	        System.out.println(context.getClass() + " " + context); // String对象,具体的方法名  
//	        if (target.isAssignableFrom(BigInteger.class)) {  
//	            return new BigInteger(value.toString());  
//	        } else {  
//	            return value;  
//	        }  
	        
	        return null;
	    }  

}
