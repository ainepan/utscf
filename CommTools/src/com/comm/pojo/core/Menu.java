package com.comm.pojo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MenuItem> menu ;
	
	private Map<String,MenuItem> processMap = new HashMap<String,MenuItem>();
	
	//待找的父亲
	private Map<String,List<MenuItem>> parentMap = new HashMap<String,List<MenuItem>>();
	
	public List<MenuItem> getMenu(){
		return menu;
	}
	
	public void addItem(MenuItem item){
		if(this.menu==null)
			this.menu = new ArrayList<MenuItem>();
	
		if(containsParaent(item)){
			item.setPath(getParent(item).getPath()+" > "+item.getPath());
			if(!getParent(item).getChildren().contains(item))
				getParent(item).getChildren().add(item);
			if(!processMap.containsKey(item.getId())){
				processMap.put(item.getId(), item);
			}
		}else{
			if(!processMap.containsKey(item.getId())){
				processMap.put(item.getId(), item);
			}
//			this.menu.add(item);
			addList(item);
		}
		
		setPath(item);
		
		/*if(containsChild(item)){
			List<MenuItem> childs = getChild(item);			
			item.setChildren(childs);
			for(MenuItem i :childs){
				this.menu.remove(i);
			}
		}*/
		registeParent(item);
//		else{
//			registeParent(item);
//		}
	}
	
	public void addList(MenuItem item){
		if(!this.menu.contains(item)){
			this.menu.add(item);
		}
	}
	
	public MenuItem getRegistedMenu(MenuItem item){
		if(!this.menu.contains(item)){
			return item;
		}
		return this.menu.get(this.menu.indexOf(item));
	}
	
	private void setPath(MenuItem item) {
		if(containsChild(item)){
			List<MenuItem> childs = getChild(item);			
			for(MenuItem i :childs){
				i.setPath(item.getPath()+" > "+i.getText());	
				setPath(i);
			}
			item.setChildren(childs);
			for(MenuItem i :childs){
				this.menu.remove(i);
			}
		}		
	}

	private boolean containsParaent(MenuItem item){
		return processMap.containsKey(item.getParentId());
	}
	
	private MenuItem getParent(MenuItem item){
		return processMap.get(item.getParentId());
	}
	
	private boolean containsChild(MenuItem item){
		return parentMap.containsKey(item.getId());
	}
	
	private List<MenuItem> getChild(MenuItem item){
		return parentMap.get(item.getId());
	}
	
	//登记找爸爸
	private void registeParent(MenuItem item){
		if(item.getId().equalsIgnoreCase(item.getParentId()))
			return;
		if(parentMap.containsKey(item.getParentId())){
			if(!parentMap.get(item.getParentId()).contains(item))				
				parentMap.get(item.getParentId()).add(item.clone());
		}else{
			List<MenuItem> p = new ArrayList<MenuItem>();
			p.add(item.clone());
			parentMap.put(item.getParentId(), p);
		}
		
	}
}
