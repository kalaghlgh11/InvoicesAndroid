package com.a2zinvoices.app;
import android.content.Context;
import org.json.*;
import java.util.*;
import java.text.SimpleDateFormat;

public final class DataStore {
 private static android.content.SharedPreferences sp;
 public static ArrayList<Customer> customers=new ArrayList<>(); 
 public static ArrayList<Supplier> suppliers=new ArrayList<>();
 public static ArrayList<Product> products=new ArrayList<>();
 public static ArrayList<Invoice> invoices=new ArrayList<>();
 public static CompanyInfo company=new CompanyInfo();

 public static void init(Context c){sp=c.getSharedPreferences("a2zinvoices_data",Context.MODE_PRIVATE);load();}
 private static JSONArray getArray(String key){try{return new JSONArray(sp.getString(key,"[]"));}catch(Exception e){return new JSONArray();}}
 private static void setArray(String key,JSONArray a){sp.edit().putString(key,a.toString()).apply();}
 public static void load(){
  customers.clear();suppliers.clear();products.clear();invoices.clear();
  JSONArray a=getArray("customers");for(int i=0;i<a.length();i++)customers.add(Customer.fromJson(a.optJSONObject(i)));
  a=getArray("suppliers");for(int i=0;i<a.length();i++)suppliers.add(Supplier.fromJson(a.optJSONObject(i)));
  a=getArray("products");for(int i=0;i<a.length();i++)products.add(Product.fromJson(a.optJSONObject(i)));
  a=getArray("invoices");for(int i=0;i<a.length();i++)invoices.add(Invoice.fromJson(a.optJSONObject(i)));
  try{company=CompanyInfo.fromJson(new JSONObject(sp.getString("company","{}")));}catch(Exception e){company=new CompanyInfo();}
 }
 public static void saveCustomers(){JSONArray a=new JSONArray();for(Customer x:customers)a.put(x.toJson());setArray("customers",a);}
 public static void saveSuppliers(){JSONArray a=new JSONArray();for(Supplier x:suppliers)a.put(x.toJson());setArray("suppliers",a);}
 public static void saveProducts(){JSONArray a=new JSONArray();for(Product x:products)a.put(x.toJson());setArray("products",a);}
 public static void saveInvoices(){JSONArray a=new JSONArray();for(Invoice x:invoices)a.put(x.toJson());setArray("invoices",a);}
 public static void saveCompany(){sp.edit().putString("company",company.toJson().toString()).apply();}
 public static String nextInvoiceNumber(){String year=new SimpleDateFormat("yyyy",Locale.US).format(new Date());int n=0;for(Invoice i:invoices)if(i.number.startsWith(year+"/"))n++;return year+"/"+String.format(Locale.US,"%03d",n+1);}
}