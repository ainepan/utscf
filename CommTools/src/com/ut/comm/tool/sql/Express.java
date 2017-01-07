package com.ut.comm.tool.sql;

import com.ut.comm.tool.string.StringUtil;

public class Express {
	private static final String and = " and ";
	private static final String or = " or ";
	public Express(String express) {
		if(express.contains(and)){
			parse(express,and);
		}else if(express.contains(or)){
			parse(express,or);
		}else{
			parse(express,"");
		}
		
	}
	
	private void parse(String express,String operator){
		String strEntity = express.replace(operator, "").trim();
		if(StringUtil.isTrimNotEmpty(strEntity))
			currentExpress = new ExpressEntity(strEntity);
		this.operator = operator;
	}
	
	
	//a = '1'
	private ExpressEntity currentExpress;
	
	//and
	private String operator;
	
	//c= '2'
	private Express beforeExpress;
	private Express nextExpress;
	public ExpressEntity getExpress() {
		return currentExpress;
	}

//	public void setCurrentExpress(ExpressEntity currentExpress) {
//		this.currentExpress = currentExpress;
//	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Express getBeforeExpress() {
		return beforeExpress;
	}

	public void setBeforeExpress(Express beforeExpress) {
		this.beforeExpress = beforeExpress;
	}

	public Express getNextExpress() {
		return nextExpress;
	}

	public void setNextExpress(Express nextExpress) {
		if(nextExpress==null)
			return;
		this.nextExpress = nextExpress;
		nextExpress.setBeforeExpress(this);
	}
	
	@Override
	public String toString(){
		return String.format("Current express is [%s] and operator is [%s].", this.getExpress(),this.getOperator());
	}
}
