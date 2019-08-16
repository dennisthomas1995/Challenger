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

public class Suggestion extends Activity {
	
	EditText email;
	EditText name;
	EditText comment;

	ProgressDialog pDialog;
	String emaill;
	String namep;
	String commentp;
	String response;
	String parseres;
	
	ProgressDialog myProgressDialog;
	String data;
	String emailid;
	String userid;
	String	firstname;
	AppUtils utils;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestion);
		
		utils=new AppUtils(this);
		email = (EditText) findViewById(R.id.sugemail);
		name = (EditText) findViewById(R.id.sugname);
		comment = (EditText) findViewById(R.id.sugcomment);
		
		name.setText(utils.getLoginusername());
		email.setText(utils.getLoginemailid());
		

	}
//	submit button clicked for submitting the login function
	
	public void  suggestionClicked(View v)
	{
		emaill=email.getText().toString().trim();	
		namep=name.getText().toString().trim();
		commentp=comment.getText().toString().trim();

		boolean flagemail=true;
		boolean flagplen=true;
		boolean flagtlen=true;
		boolean flagelen=true;
		Pattern pattern; 
		Matcher matcher; 
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(emaill);
		if(emaill.trim().equals("")){							

			flagelen=false;
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
		else if(commentp.trim().equals("")){
			flagplen=false;


			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter your comment!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}

		else if(commentp.length()<6){
			flagtlen=false;

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
			.setMessage("Please enter at least 6 characters for comment !!")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}					
			}); 

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}

		else if(flagplen==true&&flagemail==true&&flagtlen==true){

			pDialog=new ProgressDialog(Suggestion.this);
			pDialog.setMessage("Sending your comment...");
			pDialog.setCancelable(false);
			pDialog.show(); 
			SuggestionThread t=new SuggestionThread();
			t.start();

		}
	}
// thread for calling webservice and checking the network
	
	private class SuggestionThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;
		public static final int NO_DATA_AVAILABLE = 3;
		public SuggestionThread() {
		}
		@Override
		public void run() {
			try {
				if(NetworkInformation.isNetworkAvailable(Suggestion.this)){

					response = Webservice.addsuggestions(utils.getLoginuserid(),emaill,namep,commentp);

					if(response != null && response.length()>0){					
						parseres=Parser.parseSuggestion(response);
						System.out.println("parse response==="+parseres);

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
// handling all the function 
	private Handler handler = new Handler() {


		public void handleMessage(Message msg) {


			switch (msg.what) {
			case SuggestionThread.SUCCESS:	
				pDialog.dismiss();
				if(parseres.equals("1")){
					String message=Parser.get_SuccessSuggestMsg();
					Toast.makeText(Suggestion.this,message,Toast.LENGTH_LONG).show();
					
					Intent n = new Intent(Suggestion.this,Categorygrid.class);
					Main screen = new Main();
					screen.finish();
					n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(n);
					Suggestion.this.finish();

				}
				else if(parseres.equals("0")){
					pDialog.dismiss();
					String message=Parser.get_errorSuggestMsg();
					Toast.makeText(Suggestion.this,message,Toast.LENGTH_LONG).show();
				}


				else{
					pDialog.dismiss();
				//	String error=Parser.getLoginError();
					Toast.makeText(Suggestion.this,"something went wrong, please try again later!! ",Toast.LENGTH_LONG).show();
				}

				break;
			case SuggestionThread.NO_DATA:
				pDialog.dismiss();

				Toast.makeText(Suggestion.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
				break;
			case SuggestionThread.NO_DATA_AVAILABLE:
				pDialog.dismiss();


				Toast.makeText(Suggestion.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();

				break;
			case SuggestionThread.NO_NETWORK:
				pDialog.dismiss();
				Toast.makeText(Suggestion.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
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
