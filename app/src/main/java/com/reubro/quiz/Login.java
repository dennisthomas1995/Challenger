package com.reubro.quiz;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.AppUtils.AppUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;
import com.webservice_latest_code.AppController;
import com.webservice_latest_code.Webservice_Latest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.facebook.Request.TAG;

public class Login extends Activity {
	
	EditText email;
	EditText password;
	ProgressDialog pDialog;
	String emaill;
	String pass;
	String response;
	String parseres;
	ProgressDialog myProgressDialog;
	String data;
	String emailid;
	String userid;
	String	firstname;
	String getResponse;
	String jsonResponse;

	static JSONArray argsArray = new JSONArray();
	ArrayList<String> params;
	static JSONObject obj;

	static String postURL ="https://reubro.tk/quizapp/webService/?";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logins);
		email = (EditText) findViewById(R.id.Email);
		password = (EditText) findViewById(R.id.pswd);
	}
//	submit button clicked for submitting the login function

	public void  Submit(View v)
	{
		emaill=email.getText().toString().trim();	
		pass=password.getText().toString().trim();

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
		else if(pass.trim().equals("")){
			flagplen=false;


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

		else if(pass.length()<6){
			flagtlen=false;

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

		else if(flagplen==true&&flagemail==true&&flagtlen==true){

			pDialog=new ProgressDialog(Login.this);
			pDialog.setMessage("Logging in...");
			pDialog.setCancelable(false);
			pDialog.show();
			LoginThread t=new LoginThread();
			t.start();

		}
	}
// thread for calling webservice and checking the network
	private class LoginThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;
		public static final int NO_DATA_AVAILABLE = 3;
		public LoginThread() {
		}
		@Override
		public void run() {
			try {
				if(NetworkInformation.isNetworkAvailable(Login.this)){

					//response = Webservice.Login(emaill,pass);
					response = Login(emaill,pass);

					System.out.println("gerResponse "+ response);

					if(response != null && response.length()>0){					
						parseres=Parser.parseLoginResponse(response);
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
			case LoginThread.SUCCESS:	
				pDialog.dismiss();
				if(parseres.equals("1")){
					userid=Parser.getUserId();
                    firstname=Parser.getfirstname();



					System.out.println("user id in parser class"+userid);			
					Toast.makeText(Login.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
					Intent n = new Intent(Login.this,Categorygrid.class);
					Main screen = new Main();
					screen.finish();
					n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(n);
					Login.this.finish();
					System.out.println("Success");
					
					AppUtils utils=new AppUtils(Login.this);							
					utils.setLoginuserid(userid);
					

					utils.setLoginpassword(pass);
					

					utils.setLoginemailid(emaill);
				
					
					utils.setLoginusername(firstname);
					
					Login.this.finish();
					

				}
				else if(parseres.equals("0")){
					pDialog.dismiss();
					String message=Parser.getLoginMessage();
					Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
				}


				else{
					pDialog.dismiss();
				//	String error=Parser.getLoginError();
					Toast.makeText(Login.this,"emailid or password incorrect !!!! ",Toast.LENGTH_LONG).show();
				}

				break;
			case LoginThread.NO_DATA:
				pDialog.dismiss();

				Toast.makeText(Login.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
				break;
			case LoginThread.NO_DATA_AVAILABLE:
				pDialog.dismiss();


				Toast.makeText(Login.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();

				break;
			case LoginThread.NO_NETWORK:
				pDialog.dismiss();
				Toast.makeText(Login.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
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


	public String Login(final String email, final String pwd){

		try{

			JSONArray argsArray = new JSONArray();
			argsArray.put(email);
			argsArray.put(pwd);

			obj=new JSONObject();
			obj.put("argsArray", argsArray);
			obj.put("methodName","login");
			obj.put("className","quizapp");

			System.out.println("OOOOOOOOOOOOOOOOOOO "+obj.toString());
			jsonResponse = obj.toString();

		}
		catch(Exception e){

		}

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
				postURL+"Data="+jsonResponse,null,new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						getResponse = response.toString();
						Log.d(TAG, response.toString());
						System.out.println("RESPOSE_NEW++++++ "+ getResponse);

					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
			}
		});

		RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
		requestQueue.add(jsonObjReq);

		System.out.println("???????????????? " + jsonObjReq);
		return getResponse;


	}

	
}
