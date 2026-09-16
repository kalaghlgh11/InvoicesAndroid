package com.a2zinvoices.app;
import android.os.*;import android.app.*;import android.view.*;import android.widget.*;import java.util.*;
public class SupplierActivity extends BaseActivity{
 EditText name,activity,address,phone;LinearLayout list;int editing=-1;
 public void onCreate(Bundle b){super.onCreate(b);setup("الممونون");name=field("اسم الممون *");activity=field("النشاط");address=field("العنوان");phone=field("الهاتف");button("حفظ",v->save());button("مسح الحقول",v->clear());list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);root.addView(list);refresh();}
 void save(){if(name.getText().toString().trim().isEmpty()){msg("الرجاء إدخال اسم الممون.");return;}Supplier s=editing>=0?DataStore.suppliers.get(editing):new Supplier();s.name=name.getText().toString().trim();s.activity=activity.getText().toString().trim();s.address=address.getText().toString().trim();s.phone=phone.getText().toString().trim();if(editing<0)DataStore.suppliers.add(s);DataStore.saveSuppliers();clear();refresh();}
 void clear(){editing=-1;name.setText("");activity.setText("");address.setText("");phone.setText("");}
 void refresh(){list.removeAllViews();for(int i=0;i<DataStore.suppliers.size();i++){final int k=i;Button b=new Button(this);b.setText(DataStore.suppliers.get(i).name+"   ✎");b.setOnClickListener(v->edit(k));list.addView(b);}}
 void edit(int k){editing=k;Supplier s=DataStore.suppliers.get(k);name.setText(s.name);activity.setText(s.activity);address.setText(s.address);phone.setText(s.phone);new AlertDialog.Builder(this).setTitle("إدارة الممون").setMessage(s.name).setPositiveButton("حذف",(d,w)->{DataStore.suppliers.remove(k);DataStore.saveSuppliers();clear();refresh();}).setNegativeButton("تعديل",null).show();}
}