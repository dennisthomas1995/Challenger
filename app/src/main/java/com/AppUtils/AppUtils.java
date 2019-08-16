package com.AppUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppUtils {
	private final static String Loginemailid = "loginemailid";
	
	private final static String Loginpassword = "loginpassword";
	private final static String Loginuserid = "loginuserid";
	private final static String Loginusername = "loginusername";
	
	private final static String SHARED_PREFS = "settings";
	
	
	private SharedPreferences prefs;
	private Editor edit;

	public AppUtils(Context context){
		prefs = context.getSharedPreferences(SHARED_PREFS,
				Context.MODE_PRIVATE);
	}
	
	public void setLoginemailid(String auth){  
		System.out.println("Loginemailid setted is=="+auth);
		edit = prefs.edit();
		edit.putString(Loginemailid,auth);
     	edit.commit();
	}
	public String getLoginemailid(){
		System.out.println("Loginemailid getting==="+Loginemailid);
		return prefs.getString(Loginemailid, "");
	}
	
	public void setLoginpassword(String auth){  
		System.out.println("Loginpassword setted is=="+auth);
		edit = prefs.edit();
		edit.putString(Loginpassword,auth);
     	edit.commit();
	}
	public String getLoginpassword(){
		System.out.println("Loginuserid getting==="+Loginuserid);
		return prefs.getString(Loginpassword, "");
	}
	public void setLoginuserid(String auth){  
	
		edit = prefs.edit();
		edit.putString(Loginuserid,auth);
     	edit.commit();
	}
	public String getLoginuserid(){
	
		return prefs.getString(Loginuserid, "");
	}
	public void setLoginusername(String auth){  
		System.out.println("Loginusername setted is=="+auth);
		edit = prefs.edit();
		edit.putString(Loginusername,auth);
     	edit.commit();
	}
	public String getLoginusername(){
		System.out.println("Loginusername getting==="+Loginusername);
		return prefs.getString(Loginusername, "");
	}
	
}