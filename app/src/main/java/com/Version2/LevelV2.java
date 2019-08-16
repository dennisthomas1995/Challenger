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
import android.widget.ImageView;
import android.widget.Toast;

import com.adapter.ChapterList_Adapter;
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
import com.reubro.quiz.Level;
import com.reubro.quiz.Question;
import com.reubro.quiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.Request.TAG;

public class LevelV2 extends Activity {
	private static String Position=null;
	String CatId;
	int Posit;
	String Levl_id;
	ImageView bttn1;
	ImageView bttn2;
	ImageView bttn3;
	ProgressDialog pDialog;
	String Response;
	static int levellen=0;
	String[] levelname;
	String[] levelid;
	String data;
	boolean parseres;
	static String postURL ="https://reubro.tk/quizapp/webService/?";

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);         
		setContentView(R.layout.level);
		Bundle bun =getIntent().getExtras();
		System.out.println("Level 22222222222222222222222222222");
		CatId=bun.getString("CatId");

		Posit=bun.getInt("Position");


		bttn1 = (ImageView) findViewById(R.id.Btn1);
		bttn2 = (ImageView) findViewById(R.id.Btn2);
		bttn3 = (ImageView) findViewById(R.id.Btn3);

		pDialog = new ProgressDialog(LevelV2.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();
		getLevel();
	}

	public void ButtonEasyClicked(View v)
	{

		System.out.println("clicked Easy btn !!!!!!!!!!!!!!!!!!!!!!!!!1");
		Intent intent = new Intent(LevelV2.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","1");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();

	}

	public void ButtonMediumClicked(View v)
	{
		System.out.println("clicked medium btn !!!!!!!!!!!!!!!!!!!!!!!!!1");
		Intent intent = new Intent(LevelV2.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","2");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();

	}
	public void ButtonHardClicked(View v)
	{
		System.out.println("clicked hard btn !!!!!!!!!!!!!!!!!!!!!!!!!1");
		Intent intent = new Intent(LevelV2.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","3");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();

	}

	public void getLevel() {


		JSONObject obj = new JSONObject();
		try {


			obj.put("argsArray", "");
			obj.put("methodName", "level");
			obj.put("className", "quizapp");


		} catch (Exception e) {

		}

		if (NetworkInformation.isNetworkAvailable(LevelV2.this)) {

			StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
					postURL + "Data=" + data, new com.android.volley.Response.Listener<String>() {

				@Override
				public void onResponse(String response) {

					if (response != null && response.length() > 0) {

						try {
							parseres = Parser.parseLevel(Response);
							if (parseres == false) {
								pDialog.dismiss();
								Toast.makeText(LevelV2.this, "Sorry! No Data available", Toast.LENGTH_LONG).show();
							} else if (parseres == true) {
								levellen = Parser.getLevelLength();

								if (levellen == 0) {
									pDialog.dismiss();
									Toast.makeText(LevelV2.this, "Sorry! No Data available", Toast.LENGTH_LONG).show();
								} else {
									levelname = Parser.getLevlname();
									levelid = Parser.getLevlId();
									pDialog.dismiss();
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						pDialog.dismiss();
						Toast.makeText(LevelV2.this, "Sorry! No Data found", Toast.LENGTH_LONG).show();
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

			RequestQueue requestQueue = Volley.newRequestQueue(LevelV2.this);
			requestQueue.add(jsonObjReq);
		}
		else{
			pDialog.dismiss();
			Toast.makeText(LevelV2.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public void onBackPressed()
	{
//		Intent a= new Intent(Level.this,Categorygrid.class);
//		a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		startActivity(a);
		LevelV2.this.finish();

	}
}
