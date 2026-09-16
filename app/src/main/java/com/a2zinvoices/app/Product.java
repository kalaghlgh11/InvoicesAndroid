package com.a2zinvoices.app;
import org.json.JSONObject; import java.util.UUID;
public class Product {
 public String id=UUID.randomUUID().toString(),name="",unit="قطعة"; public double unitPrice=0,stock=0;
 public JSONObject toJson(){JSONObject o=new JSONObject();try{o.put("id",id);o.put("name",name);o.put("unit",unit);o.put("unitPrice",unitPrice);o.put("stock",stock);}catch(Exception ignored){}return o;}
 public static Product fromJson(JSONObject o){Product p=new Product();p.id=o.optString("id",p.id);p.name=o.optString("name","");p.unit=o.optString("unit","قطعة");p.unitPrice=o.optDouble("unitPrice",0);p.stock=o.optDouble("stock",0);return p;}
 public String toString(){return name;}
}