package com.a2zinvoices.app;
import org.json.JSONObject; import java.util.UUID;
public class InvoiceLine {
 public String productId=UUID.randomUUID().toString(),productName="",unit="قطعة"; public double quantity=1,unitPrice=0;
 public double total(){return Math.round(quantity*unitPrice*100.0)/100.0;}
 public JSONObject toJson(){JSONObject o=new JSONObject();try{o.put("productId",productId);o.put("productName",productName);o.put("unit",unit);o.put("quantity",quantity);o.put("unitPrice",unitPrice);}catch(Exception ignored){}return o;}
 public static InvoiceLine fromJson(JSONObject o){InvoiceLine l=new InvoiceLine();l.productId=o.optString("productId",l.productId);l.productName=o.optString("productName","");l.unit=o.optString("unit","قطعة");l.quantity=o.optDouble("quantity",1);l.unitPrice=o.optDouble("unitPrice",0);return l;}
}