package com.a2zinvoices.app;
import android.os.*;import android.app.*;import android.view.*;import android.widget.*;import java.util.*;
public class InvoiceListActivity extends BaseActivity{
 LinearLayout list;
 public void onCreate(Bundle b){super.onCreate(b);setup("الفواتير المحفوظة");list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);root.addView(list);refresh();}
 void refresh(){list.removeAllViews();if(DataStore.invoices.isEmpty()){list.addView(new TextView(this){{setText("لا توجد فواتير محفوظة.");setTextSize(17);setPadding(8,20,8,20);}});return;}for(int i=0;i<DataStore.invoices.size();i++){final int k=i;Invoice x=DataStore.invoices.get(i);Button b=new Button(this);b.setText("فاتورة "+x.number+" | "+x.customerName+" | "+x.grandTotal+" د.ج");b.setOnClickListener(v->actions(k));list.addView(b);}}
 void actions(int k){Invoice x=DataStore.invoices.get(k);new AlertDialog.Builder(this).setTitle("فاتورة "+x.number).setItems(new String[]{"فتح PDF","حذف"},(d,w)->{if(w==0){try{PdfHelper.open(this,PdfHelper.create(this,x,DataStore.company));}catch(Exception e){msg("تعذر إنشاء PDF: "+e.getMessage());}}else{DataStore.invoices.remove(k);DataStore.saveInvoices();refresh();}}).show();}
}