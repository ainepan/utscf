package com.ut.comm.tool.json.filter;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.ut.comm.tool.json.annotation.Visible;



public class VisibleFilter extends AbstractMethodFilter { 


    private List<String> guid = null; 

    public VisibleFilter(final String[] guid) {  
    	if(guid != null && guid.length > 0){
          this.guid = Arrays.asList(guid); 
    	}
    } 

    public boolean apply(final Method method) { 

        if (guid == null || guid.isEmpty()) { 

            return true;                                       

        } 
        
		
        if (method.isAnnotationPresent(Visible.class)) { 

        	Visible anno = method.getAnnotation(Visible.class); 

            String[] value = anno.value(); 

            for (int i = 0; i < value.length; i++) { 

                if (guid.contains(value[i])) { 

                    return false;               

                } 

            } 

        } 
        
 
      
        return true; 

    } 

} 

 
