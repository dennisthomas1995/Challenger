package com.reubro.quiz;

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
import com.adapter.EventListAdapter;
import com.adapter.WinnerListAdapter;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;


public class WinnerList extends Activity implements OnItemClickListener{
	ListView winnerlist;
	ProgressDialog pDialog;
	WinnerListAdapter adapter;
	static Winners obj1;	
	static String	response;
	static String parserResp="";
	private Boolean neterror = false;
	TextView eventhead;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);
		winnerlist 		= (ListView)findViewById(R.id.eventlist);
		eventhead 		= (TextView)findViewById(R.id.txv1);
		winnerlist.setOnItemClickListener(this);
		eventhead.setText("Winners List");
		obj1 = new Winners();
		
		
		if(!(obj1.getStatus() == AsyncTask.Status.RUNNING)){
			System.out.println("reached inside follower ");
			obj1.execute("");
		}
		
		
	}
	
	private class Winners extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(WinnerList.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false); 
			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(WinnerList.this)){
				
					neterror = false;
					
			       response = Webservice.getWinners();

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.getWinnerDetails(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(WinnerList.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
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
			
				
				
				adapter=new WinnerListAdapter(WinnerList.this,Parser.winnername,Parser.winnerimage,getApplicationContext());
				winnerlist.setAdapter(adapter);
						}
			else if(neterror)
			{
				Toast.makeText(WinnerList.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}
		
			
			else{
				pDialog.dismiss();
				//String error=Parser.getInsError();
				//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();					
				winnerlist.setVisibility(View.GONE);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						WinnerList.this);
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
			
			obj1=new Winners();
		}
		
		
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent n = new Intent(WinnerList.this,WinnerDetails.class);
		n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    Bundle b1=new Bundle();
		
		b1.putString("WinnerName",Parser.winnername[arg2]);
		b1.putString("WinnerImage",Parser.winnerimage[arg2]);
		b1.putString("WinnerDes",Parser.winnerdes[arg2]);
		b1.putString("WinnerContest",Parser.titlecontest[arg2]);
		b1.putString("ContestDes",Parser.titledes[arg2]);	
		b1.putString("WinnerContestdate",Parser.winnerdate[arg2]);
	    n.putExtras(b1);	     
	    startActivity(n);
		
	}


}
