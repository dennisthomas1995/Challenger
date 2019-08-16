package com.reubro.quiz;

import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Level extends Activity {
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
	boolean parseres;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);         
		setContentView(R.layout.level);
		Bundle bun =getIntent().getExtras();
		CatId=bun.getString("CatId");

		Posit=bun.getInt("Position");


		bttn1 = (ImageView) findViewById(R.id.Btn1);
		bttn2 = (ImageView) findViewById(R.id.Btn2);
		bttn3 = (ImageView) findViewById(R.id.Btn3);

		pDialog = new ProgressDialog(Level.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();
		Thread parserThread=new parserThread();
		parserThread.start();


	}

	public void ButtonEasyClicked(View v)
	{  


		Intent intent = new Intent(Level.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","1");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);

	}

	public void ButtonMediumClicked(View v)
	{
		Intent intent = new Intent(Level.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","2");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);

	}
	public void ButtonHardClicked(View v)
	{
		Intent intent = new Intent(Level.this,Question.class);

		Bundle bundle=new Bundle();
		bundle.putString("CatId",CatId);
		bundle.putString("Levl_id","3");
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);

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
				if(NetworkInformation.isNetworkAvailable(Level.this)){

					Response = Webservice.Level();
					if(Response != null && Response.length()>0){
						parseres=Parser.parseLevel(Response);

						if(parseres==false){
							handler.sendEmptyMessage(NO_DATA_AVAILABLE);
						}
						else if(parseres==true){
							levellen=Parser.getLevelLength();

							if(levellen==0){
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
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case parserThread.SUCCESS:
				levelname=Parser.getLevlname();
				levelid=Parser.getLevlId();
				pDialog.dismiss();
				break;
				
			case parserThread.NO_DATA:
				pDialog.dismiss();

				Toast.makeText(Level.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
				break;
			case parserThread.NO_DATA_AVAILABLE:
				pDialog.dismiss();
				Toast.makeText(Level.this, "Sorry! No Data available",Toast.LENGTH_LONG).show();
				break;

			case parserThread.NO_NETWORK:
				pDialog.dismiss();
				Toast.makeText(Level.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();
				break;

			default:
				//pDialog.dismiss();
				break;

			}

		}
	};

	@Override
	public void onBackPressed()
	{
//		Intent a= new Intent(Level.this,Categorygrid.class);
//		a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		startActivity(a);
		Level.this.finish();


	}
}
