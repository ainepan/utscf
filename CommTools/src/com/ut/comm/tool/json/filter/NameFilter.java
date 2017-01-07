package com.ut.comm.tool.json.filter;


import java.util.List;
import java.util.Map;

import net.sf.json.util.PropertyFilter;

public class NameFilter  implements PropertyFilter { 

    
	private List<String> nameList = null;

	
	public NameFilter(String[] names){
		if(names != null &&  names.length > 0 ){			
			nameList = java.util.Arrays.asList(names);
		}		
	}

    public boolean apply(final Object source, final String name, final Object value) { 

        if (source instanceof Map) { 

            return false; 

        } 

        return !(nameList != null && nameList.contains(name));

    } 

} 
