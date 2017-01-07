package com.comm.pojo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String text;

	private List<MenuItem> children = new ArrayList<MenuItem>();

	private String parentId;
	
	private String isparent;
	
	private String path;
	private String state;
	private String busiUnit;
	

	public String getBusiUnit() {
		return busiUnit;
	}

	public void setBusiUnit(String busiUnit) {
		this.busiUnit = busiUnit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public String getIsparent() {
		return isparent;
	}

	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof MenuItem))
			return false;

		final MenuItem item = (MenuItem) other;

		if (!getId().equals(item.getId()))
			return false;
		return true;
	}

	public int hashCode() {
		int result = getId().hashCode();
		result = 29 * result + getText().hashCode();
		return result;
	}
	
	public MenuItem clone(){
		MenuItem o = null;  
        try {  
            o = (MenuItem) super.clone();  
            if(!children.isEmpty()){
            	List<MenuItem> clds = new ArrayList<MenuItem>();
            	for (MenuItem c:children) {
            		clds.add(c.clone());
				}
            	o.children = clds;
            }
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
