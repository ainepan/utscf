package com.ut.comm.xml.task;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.XMLParaParseHelper;

public class TasksPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TasksPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_TASK, TaskPara.class);
	}
	
	private List<TaskPara> taskList;

	public void setTask(TaskPara filedObj) {
		if(taskList ==null)
			taskList = new ArrayList<TaskPara>();
		taskList.add(filedObj);
	}
	
	public TaskPara getTask(int index) {
		TaskPara taskObj = taskList.get(index);
		if(null != taskObj.getRules()){
			taskObj.getRules().clear();;
		}
		taskObj = XMLParaParseHelper.parseTaskPara(taskObj,super.getBu());
		return taskObj;
	}

	public int size(){
		if(taskList ==null)
			taskList = new ArrayList<TaskPara>();
		return taskList.size();
	}
	
	public List<TaskPara> getTask(){
		return taskList;
	}
	@Override
	public String getNodeName() {
		return "aftertasks,beforetasks";
	}
}
