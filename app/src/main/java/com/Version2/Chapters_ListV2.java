package com.Version2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
import com.reubro.quiz.Chapter;
import com.reubro.quiz.Chapters_List;
import com.reubro.quiz.R;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.facebook.Request.TAG;

public class Chapters_ListV2 extends Activity{
	ListView list;
	ProgressDialog progressDialog;
	String data,status;
	String chosen_catid;
	String[] chapter_ids,chapter_names;
	static String postURL ="https://reubro.tk/quizapp/webService/?";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chapters_list);

		list=(ListView)findViewById(R.id.chapter_list);
		
		Bundle bun =getIntent().getExtras();
		chosen_catid=bun.getString("CatId");

		if(NetworkInformation.isNetworkAvailable(Chapters_ListV2.this))
		{
			getChapter();
		}
		else
		{
			Toast.makeText(Chapters_ListV2.this,"Failed to connect to server",Toast.LENGTH_LONG).show();
		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent n = new Intent(Chapters_ListV2.this,Chapter.class);
				Bundle b1=new Bundle();

				b1.putString("ChapterID",chapter_ids[arg2]);
				b1.putString("CategoryID",chosen_catid);
				n.putExtras(b1);	
				n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(n);
			}

		});

	}


	public void getChapter(){


		JSONObject obj=new JSONObject();
		try{


			JSONArray argsArray = new JSONArray();
			argsArray.put(chosen_catid);

			obj.put("argsArray", argsArray);
			obj.put("methodName","login");
			obj.put("className","quizapp");
			data = obj.toString();

		}
		catch(Exception e){

		}


		StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
				postURL +"Data="+data,new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

				Log.d("Debug", response.toString());

				if (response != null && response.length() > 0) {

					JSONObject dataObject	= null;
					try {

						dataObject = new JSONObject(response);
						System.out.println("Reached inside parser category");
						status = dataObject.optString("status").toString();


						if(status.equals("1")){
							ChapterList_Adapter adapter = new ChapterList_Adapter(Chapters_ListV2.this,chapter_ids,chapter_names);
							list.setAdapter(adapter);

							if ((progressDialog != null) && progressDialog.isShowing())
								progressDialog.dismiss();

						}
						else if (status.equals("0")) {

							if ((progressDialog != null) && progressDialog.isShowing())
								progressDialog.dismiss();

							//	Toast.makeText(Chapters_List.this,R.string.no_records_found,Toast.LENGTH_LONG).show();

							final AlertDialog.Builder builder = new AlertDialog.Builder(Chapters_ListV2.this);
							builder.setMessage("No records found");
							builder.setPositiveButton("OK",
									new DialogInterface.OnClickListener()
									{
										@Override
										public void onClick(DialogInterface dialog,
															int which)
										{
											onBackPressed();
											dialog.cancel();
										}
									});
							builder.setCancelable(false);
							builder.show();

						} else {
							if ((progressDialog != null) && progressDialog.isShowing())
								progressDialog.dismiss();

							Toast.makeText(Chapters_ListV2.this,"Failed to connect to server",Toast.LENGTH_LONG).show();

						}



					} catch (JSONException e) {

						if ((progressDialog != null) && progressDialog.isShowing())
							progressDialog.dismiss();
							Toast.makeText(Chapters_ListV2.this,"Failed to connect to server",Toast.LENGTH_LONG).show();
							e.printStackTrace();
					}

				}

				System.out.println("RESPOSE_NEW++++++ " + response.toString());
				progressDialog.dismiss();
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				progressDialog.dismiss();
			}
		});

		RetryPolicy policy = new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);

		RequestQueue requestQueue = Volley.newRequestQueue(Chapters_ListV2.this);
		requestQueue.add(jsonObjReq);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
