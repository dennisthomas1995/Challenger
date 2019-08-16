package com.reubro.quiz;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.AppUtils.AppUtils;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;



public class Splash extends Activity {
	ProgressDialog pDialog;
	String emailid;
	String Password;
	
	
	
	private static final int SPLASH_DISPLAY_TIME = 3000;  /* 3 seconds */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		 
		 try {
	      	   PackageInfo info = getPackageManager().getPackageInfo("com.reubro.quiz", PackageManager.GET_SIGNATURES);
	      	   for (Signature signature : info.signatures) {
	      	        MessageDigest md = MessageDigest.getInstance("SHA");
	      	        md.update(signature.toByteArray());
	      	        System.out.println("key hash ===="+Base64.encodeBytes(md.digest()));
//	      			  TextView tvmyName = (TextView)findViewById(R.id.KeyText);
//	      		      tvmyName.setText(Base64.encodeBytes(md.digest()));
	      		  
	      		      
	      	   }
	      	} catch (NameNotFoundException e) {

	      	} catch (NoSuchAlgorithmException e) {

	      	}
	
		AppUtils utils = new AppUtils(Splash.this);
		emailid = utils.getLoginemailid();
		System.out.println("emailid==="+emailid);
		Password = utils.getLoginpassword();
		System.out.println("Password==="+Password);
		   new Handler().postDelayed(new Runnable() {
			public void run() {
   /*
				if(emailid!= null && emailid.length()!=0)
				{
					Intent mainIntent = new Intent(Splash.this, Categorygrid.class);
					Splash.this.startActivity(mainIntent);	
				}
				else
				{
					Intent mainIntent = new Intent(Splash.this, Main.class);
					Splash.this.startActivity(mainIntent);	
				}*/
				
				Intent mainIntent = new Intent(Splash.this, Description.class);
				Splash.this.startActivity(mainIntent);	

				/* Finish splash activity so user cant go back to it. */

				Splash.this.finish();

				/* Apply our splash exit (fade out) and main

                           entry (fade in) animation transitions. */

				overridePendingTransition(R.anim.mainfadein,R.anim.splashfadeout);                   
			}

			private void overridePendingTransition(int mainfadein,
					int splashfadeout) {
				// TODO Auto-generated method stub

			}			
		}, SPLASH_DISPLAY_TIME);
		   
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

			

}
