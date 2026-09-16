package com.a2zinvoices.app;
import android.app.*;import android.os.*;import android.content.*;import android.graphics.Color;import android.view.*;import android.widget.*;

public class MainActivity extends Activity{
 LinearLayout root;
 public void onCreate(Bundle b){super.onCreate(b);DataStore.init(this);build();}
 TextView tv(String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(Color.rgb(31,78,140));t.setPadding(12,18,12,18);t.setGravity(Gravity.RIGHT);return t;}
 Button btn(String s,Class<?> c){Button b=new Button(this);b.setText(s);b.setOnClickListener(v->startActivity(new Intent(this,c)));root.addView(b,new LinearLayout.LayoutParams(-1,60));return b;}
 void build(){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(24,24,24,24);root.setBackgroundColor(Color.rgb(244,247,251));root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);setContentView(root);root.addView(tv("A2ZInvoices",26));root.addView(tv("إدارة وتحرير الفواتير",16));btn("🧾 تحرير فاتورة",InvoiceActivity.class);btn("👤 الزبائن",CustomerActivity.class);btn("📦 السلع والمخزون",ProductActivity.class);btn("🚚 الممونون",SupplierActivity.class);btn("⚙ معلومات الشركة",SettingsActivity.class);btn("📋 الفواتير المحفوظة",InvoiceListActivity.class);}
}