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

import com.AppUtils.AppUtils;
import com.adapter.EventListAdapter;
import com.adapter.NotificationlistAdapter;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;


public class Notificationlist extends Activity implements OnItemClickListener{
	ListView Notificationlist;
	ProgressDialog pDialog;
	NotificationlistAdapter adapter;
	static Notification obj1;	
	static String	response;
	static String parserResp="";
	private Boolean neterror = false;
	TextView eventhead;
	String userid;
	AppUtils utils;
	String notification_id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		Notificationlist 		= (ListView)findViewById(R.id.notificationlist);
		eventhead 		= (TextView)findViewById(R.id.txv1);
		Notificationlist.setOnItemClickListener(this);
		eventhead.setText("Notification List");
		obj1 = new Notification();
		utils=new AppUtils(this);
		userid=utils.getLoginuserid();

		if(!(obj1.getStatus() == AsyncTask.Status.RUNNING)){
			System.out.println("reached inside follower ");
			obj1.execute("");

			Notificationlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {

					notification_id=Parser.notification_id1[position];

					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(Notificationlist.this);
					builder.setTitle("What you want to do");
					builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							
							
							Intent intent = new Intent(Notificationlist.this,Question.class);

							Bundle bundle=new Bundle();
							bundle.putString("CatId",Parser.category_id1[position]);
							bundle.putString("Levl_id",Parser.level1[position]);
							intent.putExtras(bundle);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(intent);

							Toast.makeText(Notificationlist.this,"All the best. Challenge the score",Toast.LENGTH_LONG).show();
							finish();
						}
					});

					builder.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							 
							adapter.notifyDataSetChanged();
							//Toast.makeText(Notificationlist.this,"Challenge request has been deleted",Toast.LENGTH_LONG).show();
							new DeleteNotification().execute();
						}
					});

					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}

			});
		}

	}

	private class Notification extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(Notificationlist.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false); 
			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(Notificationlist.this)){

					neterror = false;

					response = Webservice.getNotification(userid);

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.ReturnNotification(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(Notificationlist.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
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



				//adapter=new NotificationlistAdapter(Notificationlist.this,Parser.notification_id1,Parser.firstname1);
				adapter=new NotificationlistAdapter(Notificationlist.this,Parser.firstname1,Parser.level1,Parser.notification_categoryname);
				Notificationlist.setAdapter(adapter);
			}
			else if(neterror)
			{
				Toast.makeText(Notificationlist.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}


			else{
				pDialog.dismiss();
				//String error=Parser.getInsError();
				//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();					
				Notificationlist.setVisibility(View.GONE);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						Notificationlist.this);
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

			obj1=new Notification();
		}


	}

	@SuppressLint("InlinedApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent n = new Intent(Notificationlist.this,WinnerDetails.class);
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

	private class DeleteNotification extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(Notificationlist.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false); 
			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(Notificationlist.this)){

					neterror = false;

					response = Webservice.deletetNotification(notification_id);

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.ReturnNotification(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(Notificationlist.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
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

				Toast.makeText(Notificationlist.this,"Challenge request has been deleted",Toast.LENGTH_LONG).show();

				Notificationlist.this.finish();
				Intent intent = new Intent(Notificationlist.this,Notificationlist.class);
				startActivity(intent);

			}
			else if(neterror)
			{
				Toast.makeText(Notificationlist.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}


			else{
				pDialog.dismiss();
				//String error=Parser.getInsError();
				//Toast.makeText(Insurance.this,error,Toast.LENGTH_LONG).show();					
				Notificationlist.setVisibility(View.GONE);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						Notificationlist.this);
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

			obj1=new Notification();
		}


	}




}
