package com.reubro.quiz;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.Version2.CategorygridV2;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;

public class OneTimePassword extends Activity{

	EditText otp_et;
	Button submit;
	String otp_str;
	AppUtils utils;
	ProgressDialog progressDialog;
	String data=null, status=null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onetimepass);
		
		utils=new AppUtils(this);
		otp_et = (EditText) findViewById(R.id.otp_edit);
		submit = (Button) findViewById(R.id.regsubbtn);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				otp_str=otp_et.getText().toString();
				
				if(otp_str.trim().equals(""))
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OneTimePassword.this);
					alertDialogBuilder
					.setMessage("Please enter your OTP")
					.setCancelable(false)
					.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}					
					}); 
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
				else
				{
					new SendOTP().execute();
				}
				
			}
		});
		
	}
	
	private class SendOTP extends AsyncTask<Void, Void, Void>{


		@Override
		public void onPreExecute() {
			super.onPreExecute();
			progressDialog=new ProgressDialog(OneTimePassword.this);
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		public Void doInBackground(Void... params) {
			System.out.println("Inside do in background" );
			try {

				if (NetworkInformation.isNetworkAvailable(OneTimePassword.this)) {
					
					data = Webservice.sendOTP(utils.getLoginuserid(), otp_str);
					System.out.println("dataa==== from login asyntask" + data);
					if (data != null && data.length() > 0) {
						status = Parser.sendOTP(data);
						//System.out.println(status);

					}
				} else {
					//	Toast.makeText(OneTimePassword.this,"No Internet Connectivity",Toast.LENGTH_LONG).show();
					progressDialog.dismiss();

				}
			} catch (Exception e) {
				//	Toast.makeText(OneTimePassword.this,"No Internet Connectivity",Toast.LENGTH_LONG).show();			
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void args)
		{
			try {

				progressDialog.dismiss();

				if (status.equals("1")) 
				{
					String message=Parser.get_SuccessSuggestMsg();
					Toast.makeText(OneTimePassword.this,message,Toast.LENGTH_LONG).show();
					
					Intent n = new Intent(OneTimePassword.this,CategorygridV2.class);
					Main screen = new Main();
					screen.finish();
					n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(n);
					OneTimePassword.this.finish();
				}
				else if (status.equals("0")) 
				{
					String message=Parser.get_errorSuggestMsg();
					Toast.makeText(OneTimePassword.this,message,Toast.LENGTH_LONG).show();
				}
				else 
				{
					Toast.makeText(OneTimePassword.this,"Something went wrong, please try again later!! ",Toast.LENGTH_LONG).show();

				}
			} 
			catch (Exception e) 
			{
				Toast.makeText(OneTimePassword.this,"Something went wrong, please try again later!! ",Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
