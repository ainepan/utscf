package com.ut.comm.tool.json.filter;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.json.util.PropertyFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.json.JsonFilter;


public abstract class AbstractMethodFilter implements PropertyFilter { 

	private static final Log OA_LOG = LogFactory.getLog(AbstractMethodFilter.class);

    public abstract boolean apply(final Method method); 

 

    public boolean apply(final Object source, final String name, final Object value) { 

        if (source instanceof Map) {
            return false;
        } 

        Class clz = BeanUtils.getClass(source); 

        if(JsonFilter.isRuntimeJoinProperty(name) || !clz.getName().startsWith("com.gl.oa")){
        	return false; 
        }
        
       
        String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1); 

        Method method = null; 

        try { 

            method = clz.getMethod(methodName, (Class[]) null);  
        } catch (NoSuchMethodException nsme) { 

            String methodName2 =  "is" + name.substring(0, 1).toUpperCase() + name.substring(1);             

            try { 

                method = clz.getMethod(methodName2, (Class[]) null); 

            } catch (NoSuchMethodException ne) { 


            	OA_LOG.error("No such methods: " + methodName + " or " + methodName2 + " in " + clz.getName()); 

                return true; 

            } 

        } 
        
        
        return apply(method); 

    } 

} 
