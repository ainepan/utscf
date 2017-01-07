package com.ut.comm.xml.batch;


import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.task.TaskPara;
import com.ut.comm.xml.task.TasksPara;

public class BatchPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BatchPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_SCHEDULE, SchedulePara.class);
		XMLParaHelper.registeObjectBean("beforetasks", TasksPara.class);
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_TASK, TaskPara.class);
		XMLParaHelper.registeObjectBean("aftertasks", TasksPara.class);
	}
	
	private String name;
	private String desc;
	private SchedulePara schedule;
	/**
	 * M : manual batch
	 * A : auto batch
	 */
	private String type;
	
	private TasksPara beforetasks;
	
	private TaskPara task;
	
	private TasksPara aftertasks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public SchedulePara getSchedule() {
		return schedule;
	}

	public void setSchedule(SchedulePara schedule) {
		this.schedule = schedule;
	}

	public TasksPara getBeforetasks() {
		return beforetasks;
	}

	public void setBeforetasks(TasksPara ts) {
		this.beforetasks = ts;
	}

	public TaskPara getTask() {
		//task = XMLParaParseHelper.parseTaskPara(task,super.getBu());
		return task;
	}

	public void setTask(TaskPara ts) {
		this.task = ts;
	}
	
	public TasksPara getAftertasks() {
		return aftertasks;
	}

	public void setAftertasks(TasksPara ts) {
		this.aftertasks = ts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getNodeName() {
		return  XMLParaHelper.NOTE_NAME_BATCH;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(obj instanceof BatchPara){
			String objBu = ((BatchPara)obj).getBu();
			if(com.ut.comm.tool.string.StringUtil.isTrimEmpty(objBu)){
				objBu = "";
			}
			
			return (this.getId().equalsIgnoreCase(((BatchPara)obj).getId()))&& (super.getBu().equalsIgnoreCase(objBu));
		}else{
			return false;
		}
	}
}
