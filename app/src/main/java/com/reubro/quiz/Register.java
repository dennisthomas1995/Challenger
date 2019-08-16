package com.reubro.quiz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.AppUtils.AppUtils;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	EditText Name;
	EditText Eemail;
	EditText Passwd;
	ProgressDialog pDialog;
	String response;
	String parseres;
	String name;
	String e_mail;
	String psswd;
	String emailid;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		Name = (EditText) findViewById(R.id.regusernam);

		Eemail = (EditText) findViewById(R.id.email);

		Passwd = (EditText) findViewById(R.id.regpswd);
	}

	public void  submitClicked(View v)
	{
		name=Name.getText().toString().trim();	
		e_mail=Eemail.getText().toString().trim();
		psswd= Passwd.getText().toString().trim();

		boolean flagname=true;
		boolean flagplen=true;
		boolean flageemail=true;
		boolean flagelen=true;
		boolean flagemail=true;
		Pattern pattern; 
		Matcher matcher; 
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(e_mail);

		if(name.trim().equals("")){							
			//					Eusername.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter your Name</font> "));			
			//					Eusername.setTypeface(font);
			flagname=false;

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter your Name!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}

		else if(e_mail.trim().equals("")){							

			flageemail=false;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter your Email!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}

		else if(!(matcher.matches()==true)){
			System.out.println("inside matches true");
			flagemail=false;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter Valid Email!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}

		else if(psswd.trim().equals("")){
			flagplen=false;
			//						Epassword.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter a password</font>"));			
			//						Epassword.setTypeface(font);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter a password!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
		else if(psswd.length()<6){
			flagelen=false;
			//						Epassword.setText("");
			//						Epassword.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter at least 6 characters</font>"));			
			//						Epassword.setTypeface(font);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter at least 6 characters for password !!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}

		else if(flagplen==true&&flagemail==true&&flagname==true&&flagelen==true){

			pDialog=new ProgressDialog(Register.this);
			pDialog.setMessage("Registering...");
			pDialog.setCancelable(false);
			pDialog.show(); 
			RegisterThread t=new RegisterThread();
			t.start();
		}
	}
	
	private class RegisterThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;
		public static final int NO_DATA_AVAILABLE = 3;
		public RegisterThread() {
		}
	
		@Override
		public void run() {
			try {
				if(NetworkInformation.isNetworkAvailable(Register.this)){
                    System.out.println("################################inside");
					response = Webservice.Register(name,e_mail,psswd);
				 
				System.out.println("response"+response);
					if(response != null && response.length()>0){					
					parseres=Parser.parseRegisterResponse(response);
					
							
							handler.sendEmptyMessage(SUCCESS);									
						
					}
					else{
						handler.sendEmptyMessage(NO_DATA);
					}
				} 
				else{
					handler.sendEmptyMessage(NO_NETWORK);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
				
			switch (msg.what) {
			case RegisterThread.SUCCESS:	
  			pDialog.dismiss();
					if(parseres.equals("1")){
						emailid=Parser.getRegId();
							
						 
						 System.out.println("user id in parser class"+	emailid);			
						 Toast.makeText(Register.this,"Registered successfully!",Toast.LENGTH_SHORT).show();						 
//						 Intent n = new Intent(Register.this,Categorygrid.class);
						 Intent n = new Intent(Register.this,OneTimePassword.class);
						  startActivity(n);
						  Register.this.finish();
						  AppUtils utils=new AppUtils(Register.this);							
							utils.setLoginemailid(e_mail);
								
							utils.setLoginpassword(psswd);
								
							utils.setLoginuserid(emailid);
							
							utils.setLoginusername(name);
							
							
							 Register.this.finish();
						
				}
//					else if(parseres.equals("0"))
//					{
//						aa
//					}
					else{
						pDialog.dismiss();
						String error=Parser.getRegMessage();
						Toast.makeText(Register.this,error,Toast.LENGTH_LONG).show();
					}
					
					break;
				case RegisterThread.NO_DATA:
					pDialog.dismiss();

					Toast.makeText(Register.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
					break;
				case RegisterThread.NO_DATA_AVAILABLE:
					pDialog.dismiss();


					Toast.makeText(Register.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();

					break;
				case RegisterThread.NO_NETWORK:
					pDialog.dismiss();
					Toast.makeText(Register.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
					break;
				default:
					pDialog.dismiss();
					break;
				}
				super.handleMessage(msg);
			}

		};
		@Override
		protected void onDestroy(){
			System.out.println("reached ondestroy");
			super.onDestroy();	
			System.gc();
			Runtime.getRuntime().gc();
		}
}
