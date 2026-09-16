package com.a2zinvoices.app;
import org.json.JSONObject;
public class CompanyInfo {
 public String name="",activity="",address="",commercialRegister="",taxId="",articleNumber="",phone="";
 public JSONObject toJson(){JSONObject o=new JSONObject();try{o.put("name",name);o.put("activity",activity);o.put("address",address);o.put("commercialRegister",commercialRegister);o.put("taxId",taxId);o.put("articleNumber",articleNumber);o.put("phone",phone);}catch(Exception ignored){}return o;}
 public static CompanyInfo fromJson(JSONObject o){CompanyInfo c=new CompanyInfo();c.name=o.optString("name","");c.activity=o.optString("activity","");c.address=o.optString("address","");c.commercialRegister=o.optString("commercialRegister","");c.taxId=o.optString("taxId","");c.articleNumber=o.optString("articleNumber","");c.phone=o.optString("phone","");return c;}
}