package com.a2zinvoices.app;
import android.content.*;import android.graphics.*;import android.graphics.pdf.PdfDocument;import android.net.Uri;import androidx.core.content.FileProvider;import java.io.*;
public final class PdfHelper {
 private static final int W=595,H=842,M=28;
 public static File create(Context c,Invoice inv,CompanyInfo co)throws IOException{
  PdfDocument d=new PdfDocument();PdfDocument.PageInfo pi=new PdfDocument.PageInfo.Builder(W,H,1).create();PdfDocument.Page p=d.startPage(pi);Canvas x=p.getCanvas();x.drawColor(Color.WHITE);
  Paint normal=paint(10,Color.BLACK,false),bold=paint(10,Color.BLACK,true),title=paint(18,Color.rgb(31,78,140),true);
  x.setTextAlign(Paint.Align.RIGHT); int y=M+12;
  String cn=co.name.isEmpty()?"اسم الشركة":co.name; x.drawText(cn,W-M,y,title); y+=16;
  String[] company={co.activity,co.address,"رقم السجل التجاري: "+co.commercialRegister,"الرقم الجبائي: "+co.taxId,"رقم المادة: "+co.articleNumber,"الهاتف: "+co.phone};
  for(String s:company)if(!s.endsWith(": ")&&!s.isEmpty()){x.drawText(s,W-M,y,normal);y+=14;}
  x.setTextAlign(Paint.Align.LEFT);x.drawText("فاتورة",M+120,M+18,title);x.drawText("رقم الفاتورة: "+inv.number,M+120,M+38,bold);x.drawText("التاريخ: "+inv.date,M+120,M+54,normal);
  y=Math.max(y,M+72);x.drawLine(M,y,W-M,y,normal);y+=22;
  x.setTextAlign(Paint.Align.RIGHT);x.drawText("المطلوب من: "+inv.customerName,W-M,y,bold);y+=15;
  if(!inv.customerActivity.isEmpty()){x.drawText("النشاط: "+inv.customerActivity,W-M,y,normal);y+=14;}
  if(!inv.customerAddress.isEmpty()){x.drawText("العنوان: "+inv.customerAddress,W-M,y,normal);y+=14;}
  if(!inv.customerCommercialRegister.isEmpty()){x.drawText("رقم السجل التجاري: "+inv.customerCommercialRegister,W-M,y,normal);y+=14;}
  if(!inv.customerTaxId.isEmpty()){x.drawText("الرقم الجبائي: "+inv.customerTaxId,W-M,y,normal);y+=14;}
  y+=8;
  y=drawTable(x,inv,y,normal,bold);
  y+=12; x.setTextAlign(Paint.Align.RIGHT);
  x.drawText("المجموع بدون رسوم: "+fmt(inv.subTotal())+" د.ج",W-M,y,normal);y+=15;
  x.drawText("القيمة المضافة % "+fmt(inv.vatRate())+": "+fmt(inv.vatAmount())+" د.ج",W-M,y,normal);y+=15;
  if(inv.fiscalStamp>0){x.drawText("الطابع الجبائي: "+fmt(inv.fiscalStamp)+" د.ج",W-M,y,normal);y+=15;}
  x.drawText("المجموع الكلي: "+fmt(inv.grandTotal())+" د.ج",W-M,y,bold);y+=25;
  x.drawText("أوقفت هذه الفاتورة عند مبلغ قدره:",W-M,y,bold);y+=16;x.drawText(NumberToArabicWords.convert(inv.grandTotal()),W-M,y,normal);y+=25;
  x.drawText("كيفية التسديد: "+inv.paymentMethod,W-M,y,bold);y+=35;
  x.drawText("الزبون (إمضاء):",W/2+100,y,bold);x.drawText("الممون (إمضاء وختم):",M+140,y,bold);
  d.finishPage(p);File dir=new File(c.getFilesDir(),"pdf");if(!dir.exists())dir.mkdirs();File f=new File(dir,"invoice_"+safe(inv.number)+".pdf");try(FileOutputStream out=new FileOutputStream(f)){d.writeTo(out);}d.close();return f;
 }
 private static int drawTable(Canvas x,Invoice inv,int top,Paint n,Paint b){
  int left=M,right=W-M,row=26;int[] widths={42,155,55,55,95,135};
  // RTL order is fixed explicitly: رقم (right) -> التعيين -> الوحدة -> الكمية -> سعر الوحدة -> المجموع (left).
  int[] rx=new int[6];rx[0]=right;for(int i=1;i<6;i++)rx[i]=rx[i-1]-widths[i-1];
  int count=Math.max(4,inv.lines.size()),bottom=top+row*(count+1);
  Paint fill=new Paint();fill.setColor(Color.rgb(31,78,140));x.drawRect(left,top,right,top+row,fill);
  Paint border=paint(1,Color.BLACK,false);border.setStyle(Paint.Style.STROKE);border.setStrokeWidth(0.7f);
  String[] h={"رقم","التعيين","الوحدة","الكمية","سعر الوحدة (د.ج)","المجموع (د.ج)"};
  for(int i=0;i<6;i++){x.drawRect(rx[i]-widths[i],top,rx[i],top+row,border);cellText(x,h[i],rx[i]-widths[i]/2,top+17,b,Paint.Align.CENTER);}
  for(int r=0;r<count;r++){
   int yy=top+row*(r+1);InvoiceLine l=r<inv.lines.size()?inv.lines.get(r):null;
   String[] vals=l==null?new String[]{"","","","","",""}:new String[]{String.valueOf(r+1),l.productName,l.unit,fmt(l.quantity),fmt(l.unitPrice),fmt(l.total())};
   for(int i=0;i<6;i++){x.drawRect(rx[i]-widths[i],yy,rx[i],yy+row,border);cellText(x,vals[i],rx[i]-widths[i]/2,yy+17,n,Paint.Align.CENTER);}
  }
  return bottom;
 }
 private static void cellText(Canvas x,String s,float xx,float yy,Paint p,Paint.Align a){p.setTextAlign(a);x.drawText(s,xx,yy,p);}
 private static Paint paint(float size,int color,boolean bold){Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);p.setTextSize(size);p.setColor(color);p.setTypeface(Typeface.create("sans",bold?Typeface.BOLD:Typeface.NORMAL));return p;}
 private static String fmt(double v){return String.format(java.util.Locale.US,"%.2f",v);}
 private static String safe(String s){return s.replaceAll("[^a-zA-Z0-9_-]","_");}
 public static void open(Context c,File f){Uri u=FileProvider.getUriForFile(c,c.getPackageName()+".fileprovider",f);Intent i=new Intent(Intent.ACTION_VIEW);i.setDataAndType(u,"application/pdf");i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);try{c.startActivity(i);}catch(Exception e){Intent sh=new Intent(Intent.ACTION_SEND);sh.setType("application/pdf");sh.putExtra(Intent.EXTRA_STREAM,u);sh.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);c.startActivity(Intent.createChooser(sh,"فتح/مشاركة الفاتورة"));}}
}