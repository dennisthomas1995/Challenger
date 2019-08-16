package com.Version2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.adapter.EventListAdapter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;
import com.reubro.quiz.Categorygrid;
import com.reubro.quiz.Main;
import com.reubro.quiz.R;
import com.reubro.quiz.Suggestion;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.Request.TAG;

public class SuggestionV2 extends Activity {
	
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
	static String postURL ="https://reubro.tk/quizapp/webService/?";

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

			pDialog=new ProgressDialog(SuggestionV2.this);
			pDialog.setMessage("Sending your comment...");
			pDialog.setCancelable(false);
			pDialog.show(); 
			/*SuggestionThread t=new SuggestionThread();
			t.start();*/
			if(NetworkInformation.isNetworkAvailable(SuggestionV2.this)) {

				addSuggesion();
			}
		}
		else{
			pDialog.dismiss();
			Toast.makeText(SuggestionV2.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();

		}
	}

	public void addSuggesion(){

		JSONObject obj=new JSONObject();
		try{

			JSONObject argsArray = new JSONObject();
			argsArray.put("suggestion_msg",commentp);
			argsArray.put("email",emaill);
			argsArray.put("user_name",namep);
			argsArray.put("userid",utils.getLoginuserid());

			obj.put("argsArray", argsArray);
			obj.put("methodName","categorySuggestion");
			obj.put("className","quizapp");

			data = obj.toString();
			System.out.println("datasuggest:"+data);

		}
		catch(Exception e){

		}

		StringRequest jsonObjReq = new StringRequest(Request.Method.GET,postURL +"Data="+data,new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

				Log.d("Debug", response.toString());

					if (response != null && response.length() > 0) {

						parseres = Parser.parseSuggestion(response);
						System.out.println("parse response===" + parseres);
						pDialog.dismiss();
						if (parseres.equals("1")) {
							String message = Parser.get_SuccessSuggestMsg();
							Toast.makeText(SuggestionV2.this, message, Toast.LENGTH_LONG).show();

							Intent n = new Intent(SuggestionV2.this, CategorygridV2.class);
							Main screen = new Main();
							screen.finish();
							n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(n);
							SuggestionV2.this.finish();

						} else if (parseres.equals("0")) {
							pDialog.dismiss();
							String message = Parser.get_errorSuggestMsg();
							Toast.makeText(SuggestionV2.this, message, Toast.LENGTH_LONG).show();
						} else {
							pDialog.dismiss();
							//	String error=Parser.getLoginError();
							Toast.makeText(SuggestionV2.this, "something went wrong, please try again later!! ", Toast.LENGTH_LONG).show();
						}
					} else {

						pDialog.dismiss();

						Toast.makeText(SuggestionV2.this, "Sorry! No Data found", Toast.LENGTH_LONG).show();

					}

				System.out.println("RESPOSE_NEW++++++ " + response.toString());
				pDialog.dismiss();
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				pDialog.dismiss();
			}
		});

		RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);

		RequestQueue requestQueue = Volley.newRequestQueue(SuggestionV2.this);
		requestQueue.add(jsonObjReq);
	}

	@Override
	protected void onDestroy(){
		System.out.println("reached ondestroy");
		super.onDestroy();	
		System.gc();
		Runtime.getRuntime().gc();
	}
	
}
