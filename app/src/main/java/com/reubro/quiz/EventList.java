package com.reubro.quiz;


import com.adapter.EventListAdapter;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EventList extends Activity  implements OnItemClickListener{
	ListView eventlist;
	ProgressDialog pDialog;
	EventListAdapter adapter;
	static Events obj1;	
	static String	response;
	static String parserResp="";
	private Boolean neterror = false;
	TextView eventhead;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);
		eventlist 		= (ListView)findViewById(R.id.eventlist);
		eventhead 		= (TextView)findViewById(R.id.txv1);
		eventlist.setOnItemClickListener(this);
		eventhead.setText("Events List");
		obj1 = new Events();
		
		
		if(!(obj1.getStatus() == AsyncTask.Status.RUNNING)){
			System.out.println("reached inside follower ");
			obj1.execute("");
		}
		
		
	}
	
	private class Events extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(EventList.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false); 
			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(EventList.this)){
				
					neterror = false;
					
			       response = Webservice.getEvents();

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.getEventDetails(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(EventList.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
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
			
				
				
				adapter=new EventListAdapter(EventList.this,EventList.this,Parser.eventid,Parser.eventname,Parser.date);
				eventlist.setAdapter(adapter);
						}
			else if(neterror)
			{
				Toast.makeText(EventList.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}
		
			
			else{
				pDialog.dismiss();
				//String error=Parser.getInsError();
				//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();					
				eventlist.setVisibility(View.GONE);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						EventList.this);
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
	
	@SuppressLint("InlinedApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent n = new Intent(EventList.this,EventDetails.class);
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
