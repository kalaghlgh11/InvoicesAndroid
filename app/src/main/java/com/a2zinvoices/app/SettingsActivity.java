package com.a2zinvoices.app;
import android.os.*;import android.app.*;import android.view.*;import android.widget.*;import java.util.*;
public class SettingsActivity extends BaseActivity{
 EditText n,a,ad,cr,tax,art,ph;
 public void onCreate(Bundle b){super.onCreate(b);setup("معلومات الشركة");n=field("اسم الشركة / المسير");a=field("النشاط");ad=field("العنوان");cr=field("رقم السجل التجاري");tax=field("الرقم الجبائي");art=field("رقم المادة");ph=field("الهاتف");CompanyInfo c=DataStore.company;n.setText(c.name);a.setText(c.activity);ad.setText(c.address);cr.setText(c.commercialRegister);tax.setText(c.taxId);art.setText(c.articleNumber);ph.setText(c.phone);button("حفظ معلومات الشركة",v->save());}
 void save(){CompanyInfo c=DataStore.company;c.name=n.getText().toString().trim();c.activity=a.getText().toString().trim();c.address=ad.getText().toString().trim();c.commercialRegister=cr.getText().toString().trim();c.taxId=tax.getText().toString().trim();c.articleNumber=art.getText().toString().trim();c.phone=ph.getText().toString().trim();DataStore.saveCompany();msg("تم حفظ معلومات الشركة بنجاح.");}
}