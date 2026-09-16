package com.a2zinvoices.app;
import android.app.*;import android.graphics.Color;import android.view.*;import android.widget.*;
public class BaseActivity extends Activity{
 LinearLayout root; TextView title;
 public void setup(String s){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(16,16,16,16);root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);root.setBackgroundColor(Color.rgb(244,247,251));ScrollView sv=new ScrollView(this);sv.addView(root);setContentView(sv);title=new TextView(this);title.setText(s);title.setTextSize(22);title.setTextColor(Color.rgb(31,78,140));title.setPadding(8,8,8,18);root.addView(title);}
 EditText field(String hint){EditText e=new EditText(this);e.setHint(hint);e.setTextSize(16);e.setSingleLine();e.setGravity(Gravity.RIGHT);root.addView(e,new LinearLayout.LayoutParams(-1,58));return e;}
 Button button(String s,View.OnClickListener l){Button b=new Button(this);b.setText(s);b.setOnClickListener(l);root.addView(b,new LinearLayout.LayoutParams(-1,58));return b;}
 void msg(String s){new AlertDialog.Builder(this).setMessage(s).setPositiveButton("حسنا",null).show();}
}