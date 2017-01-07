package com.ut.comm.xml.sys;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class SchemaMapping extends AbsObject{
	private static final long serialVersionUID = -1865078744895197903L;

	public SchemaMapping(){
		super();
		XMLParaHelper.registeObjectBean("schema", Schema.class);
	} 

	private List<Schema> schemaList ;
	
	public void setSchemaList(List<Schema> schemaList) {
		this.schemaList = schemaList;
	}
	
	public Schema getSchemaPara(int index) {
		Schema refPara = schemaList.get(index);
		return refPara;
	}
	
	public void setSchema(Schema ref,int index) {
		if(this.schemaList==null)
			this.schemaList = new ArrayList<Schema>();
		if(index>this.schemaList.size())
			this.schemaList.add(ref);
	}
	
	public void setSchema(Schema schema) {
		if(this.schemaList==null)
			this.schemaList = new ArrayList<Schema>();
		this.schemaList.add(schema);
	}
	
	public int getSchemaSize(){
		return this.schemaList==null?0:this.schemaList.size();
	}
	
	public String getMappingSchema(String type){
		for (int i = 0; i < schemaList.size(); i++) {
			Schema schema = getSchemaPara(i);
			if(schema.getType().equalsIgnoreCase(type))
				return schema.getSchema();
		}
		return type;
	}

	@Override
	public String getNodeName() {
		return "mappingschema";
	}
}
