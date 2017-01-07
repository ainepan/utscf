package com.ut.comm.tool.sql;

import org.springframework.util.Assert;

public class ExpressEntity {
	private String fieldName;
	private String fieldValue;
	private String op;
	public ExpressEntity(String currentExpress) {
		op = ExpressionHelper.getInstence().getOperator(currentExpress);
		Assert.hasText(op,String.format("Cannot prase operator from current express[].", currentExpress));
		
		fieldName = currentExpress.substring(0,currentExpress.indexOf(op)).trim();
		fieldValue = currentExpress.substring(currentExpress.indexOf(op)+op.length()).trim();
		
		Assert.hasText(op,String.format("Cannot prase field name from current express[].", currentExpress));
		
		Assert.hasText(op,String.format("Cannot prase field value from current express[].", currentExpress));
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public String getOpName(){
		return ExpressionHelper.getInstence().getOperatorName(op);
	}
	
	
	@Override
	public String toString(){
		return String.format("Field Name: [%s] Operator: [%s] Field Value: [%s].", this.fieldName,this.op,this.fieldValue);
	}
}
