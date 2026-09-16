package com.a2zinvoices.app;
import android.content.*;import
android.graphics.*;import
android.graphics.pdf.PdfDocument;import
android.net.Uri;import
androidx.core.content.FileProvider;import
java.io.*;
public final class PdfHelper {
private static final int W=595,H=842,M=28;
public static File create(Context
c,Invoice inv,CompanyInfo co)throws
IOException{
PdfDocument d=new
PdfDocument();PdfDocument.PageInfo pi=new
PdfDocument.PageInfo.Builder(W,H,1).create(
);PdfDocument.Page p=d.startPage(pi);Canvas
x=p.getCanvas();x.drawColor(Color.WHITE);
Paint
normal=paint(10,Color.BLACK,false),bold=pai
nt(10,Color.BLACK,true),title=paint(18,Colo
r.rgb(31,78,140),true);
int y=M+12;
title.setTextAlign(Paint.Align.RIGHT);
normal.setTextAlign(Paint.Align.RIGHT);
String cn=co.name.isEmpty()?" اسم
الشركة ":co.name; x.drawText(cn,W-M,y,title);
y+=16;
String[] company=
{co.activity,co.address," :رقم السجل التجاري
"+co.commercialRegister," :الرقم الجبائي
"+co.taxId," :رقم المادة
"+co.articleNumber," الهاتف : "+co.phone};
for(String s:company)if(!s.endsWith(":
")&&!s.isEmpty()){x.drawText(s,WM,
y,normal);y+=14;}
title.setTextAlign(Paint.Align.LEFT);bold.s
etTextAlign(Paint.Align.LEFT);normal.setTex
tAlign(Paint.Align.LEFT);x.drawText(" ,"فاتورة
M+120,M+18,title);x.drawText(" :رقم الفاتورة
"+inv.number,M+120,M+38,bold);x.drawText(" لا
يختار : "+inv.date,M+120,M+54,normal);
y=Math.max(y,M+72);x.drawLine(M,y,WM,
y,normal);y+=22;
bold.setTextAlign(Paint.Align.RIGHT);normal
.setTextAlign(Paint.Align.RIGHT);x.drawText
المطلوب من") : "+inv.customerName,WM,
y,bold);y+=15;
if(!inv.customerActivity.isEmpty())
{x.drawText(" :النشاط
"+inv.customerActivity,WM,
y,normal);y+=14;}
if(!inv.customerAddress.isEmpty())
{x.drawText(" العنوان : "+inv.customerAddress,WM,
y,normal);y+=14;}
if(!inv.customerCommercialRegister.isEmpty(
)){x.drawText(" :رقم السجل التجاري
"+inv.customerCommercialRegister,WM,
y,normal);y+=14;}
if(!inv.customerTaxId.isEmpty())
{x.drawText(" :الرقم الجبائي
"+inv.customerTaxId,W-M,y,normal);y+=14;}
y+=8;
y=drawTable(x,inv,y,normal,bold);
y+=12;
normal.setTextAlign(Paint.Align.RIGHT);
bold.setTextAlign(Paint.Align.RIGHT);
x.drawText(" :المجموع بدون رسوم
"+fmt(inv.subTotal())+" د.ج ",WM,
y,normal);y+=15;
x.drawText(" % القيمة المضافة
"+fmt(inv.vatRate)+":
"+fmt(inv.vatAmount())+" د.ج ",WM,
y,normal);y+=15;
if(inv.fiscalStamp>0){x.drawText(" الطابع
الجبائي : "+fmt(inv.fiscalStamp)+" د.ج ",WM,
y,normal);y+=15;}
x.drawText(" :المجموع الكلي
"+fmt(inv.grandTotal())+" د.ج ",WM,
y,bold);y+=25;
x.drawText(" أوقفت هذه الفاتورة عند مبلغ قدره :",WM,
y,bold);y+=16;x.drawText(NumberToArabicWo
rds.convert(inv.grandTotal()),WM,
y,normal);y+=25;
x.drawText(" :كيفية التسديد
"+inv.paymentMethod,W-M,y,bold);y+=35;
x.drawText(" الزبون
إمضاء( (:",W/2+100,y,bold);x.drawText(" الممون
إمضاء وختم( (:",M+140,y,bold);
d.finishPage(p);File dir=new
File(c.getFilesDir(),"pdf");if(!dir.exists(
))dir.mkdirs();File f=new
File(dir,"invoice_"+safe(inv.number)+".pdf"
);try(FileOutputStream out=new
FileOutputStream(f))
{d.writeTo(out);}d.close();return f;
}
private static int drawTable(Canvas
x,Invoice inv,int top,Paint n,Paint b){
int left=M,right=W-M,row=26;int[] widths=
{42,155,55,55,95,135};
// RTL order is fixed explicitly: رقم
(right) -> التعيين -< الوحدة -< الكمية -< سعر الوحدة
المجموع >- (left).
int[] rx=new int[6];rx[0]=right;for(int
i=1;i<6;i++)rx[i]=rx[i-1]-widths[i-1];
int
count=Math.max(4,inv.lines.size()),bottom=t
op+row*(count+1);
Paint fill=new
Paint();fill.setColor(Color.rgb(31,78,140))
;x.drawRect(left,top,right,top+row,fill);
Paint
border=paint(1,Color.BLACK,false);border.se
tStyle(Paint.Style.STROKE);border.setStroke
Width(0.7f);
String[] h=
رقم","التعيين","الوحدة","الكمية","سعر الوحدة"}
;{")د.ج(","المجموع )د.ج(
for(int i=0;i<6;i++){x.drawRect(rx[i]-
widths[i],top,rx[i],top+row,border);cellTex
t(x,h[i],rx[i]-
widths[i]/2,top+17,b,Paint.Align.CENTER);}
for(int r=0;r<count;r++){
int yy=top+row*(r+1);InvoiceLine
l=r<inv.lines.size()?inv.lines.get(r):null;
String[] vals=l==null?new String[]
{"","","","","",""}:new String[]
{String.valueOf(r+1),l.productName,l.unit,f
mt(l.quantity),fmt(l.unitPrice),fmt(l.total
())};
for(int i=0;i<6;i++){x.drawRect(rx[i]-
widths[i],yy,rx[i],yy+row,border);cellText(
x,vals[i],rx[i]-
widths[i]/2,yy+17,n,Paint.Align.CENTER);}
}
return bottom;
}
private static void cellText(Canvas
x,String s,float xx,float yy,Paint
p,Paint.Align a)
{p.setTextAlign(a);x.drawText(s,xx,yy,p);}
private static Paint paint(float size,int
color,boolean bold){Paint p=new
Paint(Paint.ANTI_ALIAS_FLAG);p.setTextSize(
size);p.setColor(color);p.setTypeface(Typef
ace.create("sans",bold?
Typeface.BOLD:Typeface.NORMAL));return p;}
private static String fmt(double v){return
String.format(java.util.Locale.US,"%.2f",v)
;}
private static String safe(String s)
{return s.replaceAll("[^a-zA-Z0-
9_-]","_");}
public static void open(Context c,File f)
{Uri
u=FileProvider.getUriForFile(c,c.getPackage
Name()+".fileprovider",f);Intent i=new
Intent(Intent.ACTION_VIEW);i.setDataAndType
(u,"application/pdf");i.addFlags(Intent.FLA
G_GRANT_READ_URI_PERMISSION);try{c.startAct
ivity(i);}catch(Exception e){Intent sh=new
Intent(Intent.ACTION_SEND);sh.setType("appl
ication/pdf");sh.putExtra(Intent.EXTRA_STRE
AM,u);sh.addFlags(Intent.FLAG_GRANT_READ_UR
I_PERMISSION);c.startActivity(Intent.create
Chooser(sh," {{;(("فتح/مشاركة الفاتورة
}
