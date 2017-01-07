package com.ut.comm.tool.sql;

import com.ut.comm.tool.string.StringUtil;

public class ExpressBox {
	private Express boxExpress;
	
	private ExpressBox childBox;

	public ExpressBox getChildBox() {
		return childBox;
	}

	public void setChildBox(ExpressBox childBox) {
//		if(childBox.isOperatorBox()){
//			this.setBoxOperator(childBox.getBoxOperator());
//		}else{
//			this.childBox = childBox;
//		}
		this.childBox = childBox;
	}

	private ExpressBox nextBox;
	
	private ExpressBox preBox;
	
	public boolean isOperatorBox(){
		if(isEmptyBox())
			return false;
		
		return this.boxOperator!=null&&this.boxExpress==null;
	}
	
	private boolean isEmptyBox(){
		return this.boxExpress==null&&this.boxOperator==null;
	}
	
	public ExpressBox getPreBox() {
		return preBox;
	}

	public void setPreBox(ExpressBox preExpress) {
		if(preExpress!=null){
			this.preBox = preExpress;
			preExpress.setNextBox(this);
		}

	}

	private Express boxOperator;
	
	public Express getBoxExpress() {
		return boxExpress;
	}

	public void setBoxExpress(Express express) {
		if(express.getExpress()!=null)
			this.boxExpress = express;
		else if(StringUtil.isTrimNotEmpty(express.getOperator())){
			this.setBoxOperator(express);
		}else{
			//nothing
		}
			
	}

	public ExpressBox getNextBox() {
		return nextBox;
	}

	public void setNextBox(ExpressBox nextExpress) {
		this.nextBox = nextExpress;
	}

	public Express getBoxOperator() {
		return boxOperator;
	}

	public void setBoxOperator(Express operator) {
		this.boxOperator = operator;
	}
	
	@Override
	public String toString(){
		return String.format("Current box is [%s] and box operator is [%s].", this.getBoxExpress()==null?"":this.getBoxExpress().getExpress()
				,this.getBoxOperator()==null?"":this.getBoxOperator().getOperator());
	}
}
