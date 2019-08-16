package com.reubro.quiz;
import com.AppUtils.AppUtils;
import com.adapter.CategoryGridAdapter;
import com.adapter.NotificationlistAdapter;
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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Categorygrid extends Activity implements OnItemClickListener{

	String Response,LogoutResponse;
	static String[] Category_name;
	static String[] Category_id;
	static String[] Cat_img;
	static String[] pref;
	boolean parseres;
	ProgressDialog pDialog;
	GridView gridview;
	static int Categolen=0;
	Button logoutbtn;
	TextView NotifTv;
	String userid;
	private Boolean neterror = false;
	static String parserResp="";

	static String	response;


	String Data;
	AppUtils utils;
	public static int notifyno=0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorygrid);
		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setOnItemClickListener(Categorygrid.this); 
		NotifTv=(TextView)findViewById(R.id.notno);
		logoutbtn=(Button) findViewById(R.id.logoutbtnn);

		utils=new AppUtils(Categorygrid.this);

		userid=utils.getLoginuserid();

		new Notification1().execute();

		Question.score=0;
		Question.skipcount=0;
		Question.notattendedcount=0;
		Question.wrongc=0;
		Question.rightc=0;

		pDialog = new ProgressDialog(Categorygrid.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();
		Thread parserThread=new parserThread();
		parserThread.start();
	}
	public class Notification1 extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			//			pDialog=new ProgressDialog(Categorygrid.this);
			//			pDialog.setMessage("Loading...");
			//			pDialog.setCancelable(false); 
			//			pDialog.show(); 
			super.onPreExecute(); 

		}   

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(Categorygrid.this)){

					neterror = false;

					response = Webservice.getNotification(userid);

					System.out.println("responsee dep==="+response);
					if(response != null && response.length()>0){						
						parserResp=Parser.ReturnNotification(response);	
					}
				}
				else{
					neterror = true;
					Toast.makeText(Categorygrid.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
					//					pDialog.dismiss();
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

		}
		@Override
		protected void onPostExecute(String Userid) {			
			if(parserResp.equals("1")){
				//				pDialog.dismiss();

				notifyno=Parser.namelen;

				NotifTv.setText(""+notifyno);
			}
			else 
				if(neterror)
				{
					Toast.makeText(Categorygrid.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
					//				pDialog.dismiss();
				}			
		}
	}


	private class parserThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;
		public static final int NO_DATA_AVAILABLE = 3;
		public parserThread() {
		}
		@Override
		public void run() {



			try {
				System.out.println("Network="+NetworkInformation.isNetworkAvailable(Categorygrid.this));
				if(NetworkInformation.isNetworkAvailable(Categorygrid.this)){

					Response = Webservice.Categorygrid();
					if(Response != null && Response.length()>0){
						parseres=Parser.parseCategory(Response);

						if(parseres==false){
							handler.sendEmptyMessage(NO_DATA_AVAILABLE);
						}
						else if(parseres==true){
							Categolen=Parser.getCategoryLength();

							if(Categolen==0){
								handler.sendEmptyMessage(NO_DATA_AVAILABLE);
							}else{
								handler.sendEmptyMessage(SUCCESS);
							}
						}

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
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try
			{

				switch (msg.what) {
				case parserThread.SUCCESS:

					Category_name=Parser.getCatname();
					Category_id=Parser.getCatId();
					Cat_img = Parser.getCatimg();
					pref=Parser.getpref();


					pDialog.dismiss();

					gridview.setAdapter(new CategoryGridAdapter(Categorygrid.this, Category_name, Cat_img,getApplicationContext()));
					break;
				case parserThread.NO_DATA:
					pDialog.dismiss();

					Toast.makeText(Categorygrid.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
					break;
				case parserThread.NO_DATA_AVAILABLE:
					pDialog.dismiss();
					Toast.makeText(Categorygrid.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();
					break;

				case parserThread.NO_NETWORK:
					pDialog.dismiss();
					Toast.makeText(Categorygrid.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
					break;

				default:
					//pDialog.dismiss();
					break;


				}
			}
			catch(Exception e)
			{
				Toast.makeText(Categorygrid.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
			}

		}
	};




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		System.out.println("pos clicked===="+arg2);
		Intent intent=new Intent(Categorygrid.this,Level.class);
		Bundle bundle=new Bundle();
		bundle.putString("CatId",Category_id[arg2]);
		bundle.putInt("Position",arg2);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);

	}

	public void logoutButtonClicked(View view){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder
		.setMessage("Do you want to logout?")
		.setCancelable(false)

		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {

				//				try {
				//					LogoutThread thread = new LogoutThread();
				//					thread.start();
				//
				//				} catch (Exception e) {
				//					e.printStackTrace();
				//				}

				utils.setLoginuserid("");
				utils.setLoginemailid("");
				utils.setLoginusername("");

				Intent n= new Intent(Categorygrid.this,Main.class);
				n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(n);
				Categorygrid.this.finish();

			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});  
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	} 

	public void notificationButtonClicked(View view){ 

		Intent n=new Intent(Categorygrid.this,Notificationlist.class);
		n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(n);

	}
	private class LogoutThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;
		public static final int NO_DATA_AVAILABLE = 3;
		public LogoutThread() {
		}
		@Override
		public void run() {
			try {
				if(NetworkInformation.isNetworkAvailable(Categorygrid.this)){	

					System.out.println("utils uid=="+userid);
					LogoutResponse = Webservice.Logout(userid);
					System.out.println("response of logout in reslistclass=="+Response);


					if(LogoutResponse != null && LogoutResponse.length()>0){	
						Data=Parser.parseLogout(LogoutResponse);
						System.out.println("parse status=="+Data);

						logouthandler.sendEmptyMessage(SUCCESS);	

					}
					else{
						logouthandler.sendEmptyMessage(NO_DATA);
					}
				}


				else{
					logouthandler.sendEmptyMessage(NO_NETWORK);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Handler logouthandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case parserThread.SUCCESS:
				if(Data.equals("0")){
					System.out.println("check==");

					//						System.out.println("sessionkey="+sessionkey);
					utils.setLoginuserid(null);
					utils.setLoginemailid(null);
					utils.setLoginusername(null);
					//Toast.makeText(MainActivity.this," Added to Favorites",Toast.LENGTH_LONG).show();
					//					SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(getBaseContext());                          
					//					SharedPreferences.Editor editor = settings.edit();
					//					editor.clear();
					//					editor.commit();

					Intent n= new Intent(Categorygrid.this,Main.class);
					n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(n);
					Categorygrid.this.finish();

					//					Intent intent = new Intent("CLOSE_ALL");
					//					this.sendBroadcast(intent);
					//System.exit(0);

				}
				else if(Data.equals("1")){

					Toast.makeText(Categorygrid.this,"Logout failed",Toast.LENGTH_LONG).show();
				}
				break;
			case LogoutThread.NO_DATA:
				pDialog.dismiss();

				Toast.makeText(Categorygrid.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
				break;
			case LogoutThread.NO_DATA_AVAILABLE:
				pDialog.dismiss();
				Toast.makeText(Categorygrid.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();
				break;
			case LogoutThread.NO_NETWORK:
				pDialog.dismiss();
				Toast.makeText(Categorygrid.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
				break;
			default:
				//pDialog.dismiss();
				break;
			}
			super.handleMessage(msg);
		}

	};


	public void onBackPressed(){

		this.finish();

	}

	public void eventsClick(View view){
		Intent n=new Intent(Categorygrid.this,EventList.class);
		n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(n);
	}

	public void winnersClick(View view){

		Intent n=new Intent(Categorygrid.this,WinnerList.class);
		n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(n);
	}

	public void suggestionClick(View view){
		Intent n=new Intent(Categorygrid.this,Suggestion.class);
		n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(n);
	}
}