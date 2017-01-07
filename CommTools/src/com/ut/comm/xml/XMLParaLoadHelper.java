package com.ut.comm.xml;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;

import com.ut.comm.tool.FileUtil;
import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.tool.xml.XMLManager;

public class XMLParaLoadHelper {
	public static final String AP_PARA_DEFAULT_PATH = ASPathConst.getDefaultBuName();
	
	
	public static Document loadApParaDefine(String id,String prefix,String bu) throws Exception, IOException{
		if(StringUtil.isTrimEmpty(bu)){
			bu = AP_PARA_DEFAULT_PATH;
		}
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());	
		paraPath.append(File.separator).append(bu).append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		return XMLManager.xmlFileToDom(paraPath.toString());
	}
	
	public static Document loadParaDefine(String id,String prefix) throws Exception, IOException{
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		
		return XMLManager.xmlFileToDom(paraPath.toString());
	}
	
	public static String getApParaDeinePath(String prefix,String bu){
		if(StringUtil.isTrimEmpty(bu)){
			bu = AP_PARA_DEFAULT_PATH;
		}
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(bu).append(File.separator).append(prefix).append(File.separator);
		return paraPath.toString();
	}
	
	public static void writeApParaDefine(String id,String prefix,String bu,Document content) throws Exception, IOException{
		if(StringUtil.isTrimEmpty(bu)){
			bu = AP_PARA_DEFAULT_PATH;
		}
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(bu).append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		
		XMLManager.writeXMLtoFile(paraPath.toString(), content);
	}
	
	public static void writeParaDefine(String id,String prefix,Document content) throws Exception, IOException{
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		
		XMLManager.writeXMLtoFile(paraPath.toString(), content);
	}
	
	public static void deleteApParaDefine(String id,String prefix,String bu) throws Exception, IOException{
		if(StringUtil.isTrimEmpty(bu)){
			bu = AP_PARA_DEFAULT_PATH;
		}
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(bu).append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		
		File f = new File(paraPath.toString());
		if(f.exists())
			f.delete();
	}
	
	public static void deleteParaDefine(String id,String prefix) throws Exception, IOException{
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append(prefix)
		.append(File.separator).append(id).append(".xml");
		
		File f = new File(paraPath.toString());
		if(f.exists())
			f.delete();
	}
	
	public static Document loadFuncPara(String funcID, String bu) throws Exception, IOException{
		return loadApParaDefine(funcID,"func",bu);
	}
	
	public static Document loadFuncLogicFlowPara(String logicID, String bu) throws Exception, IOException{
		return loadApParaDefine(logicID,"logicflow",bu);
	}
	
	public static Document loadFuncServicePara(String serviceID, String bu) throws Exception, IOException{
		return loadApParaDefine(serviceID,"service",bu);
	}
	
	public static Document loadFuncCatalogPara(String cataId, String bu) throws Exception, IOException{
		return loadApParaDefine(cataId,"cata",bu);
	}
	
	public static Document loadPagePara(String pageID, String bu) throws Exception, IOException{
		return loadApParaDefine(pageID,"page",bu);
	}
	
	public static Document loadReportPara(String reportID, String bu) throws Exception, IOException{
		return loadApParaDefine(reportID,"report",bu);
	}
	
	public static Document loadQueryPara(String pageID, String bu) throws Exception, IOException{
		return loadApParaDefine(pageID,"query",bu);
	}
	
	public static Document loadInqDataPara(String inqId, String bu) throws Exception, IOException{
		return loadApParaDefine(inqId,"inqdata",bu);
	}
	
	public static Document loadSysPara(String bu) throws Exception, IOException{
		return loadApParaDefine("SYSPara","syst",bu);
	}
	
	public static Document loadFuncTemplatePara(String templateId, String bu) throws Exception, IOException{
		return loadApParaDefine(templateId,"template",bu);
	}
	
	public static Document loadBatchsPara(String bu) throws Exception, IOException{
		return loadApParaDefine("root","batch",bu);
	}
	
	public static Document loadBatchPara(String batchId, String bu) throws Exception, IOException{
		return loadApParaDefine(batchId,"batch",bu);
	}
	
	public static Document loadTaskPara(String taskId, String bu) throws Exception, IOException{
		return loadApParaDefine(taskId,"task",bu);
	}
	
	public static Document loadEDITemplatePara(String tempId,String bu) throws Exception, IOException{
		return loadApParaDefine(tempId,"template"+File.separator+"edi",bu);
	}
	
	public static Document loadEDIMappingPara(String mappingId,String bu) throws Exception, IOException{
		return loadApParaDefine(mappingId,"edi"+File.separator+"mapping",bu);
	}
	
	public static Document loadGAPITemplatePara(String tempId,String bu) throws Exception, IOException{
		return loadApParaDefine(tempId,"gapitemp",bu);
	}
	
	public static Document loadGAPIMappingPara(String mappingId,String bu) throws Exception, IOException{
		return loadApParaDefine(mappingId,"gapimapping",bu);
	}
	
	public static Document loadProductPara(String productId,String bu) throws Exception, IOException{
		return loadApParaDefine(productId,"product",bu);
	}
}
