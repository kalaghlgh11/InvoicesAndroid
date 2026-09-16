package com.a2zinvoices.app;
import org.json.JSONObject;
import java.util.UUID;
public class Customer {
 public String id=UUID.randomUUID().toString(), name="", activity="", address="", phone="", commercialRegister="", taxId="";
 public JSONObject toJson(){JSONObject o=new JSONObject(); try{o.put("id",id);o.put("name",name);o.put("activity",activity);o.put("address",address);o.put("phone",phone);o.put("commercialRegister",commercialRegister);o.put("taxId",taxId);}catch(Exception ignored){} return o;}
 public static Customer fromJson(JSONObject o){Customer c=new Customer(); c.id=o.optString("id",c.id);c.name=o.optString("name","");c.activity=o.optString("activity","");c.address=o.optString("address","");c.phone=o.optString("phone","");c.commercialRegister=o.optString("commercialRegister","");c.taxId=o.optString("taxId","");return c;}
 public String toString(){return name;}
}