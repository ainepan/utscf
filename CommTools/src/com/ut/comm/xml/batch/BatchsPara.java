package com.ut.comm.xml.batch;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.XMLParaParseHelper;

public class BatchsPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BatchsPara(){
		super();
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_BATCH, BatchPara.class);
	}
	
	private List<BatchPara> batchList;
	
	public void setBatch(BatchPara filedObj) {
		if(batchList ==null)
			batchList = new ArrayList<BatchPara>();
		batchList.add(filedObj);
	}
	
	public BatchPara getBatch(int index) {
		BatchPara batchObj = batchList.get(index);
		XMLParaParseHelper.parseBatchPara(batchObj,super.getBu());
		return batchObj;
	}

	public int size(){
		if(batchList ==null)
			batchList = new ArrayList<BatchPara>();
		return batchList.size();
	}
	
	public List<BatchPara> getBatch(){
		return batchList;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_BATCHS_ROOT;
	}

	public List<BatchPara> getBatchList() {
		return batchList;
	}

	public void setBatchList(BatchPara batch) {
		setBatch(batch);
	}
}
