package mongodb;

import net.sf.json.JSONObject;

public class NewHandleString extends handleString{
	String[] rs = new String[6];
	public NewHandleString() {
		// TODO Auto-generated constructor stub
		
	}
	public String[] start(String s) {
		//ȡ��_id��ֵ
		JSONObject object = JSONObject.fromObject(s);
		JSONObject id = JSONObject.fromObject(object.get("_id"));
		rs[0]=id.getString("$oid");
		rs[1]=",";
		//ȡ��filename��ֵ
		rs[2]=object.getString("filesname");
		rs[3]=",";
		//ȡ��date
		JSONObject date = JSONObject.fromObject(object.get("uploadDate"));
		rs[4]=date.getString("$date");
		rs[5]="/n";
		return rs;
		
	}
}
