package com.a2zinvoices.app;
import org.json.JSONObject; import java.util.UUID;
public class Supplier {
 public String id=UUID.randomUUID().toString(),name="",activity="",address="",phone="";
 public JSONObject toJson(){JSONObject o=new JSONObject();try{o.put("id",id);o.put("name",name);o.put("activity",activity);o.put("address",address);o.put("phone",phone);}catch(Exception ignored){}return o;}
 public static Supplier fromJson(JSONObject o){Supplier s=new Supplier();s.id=o.optString("id",s.id);s.name=o.optString("name","");s.activity=o.optString("activity","");s.address=o.optString("address","");s.phone=o.optString("phone","");return s;}
 public String toString(){return name;}
}