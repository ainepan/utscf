package com.ut.comm.tool.json.filter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.ut.comm.tool.json.annotation.Invisible;

public class InvisibleFilter extends AbstractMethodFilter { 



    private List<String> guid = null; 

    public InvisibleFilter(final String[] guid) {  
    	if(guid != null && guid.length > 0){
          this.guid = Arrays.asList(guid); 
    	}
    } 
 

    public boolean apply(final Method method) { 

        if (guid == null || guid.isEmpty()) { 

            return false;                                        

        } 

        if (method.isAnnotationPresent(Invisible.class)) { 

            Invisible anno = method.getAnnotation(Invisible.class); 

            String[] value = anno.value(); 

            for (int i = 0; i < value.length; i++) { 

                if (guid.contains(value[i])) { 
                   return true; 

                } 

            } 

        } 


        return false; 

    } 

} 

 
