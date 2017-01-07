package com.ut.comm.tool.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.Criterion;

import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.string.StringUtil;

public class ExpressionHelper {

	protected static final String eq = "=";
	protected static final String ne = "<>";
	protected static final String like = " like ";
	protected static final String gt = ">";
	protected static final String lt = "<";
	protected static final String le = "<=";
	protected static final String ge = ">=";
	protected static final String between = " between ";
	protected static final String in = " in ";
	protected static final String isNull = "is Null";
	protected static final String isNotNull = "is Not Null";
	protected static final String and = " and ";
	protected static final String or = " or ";
	protected static final String not = " not ";
	protected static final String l_parenthesis = ")";
	protected static final String r_parenthesis = "(";
	protected static final String escape = "\\";
	protected static final String separator = ",";
	protected static final String quotation = "'";
	protected static final String sys_express = "$";

	protected static Map<String, String> operatorMap = new HashMap<String, String>();
	protected static Map<String, String> operatorNameMap = new HashMap<String, String>();
	protected static Map<String, String> relatedMap = new HashMap<String, String>();

	static {
		operatorMap.put(eq, eq);
		operatorMap.put(ne, ne);
		operatorMap.put(like, like);
		operatorMap.put(gt, gt);
		operatorMap.put(lt, lt);
		operatorMap.put(le, le);
		operatorMap.put(ge, ge);
		operatorMap.put(between, between);
		operatorMap.put(in, in);
		operatorMap.put(isNull, "isNull");
		operatorMap.put(isNotNull, isNotNull);

		operatorNameMap.put(eq, "eq");
		operatorNameMap.put(ne, "ne");
		operatorNameMap.put(like, "like");
		operatorNameMap.put(gt, "gt");
		operatorNameMap.put(lt, "lt");
		operatorNameMap.put(le, "le");
		operatorNameMap.put(ge, "ge");
		operatorNameMap.put(between, "between");
		operatorNameMap.put(in, "in");
		operatorNameMap.put(isNull, "isNull");
		operatorNameMap.put(isNotNull, "isNotNull");

		relatedMap.put(and, and);
		relatedMap.put(or, or);
		// operatorMap.put(not, not);
	}

	private static ExpressionHelper helperInstence;

	private ExpressionHelper() {

	}

	public static ExpressionHelper getInstence() {
		if (helperInstence == null)
			helperInstence = new ExpressionHelper();

		return helperInstence;
	}

	public List<String> splitToList(String str) {
		List<String> retList = new ArrayList<String>();
		if (StringUtil.isTrimEmpty(str)) {
			return retList;
		}
		String[] strs = str.split(separator);
		for (int i = 0; i < strs.length; i++) {
			retList.add(strs[i]);
		}
		return retList;
	}

	public String getOperatorName(String op) {
		return operatorNameMap.get(op);
	}

	public static void main(String[] args) {
		String testCondition = "(sysEventTimes>0andsysTrxSts=M)or((sysEventTimes=1andsysRefNo=C003) or(sysRefNo=C004 and sysEventTimes=2))or(sysEventTimes=3and sysRefNo=C004)";
		List<ExpressBox> o = ExpressionHelper.getInstence().parseSql(testCondition);

		System.out.println(o);

		// ExpressEntity op = new ExpressEntity("a='1'");
		//
		// System.out.println(op);
	}

	// public Map parseStrCondition(String condition) {
	//
	// }
	//
	public String praseMapCondition(String source, Map condition) {
		Map<String, String> formatMap = convertMap(condition);
		if(StringUtil.isNull(source)&&condition.isEmpty())
			return "";
		StringBuffer buff = new StringBuffer("(");
		if(StringUtil.isTrimNotEmpty(source)){
			buff.append(source).append(")");
			buff.append(" and (");
		}
	
		Set<Entry<String, String>> set = formatMap.entrySet();
		for (Entry<String, String> en : set) {
			buff.append(en.getKey()).append("=").append(en.getValue());
			buff.append(" and ");
		}
		if (buff.toString().endsWith(" and ")) {
			buff.delete(buff.length() - " and ".length(), buff.length());
		}
		if (!buff.toString().endsWith("(")) {
			buff.append(")");
		}
		return buff.toString();

	}

	private Map<String, String> convertMap(Map<String, Object> condition) {
		return convertMap("",condition);
	}

	private Map<String, String> convertMap(String preFix ,Map<String, Object> reuqestMap) {
		Map<String, String> retMap = new HashMap<String, String>();
		Set<Entry<String, Object>> set = reuqestMap.entrySet();
		for (Entry<String, Object> en : set) {
			if (en.getValue() == null)
				continue;
			if (BeanUtils.isBaseJavaType(en.getValue().getClass())) {
				retMap.put(getFieldName(preFix,en.getKey()), String.valueOf(en.getValue()));
			} else {
				Map tempMap = BeanUtils.toMap(en.getValue());
				Map<String, String> childMap = convertMap(en.getKey(),tempMap);
				if (!childMap.isEmpty())
					retMap.putAll(childMap);
			}
		}
		return retMap;
	}
	
	private String getFieldName(String preFix,String name){
		if(StringUtil.isTrimEmpty(preFix))
			return name;
		return new StringBuffer(preFix).append(".").append(name).toString();
	}

	//
	// public Criterion parseCriterion(String condition) {
	//
	// }

	public String getOperator(String express) {
		Set<Entry<String, String>> set = this.operatorMap.entrySet();
		for (Entry<String, String> en : set) {
			if (express.contains(en.getKey())) {
//				return en.getKey().trim();
				return en.getKey();
			}
		}
		return "";
	}

	// 处理最基本的表达式单元，没有（）
	private Express parseSingleExpress(String express) {
		if (StringUtil.isTrimEmpty(express))
			return null;
		if (express.contains(and)) {
			String preExpress = express.substring(0, express.indexOf(and));
			String subExpress = express.substring(express.indexOf(and) + and.length(), express.length());
			Express preEx = parseSingleExpress(preExpress);
			Express subEx = parseSingleExpress(subExpress);
			if (preEx != null) {
				preEx.setNextExpress(subEx);
				preEx.setOperator(and);
			} else if (subEx != null)
				subEx.setOperator(and);
			else
				return new Express(and);
			return preEx;
		}
		else if (express.contains(or)) {
			String preExpress = express.substring(0, express.indexOf(or));
			String subExpress = express.substring(express.indexOf(or) + or.length(), express.length());
			Express preEx = parseSingleExpress(preExpress);
			Express subEx = parseSingleExpress(subExpress);
			if (preEx != null) {
				preEx.setOperator(or);
				preEx.setNextExpress(subEx);
			} else if (subEx != null)
				subEx.setOperator(or);
			else
				return new Express(or);
			return preEx;
		} 
		else {
			return new Express(express);
		}
	}

	// 处理表达式，转换成List，但是每次只处理一层，不自动递归
	public List<String> parseSqlToExpressList(String input) {
//		input = trimAll(input, " ");
		int index = 0;
		int begin = 0;

		List<String> seqList = new ArrayList<String>();
		for (int i = 0; i < input.length(); i++) {
			Character c = input.charAt(i);
			if (c.toString().equals(r_parenthesis)) {

				if (index == 0) {
					String seqStr = input.substring(begin, i);
					if (seqStr.endsWith(and)) {
						if (StringUtil.isTrimNotEmpty(input.substring(begin, i - and.length())))
							seqList.add(input.substring(begin, i - and.length()));
						seqList.add(and);
					} else if (seqStr.endsWith(or)) {
						if (StringUtil.isTrimNotEmpty(input.substring(begin, i - or.length())))
							seqList.add(input.substring(begin, i - or.length()));
						seqList.add(or);
					} else {
						if (StringUtil.isTrimNotEmpty(input.substring(begin, i)))
							seqList.add(input.substring(begin, i));
					}
					begin = i + 1;
				}

				index++;
			} else if (c.toString().equals(l_parenthesis)) {
				index--;
				if (index == 0) {
					seqList.add(input.substring(begin, i));
					begin = i + 1;
				}
			}
		}

		if (begin != input.length()) {
			String seqStr = input.substring(begin, input.length());
			if (seqStr.startsWith(and)) {
				seqList.add(and);
				seqList.add(input.substring(begin + and.length(), input.length()));

			} else if (seqStr.startsWith(or)) {
				seqList.add(or);
				seqList.add(input.substring(begin + or.length(), input.length()));
			} else {
				seqList.add(input.substring(begin, input.length()));
			}
		}

		return seqList;
	}

	// 处理最外层Box，Box之间没有关联关系
	public List<ExpressBox> parseSql(String input) {
		List<ExpressBox> exsList = new ArrayList<ExpressBox>();
		if(StringUtil.isTrimEmpty(input))
			return exsList;
		List<String> seqList = parseSqlToExpressList(input);
		
		for (String express : seqList) {
			ExpressBox box = parseSingleBox(express);
			
			if (box != null){
				if(box.isOperatorBox()){
					exsList.get(exsList.size()-1).setBoxOperator(box.getBoxOperator());
				}else{
					exsList.add(box);
				}			
			}
		}
		return exsList;
	}

	// 逐层处理Box，递归调用自己
	public ExpressBox parseSingleBox(String input){
		List<String> seqList = parseSqlToExpressList(input);
		ExpressBox box = new ExpressBox();
		List<ExpressBox> exsList = new ArrayList<ExpressBox>();
		for (String express : seqList) {
			if(StringUtil.isTrimEmpty(express))
				continue;
			if(express.contains(r_parenthesis)){
				ExpressBox childBox = parseSingleBox(express);
				if (!exsList.isEmpty()){
					childBox.setPreBox(exsList.get(exsList.size() - 1));
				}
				exsList.add(childBox);
				
			}else{
				Express ex = parseSingleExpress(express);//叶子盒子或者操作盒子
				ExpressBox newBox = new ExpressBox();
				if(ex.getExpress()==null){
					newBox.setBoxOperator(ex);
				}else{
					newBox.setBoxExpress(ex);
				}
				if (exsList.isEmpty()){
					exsList.add(newBox);
				}else{
					if(newBox.isOperatorBox()){
						exsList.get(exsList.size() - 1).setBoxOperator(ex);
					}else{
						newBox.setPreBox(exsList.get(exsList.size() - 1));
						exsList.add(newBox);
					}
				}
			}
			
		}
		if(exsList.get(0).isOperatorBox()){
			box.setBoxOperator(exsList.get(0).getBoxOperator());
		}else{
			box.setChildBox(exsList.get(0));
		}
		
		return box;
	}

	private String trimAll(String input, String a) {
		return input.replace(a, "");
	}

	private static String getSplitName(String c) {
		return escape + c;
	}

}
