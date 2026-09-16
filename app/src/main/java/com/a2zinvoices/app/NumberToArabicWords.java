package com.a2zinvoices.app;
import java.util.*;
public final class NumberToArabicWords {
 private static final String[] O={"","واحد","اثنان","ثلاثة","أربعة","خمسة","ستة","سبعة","ثمانية","تسعة"};
 private static final String[] OF={"","واحدة","اثنتان","ثلاث","أربع","خمس","ست","سبع","ثمان","تسع"};
 private static final String[] T={"عشرة","أحد عشر","اثنا عشر","ثلاثة عشر","أربعة عشر","خمسة عشر","ستة عشر","سبعة عشر","ثمانية عشر","تسعة عشر"};
 private static final String[] TS={"","","عشرون","ثلاثون","أربعون","خمسون","ستون","سبعون","ثمانون","تسعون"};
 private static final String[] H={"","مائة","مائتان","ثلاثمائة","أربعمائة","خمسمائة","ستمائة","سبعمائة","ثمانمائة","تسعمائة"};
 public static String convert(double amount){amount=Math.abs(Math.round(amount*100)/100.0);long ip=(long)Math.floor(amount);int f=(int)Math.round((amount-ip)*100);String s=(ip==0?"صفر":integer(ip))+" دينار جزائري";if(f>0)s+=" و"+integer(f)+" سنتيم";return s;}
 private static String integer(long n){if(n==0)return"صفر";long[] v={1000000000,1000000,1000};String[] a={"مليار","مليون","ألف"},b={"ملياران","مليونان","ألفان"},c={"مليارات","ملايين","آلاف"};ArrayList<String> p=new ArrayList<>();long r=n;for(int k=0;k<3;k++){long q=r/v[k];r%=v[k];if(q==0)continue;if(q==1)p.add(a[k]);else if(q==2)p.add(b[k]);else if(q<=10)p.add(under(q,true)+" "+c[k]);else p.add(under(q,false)+" "+a[k]);}if(r>0)p.add(under(r,false));return String.join(" و",p);}
 private static String under(long n,boolean fem){String[] one=fem?OF:O;StringBuilder s=new StringBuilder();long h=n/100,r=n%100;if(h>0){s.append(H[(int)h]);if(r>0)s.append(" و");}if(r>=10&&r<=19)s.append(T[(int)r-10]);else{long t=r/10,o=r%10;if(t>0&&o>0)s.append(one[(int)o]).append(" و").append(TS[(int)t]);else if(t>0)s.append(TS[(int)t]);else if(o>0)s.append(one[(int)o]);}return s.toString();}
}