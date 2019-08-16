package com.Version2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.ChapterList_Adapter;
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
import com.reubro.quiz.EventDetails;
import com.reubro.quiz.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.Request.TAG;

public class EventListV2 extends Activity  implements OnItemClickListener{
	ListView eventlist;
	ProgressDialog pDialog;
	EventListAdapter adapter;
	static String	response;
	static String parserResp="";
	String data;
	private Boolean neterror = false;
	TextView eventhead;
	static String postURL ="https://reubro.tk/quizapp/webService/?";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);
		eventlist 		= (ListView)findViewById(R.id.eventlist);
		eventhead 		= (TextView)findViewById(R.id.txv1);
		eventlist.setOnItemClickListener(this);
		eventhead.setText("Events List");


		pDialog = new ProgressDialog(EventListV2.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();

		//obj1 = new Events();
		
		
		/*if(!(obj1.getStatus() == AsyncTask.Status.RUNNING)){
			System.out.println("reached inside follower ");
			obj1.execute("");
		}*/


		getEventList();
		
	}
	
/*
	private class Events extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(EventListV2.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false); 
			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(EventListV2.this)){
				
					neterror = false;
					
			       response = Webservice.getEvents();

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.getEventDetails(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(EventListV2.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
					pDialog.dismiss();
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}	           
			return parserResp;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			//	System.out.println("am in progress update");
			//    updateProgressBar(values[0]);


		}
		@Override
		protected void onPostExecute(String Userid) {			
			if(parserResp.equals("1")){
				pDialog.dismiss();
			
				
				
				adapter=new EventListAdapter(EventListV2.this,EventListV2.this,Parser.eventid,Parser.eventname,Parser.date);
				eventlist.setAdapter(adapter);
						}
			else if(neterror)
			{
				Toast.makeText(EventListV2.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}
		
			
			else{
				pDialog.dismiss();
				//String error=Parser.getInsError();
				//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();					
				eventlist.setVisibility(View.GONE);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						EventListV2.this);
				alertDialogBuilder
				.setMessage("No Records Found")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}					
				}); 

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
			
			obj1=new Events();
		}
		
		
	}
*/





	public void getEventList(){



		JSONObject obj=new JSONObject();

		try{

			obj.put("argsArray","");
			obj.put("methodName","quizContext");
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

					parserResp=Parser.getEventDetails(response);


					if(parserResp.equals("1")){
						pDialog.dismiss();



						adapter=new EventListAdapter(EventListV2.this,EventListV2.this,Parser.eventid,Parser.eventname,Parser.date);
						eventlist.setAdapter(adapter);
					}
					else if(neterror)
					{
						Toast.makeText(EventListV2.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
						pDialog.dismiss();
					}


					else{
						pDialog.dismiss();
						//String error=Parser.getInsError();
						//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();
						eventlist.setVisibility(View.GONE);
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								EventListV2.this);
						alertDialogBuilder
								.setMessage("No Records Found")
								.setCancelable(false)
								.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										dialog.cancel();
									}
								});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					}

					//obj1=new Events();

				}
				else {
					neterror = true;
					Toast.makeText(EventListV2.this, "No Internet Connectivity", Toast.LENGTH_LONG).show();
					pDialog.dismiss();
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

		RequestQueue requestQueue = Volley.newRequestQueue(EventListV2.this);
		requestQueue.add(jsonObjReq);


	}


	
	@SuppressLint("InlinedApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent n = new Intent(EventListV2.this,EventDetails.class);
	    Bundle b1=new Bundle();
		
		b1.putString("EventName",Parser.eventname[arg2]);
		b1.putString("EventDes",Parser.description[arg2]);
		b1.putString("EventDate",Parser.date[arg2]);
		b1.putString("EventLocation",Parser.location[arg2]);
	    n.putExtras(b1);	
	    n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    startActivity(n);
		
	}


}
