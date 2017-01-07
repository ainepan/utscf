package com.ut.comm.xml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.ut.comm.cache.CommCacheManager;
import com.ut.comm.cache.ICacheClient;
import com.ut.comm.cache.ICacheManager;
import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.ErrorUtil;
import com.ut.comm.tool.xml.XMLManager;
import com.ut.comm.xml.accounting.AccountingNoPara;
import com.ut.comm.xml.advice.AdvicePara;
import com.ut.comm.xml.app.AppPara;
import com.ut.comm.xml.batch.BatchPara;
import com.ut.comm.xml.batch.BatchsPara;
import com.ut.comm.xml.catalog.CatalogPara;
import com.ut.comm.xml.catalog.FieldObj;
import com.ut.comm.xml.comm.CommunicationPara;
import com.ut.comm.xml.edi.EDIPara;
import com.ut.comm.xml.esb.ESBServicePara;
import com.ut.comm.xml.esb.ESBServicesPara;
import com.ut.comm.xml.func.FunctionPara;
import com.ut.comm.xml.gapi.GapiMsgPara;
import com.ut.comm.xml.gapi.GapiPara;
import com.ut.comm.xml.inqdata.InquireDataPara;
import com.ut.comm.xml.logicflow.LogicFlowPara;
import com.ut.comm.xml.method.MethodDefinePara;
import com.ut.comm.xml.page.PagePara;
import com.ut.comm.xml.party.PartyPara;
import com.ut.comm.xml.product.ProductPara;
import com.ut.comm.xml.query.QueryPara;
import com.ut.comm.xml.report.ReportPara;
import com.ut.comm.xml.root.SysRootPara;
import com.ut.comm.xml.service.ServicePara;
import com.ut.comm.xml.sys.SysPara;
import com.ut.comm.xml.task.TaskPara;
import com.ut.comm.xml.template.TemplatePara;
import com.ut.comm.xml.workflow.WorkFlowPara;

public class XMLParaParseHelper {
	private static Logger logger = LoggerFactory.getLogger(XMLParaParseHelper.class);
	
	public static FunctionPara parseFuncPara(String funcID, String bu) {
		FunctionPara obj= new FunctionPara();
		obj.setId(funcID);
		return parseFuncPara(obj,bu);
	}
	
	public static <T extends AbsObject> T parseApPara(T obj,String prefix, String bu){
		Assert.notNull(obj.getId(),"Current object must has id.");
		T retObj = getFromCache(obj,prefix,bu);
		if(retObj!=null){
			return retObj;
		}
			
		
		Document funcPara;
		try {
			funcPara = XMLParaLoadHelper.loadApParaDefine(obj.getId(),prefix,bu);
			XMLParaHelper.parseXml2Bean(obj, funcPara.getDocumentElement());
			obj.setBu(bu);
//			XMLParaHelper.registeObject(prefix, obj.getClass());
		} catch (IOException e) {
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (Exception e) {
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}
		
		saveIntoCache(obj,prefix,bu);
		return obj;
	}
	private static <T extends AbsObject> T getFromCache(T obj,String prefix, String bu) {
		SysRootPara root = XMLParaParseHelper.parseSystPara();
		String devModle = root.getDevModle();
		if("true".equalsIgnoreCase(devModle)){
			return null;
		}
		String key = obj.getId()+"_"+prefix+"_"+bu;
		ICacheClient cc = getParaCacheManager();
		if(cc!=null&&cc.existData(key)){
			logger.debug("Get para object into cache:"+obj.getId());
			return (T) cc.getData(key);
		}

		return null;
	}
	
	private static <T extends AbsObject> T saveIntoCache(T obj ,String prefix, String bu) {
		SysRootPara root = XMLParaParseHelper.parseSystPara();
		String devModle = root.getDevModle();
		if("true".equalsIgnoreCase(devModle)){
			return null;
		}
		String key = obj.getId()+"_"+prefix+"_"+bu;
		
		ICacheClient cc = getParaCacheManager();
		if(cc!=null){
			cc.createData(key, obj);
			logger.debug("Save para object into cache:"+obj.getId());
		}

		return null;
	}
	
	public static <T extends AbsObject> T clearCache(T obj ,String prefix, String bu) {
		SysRootPara root = XMLParaParseHelper.parseSystPara();
		String devModle = root.getDevModle();
		if("true".equalsIgnoreCase(devModle)){
			return null;
		}
		String key = obj.getId()+"_"+prefix+"_"+bu;
		
		ICacheClient cc = getParaCacheManager();
		if(cc!=null&&cc.existData(key)){
			cc.removeData(key);
			logger.debug("Clear para object in cache:"+obj.getId());
		}

		return null;
	}
	
	private static ICacheClient getParaCacheManager() {
		ICacheManager cm = CommCacheManager.getInstance();
		try {
			return cm.getCache("CACHE_PARA_DATA");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T extends AbsObject> T parsePara(T obj,String prefix){
		Assert.notNull(obj.getId(),"Current object must has id.");
		T retObj = getFromCache(obj,prefix,"");
		if(retObj!=null){
			return retObj;
		}
		obj = parseParaWithoutCache(obj,prefix);
		saveIntoCache(obj,prefix,"");
		return obj;
	}
	
	public static <T extends AbsObject> T parseParaWithoutCache(T obj,String prefix){
		Assert.notNull(obj.getId(),"Current object must has id.");
		Document funcPara;
		try {
			funcPara = XMLParaLoadHelper.loadParaDefine(obj.getId(),prefix);
			XMLParaHelper.parseXml2Bean(obj, funcPara.getDocumentElement());
		} catch (IOException e) {
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (Exception e) {
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}
		return obj;
	}
	
	public static FunctionPara parseFuncPara(FunctionPara funcObj, String bu) {
		return parseApPara(funcObj,"func",bu);
	}
	
	
	public static LogicFlowPara parseFuncLogicFlow(LogicFlowPara funcLogicObj, String bu) {
		return parseApPara(funcLogicObj,"logicflow",bu);
	}
	
	public static LogicFlowPara parseFuncLogicFlow(String logicId, String bu) {
		LogicFlowPara funcLogicObj = new LogicFlowPara();
		funcLogicObj.setId(logicId);
		return parseFuncLogicFlow(funcLogicObj,bu);
	}
	
	public static PagePara parsePage(PagePara pagePara, String bu) {
		return parseApPara(pagePara,"page",bu);
	}
	
	public static ReportPara parseReport(String reportId, String bu) {
		ReportPara reprot = new ReportPara();
		reprot.setId(reportId);
		return parseApPara(reprot,"report",bu);
	}
	
	public static ReportPara parseReport(ReportPara reportPara, String bu) {
		return parseApPara(reportPara,"report",bu);
	}
	
	public static QueryPara parseQuery(QueryPara queryPara, String bu) {
		return parseApPara(queryPara,"query",bu);
	}
	
	public static QueryPara parseQuery(String queryId, String bu) {
		QueryPara queryPara = new QueryPara();
		queryPara.setId(queryId);
		return parseQuery(queryPara,bu);
	}
	
	public static ServicePara parseFuncService(ServicePara serviceObj, String bu) {
		return parseApPara(serviceObj,"service",bu);
	}
	
	public static ServicePara parseFuncService(String serviceId, String bu) {
		ServicePara serviceObj = new ServicePara();
		serviceObj.setId(serviceId);
		return parseFuncService(serviceObj,bu);
	}
	
	public static CatalogPara parseFuncCatalog(String catalogID, String bu) {
		CatalogPara obj = new CatalogPara();
		obj.setId(catalogID);
		return parseFuncCatalog(obj,bu);
	}
	
	public static CatalogPara parseFuncCatalog(CatalogPara funcCatalogObj, String bu) {
		return parseApPara(funcCatalogObj,"cata",bu);
	}
	
	/**
	 * user [parseFieldAttribute] instead of
	 * @param funcCatalogObj
	 * @param currentNode
	 * @return
	 */
	@Deprecated
	public static FieldObj parseCatalogField(FieldObj funcCatalogObj,Node currentNode){
		Map attributes = XMLManager.convertToHashMap(currentNode);
		BeanUtils.setBeanProperty(funcCatalogObj, attributes);
		return funcCatalogObj;
	}
	
	public static <T> T parseFieldAttribute(T paraObj,Node currentNode){
		Map attributes = XMLManager.convertToHashMap(currentNode);
		BeanUtils.setBeanProperty(paraObj, attributes);
		return paraObj;
	}
	
	public static InquireDataPara parseInqDataPara(String inqID, String bu) {
		InquireDataPara obj = new InquireDataPara();
		obj.setId(inqID);
		return parseInqDataPara(obj,bu);
	}
	
	public static InquireDataPara parseInqDataPara(InquireDataPara inqPara, String bu) {
		return parseApPara(inqPara,"inqdata",bu);
	}
	
	public static SysPara parseSysPara(String bu) {
		SysPara obj = new SysPara();
		obj.setId("SYSPara");
		return parseApPara(obj,"syst",bu);
	}
	
	
	public static TemplatePara parseFuncTemplatePara(String templateId, String bu) {
		TemplatePara templatePara = new TemplatePara();
		templatePara.setId(templateId);
		return parseFuncTemplatePara(templatePara,bu);
	}
	
	public static TemplatePara parseFuncTemplatePara(TemplatePara funcTemplatePara, String bu) {
		return parseApPara(funcTemplatePara,"template",bu);
	}
	
	public static BatchsPara parseBatchsPara(String bu) {
		BatchsPara batchs = new BatchsPara();
		batchs.setId("Batch_Root");
		return parseApPara(batchs,"syst",bu);
	}
	
	public static BatchPara parseBatchPara(BatchPara batchPara, String bu) {
		return parseApPara(batchPara,"batch",bu);
	}
	
	public static TaskPara parseTaskPara(TaskPara tPara, String bu){
		return parseApPara(tPara,"task",bu);
	}
	
	public static TaskPara parseTaskPara(String tId, String bu){
		TaskPara tPara = new TaskPara();
		tPara.setId(tId);
		return parseTaskPara(tPara,bu);
	}
	
	public static GapiPara parseGapiPara(String batchPara, String bu) {
		GapiPara gapi = new GapiPara();
		gapi.setId(batchPara);
		return parseGapiPara(gapi,bu);
	}
	
	public static GapiPara parseGapiPara(GapiPara gapiPara, String bu){
		return parseApPara(gapiPara,"gapi",bu);
	}
	
	public static GapiMsgPara parseGapiMsgPara(String gapiMsgId, String bu) {
		GapiMsgPara gapi = new GapiMsgPara();
		gapi.setId(gapiMsgId);
		return parseGapiMsgPara(gapi,bu);
	}
	
	public static GapiMsgPara parseGapiMsgPara(GapiMsgPara gapiMsgPara, String bu){
		return parseApPara(gapiMsgPara,"gapimsg",bu);
	}
	
	public static EDIPara parseEDIPara(String ediId,String bu){
		EDIPara edi = new EDIPara();
		edi.setId(ediId);
		return parseEDIPara(edi,bu);
	}
	
	public static EDIPara parseEDIPara(EDIPara edi,String bu){
		return parseApPara(edi,"edi",bu);
	}
	
	public static ESBServicesPara parseESBSvicesPara() {
		ESBServicesPara batchPara = new ESBServicesPara();
		batchPara.setId("ESB_Root");
		return parsePara(batchPara,"syst");
	}
	
	public static ESBServicePara parseESBSvicePara(ESBServicePara servicePara) {
		return parsePara(servicePara,"esb"+File.separator+"services");
	}
	
	public static ESBServicePara parseESBSvicePara(String serviceId) {
		ESBServicePara servicePara = new ESBServicePara();
		servicePara.setId(serviceId);
		return parsePara(servicePara,"esb"+File.separator+"services");
	}
	
	public static AdvicePara parseAdvicePara(String adviceId, String bu) {
		AdvicePara reprot = new AdvicePara();
		reprot.setId(adviceId);
		return parseAdvicePara(reprot,bu);
	}
	
	public static AdvicePara parseAdvicePara(AdvicePara advice, String bu) {
		return parseApPara(advice,"advice",bu);
	}
	
	public static ProductPara parseProductPara(String productId, String bu) {
		ProductPara reprot = new ProductPara();
		reprot.setId(productId);
		return parseProductPara(reprot,bu);
	}
	
	public static ProductPara parseProductPara(ProductPara advice, String bu) {
		return parseApPara(advice,"product",bu);
	}
	
	public static SysRootPara parseSystPara() {
		SysRootPara rootPara = new SysRootPara();
		rootPara.setId("Root_Para");
		return parseParaWithoutCache(rootPara,"syst");
	}
	
	public static WorkFlowPara parseWorkflowPara(String productId, String bu) {
		WorkFlowPara reprot = new WorkFlowPara();
		reprot.setId(productId);
		return parseWorkflowPara(reprot,bu);
	}
	
	public static WorkFlowPara parseWorkflowPara(WorkFlowPara advice, String bu) {
		return parseApPara(advice,"workflow",bu);
	}
	
	public static AccountingNoPara parseAccountNoPara(String productId, String bu) {
		AccountingNoPara accountNo = new AccountingNoPara();
		accountNo.setId(productId);
		return parseAccountNoPara(accountNo,bu);
	}
	
	public static AccountingNoPara parseAccountNoPara(AccountingNoPara accountNo, String bu) {
		return parseApPara(accountNo,"account",bu);
	}
	
	public static PartyPara parsePartyPara(String partyId, String bu) {
		PartyPara party = new PartyPara();
		party.setId(partyId);
		return parsePartyPara(party,bu);
	}
	
	public static PartyPara parsePartyPara(PartyPara party, String bu) {
		return parseApPara(party,XMLParaHelper.PARA_PATH_PARTY,bu);
	}
	
	public static AppPara parseAppPara(String appId, String bu) {
		AppPara appPara = new AppPara();
		appPara.setId(appId);
		return parseAppPara(appPara,bu);
	}
	
	public static AppPara parseAppPara(AppPara app, String bu) {
		return parseApPara(app,XMLParaHelper.PARA_PATH_APP,bu);
	}
	
	public static MethodDefinePara parseMethodDefinePara(String mId, String bu) {
		MethodDefinePara methodPara = new MethodDefinePara();
		methodPara.setId(mId);
		return parseMethodDefinePara(methodPara,bu);
	}
	
	public static MethodDefinePara parseMethodDefinePara(MethodDefinePara method, String bu) {
		return parseApPara(method,XMLParaHelper.PARA_PATH_METHOD_DEFINE,bu);
	}
	
	public static CommunicationPara parseCommunicationPara(String mId, String bu) {
		CommunicationPara methodPara = new CommunicationPara();
		methodPara.setId(mId);
		return parseCommunicationPara(methodPara,bu);
	}
	
	public static CommunicationPara parseCommunicationPara(CommunicationPara comm, String bu) {
		return parseApPara(comm,XMLParaHelper.PARA_PATH_COMM,bu);
	}
	
}
