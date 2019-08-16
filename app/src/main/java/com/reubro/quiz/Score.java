package com.reubro.quiz;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.Version2.CategorygridV2;
import com.adapter.ScorelistAdapter;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseDialogListener;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.LoginButton;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;
import com.facebook.android.SessionStore;
import com.facebook.android.Util;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Score extends Activity  {


	public static final String APP_ID ="1404194593172383";
	private LoginButton mLoginButton;
	private Handler mHandler;

	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;
	private static final String[] PERMS = new String[] {"email","read_stream","publish_actions"};
	static int i=0;
	String id;
	String token;
	ProgressDialog	pDialog;
	int SUCCESS;
	ScorelistAdapter adapter;

	String parseres;

	String CatId;
	String level;
	int scoree;
	private AdView adView;
	String Response;
	String scoremess;
	String scorestatus;
	ListView listview;

	String[] Nname;
	String[] fname;
	public static String[] Usid;
	String[] nscore;
	String[] CCategory;
	String[] d_added;
	String[] playId;
	public static ArrayList<String> playid_arrayList=new ArrayList<String>();

	TextView yscore;
	TextView sscbtn;
	TextView Stxt;
	TextView Uname;
	String data=null, status=null;
	ProgressDialog progressDialog;
	AppUtils utils;

	String uid;
	String userName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);

		listview=(ListView)findViewById(R.id.listvw);
		yscore=(TextView)findViewById(R.id.txtvw3);
		sscbtn=(TextView)findViewById(R.id.sscbttn2);
		Stxt=(TextView)findViewById(R.id.txtvw1);
		Uname=(TextView)findViewById(R.id.txtvw2);

		utils=new AppUtils(this);

		 uid=utils.getLoginuserid();

		 playid_arrayList.clear();

		Bundle bun =getIntent().getExtras();
		CatId=bun.getString("CatId");

		level=bun.getString("Levl_id");

		scoree=bun.getInt("score");
		userName=bun.getString("name");


		yscore.setText("You scored "+Integer.toString(scoree));
		sscbtn.setText(Integer.toString(scoree));

		if(scoree==0)
		{
			Stxt.setText("Try Hard Again");
		}
		else if(scoree<=60 && scoree>0)
		{
			Stxt.setText("Try Again");
		}
		else if(scoree>=60)
		{
			Stxt.setText("Well done!!!");
		}


		AppUtils utils=new AppUtils(Score.this);

		String uname=utils.getLoginusername();
		Uname.setText(uname);

		String b ="a15253c68ab3092";
		// Create the adView
		adView = new AdView(this, AdSize.BANNER, b);

		// Lookup your LinearLayout assuming it's been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout)findViewById(R.id.scoreLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

		pDialog = new ProgressDialog(Score.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();
		Thread parserThread=new parserThread();
		parserThread.start();


		if (APP_ID == null) {
			Util.showAlert(this, "Warning", "Facebook Applicaton ID must be " +
					"specified before running this example: see Example.java");
		}
		mHandler = new Handler();
		//System.out.println("reached facebook class");
		mLoginButton= (LoginButton)findViewById(com.reubro.quiz.R.id.fshare1);
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		SessionStore.restore(mFacebook, this);
		SessionEvents.addAuthListener(new SampleAuthListener());
		SessionEvents.addLogoutListener(new SampleLogoutListener());

		mLoginButton.setOnTouchListener(new View.OnTouchListener(){
			//private String[] PERMS;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mLoginButton.init(Score.this, mFacebook,PERMS);
				boolean sessionValid = mFacebook.isSessionValid();
				System.out.println( "sessionValid: " + sessionValid);
				if(mFacebook.isSessionValid()){
					i=1;

					mFacebook.dialog(Score.this, "feed", param(),
							new SampleDialogListener());

				}
				// TODO Auto-generated method stub
				return false;
			}


		});


	}

	public void  PiechartM(View v)
	{
		Intent intent= new Intent(Score.this, PieChart1.class);
		startActivity(intent);

	}
	public void  sendRequest(View v)
	{
		if(playid_arrayList.size()>0)
		{
			for(int i=0;i<playid_arrayList.size();i++)
			{
				if(uid.equals(playid_arrayList.get(i)))
				{
					Toast.makeText(Score.this,"You cant send challenge request to yourself. Select other players",Toast.LENGTH_LONG).show();
				}
				else{
					new SendRequest().execute();
				}
			}

		}
		else
		{
			Toast.makeText(Score.this, "Select atleast one player", Toast.LENGTH_SHORT).show();
		}

	}


	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	public class SampleAuthListener implements AuthListener {

		public void onAuthSucceed() {
			//     mText.setText("You have logged in!");
			// mRequestButton.setVisibility(View.VISIBLE);
			// mUploadButton.setVisibility(View.VISIBLE);
			//mPostButton.setVisibility(View.VISIBLE);
			//System.out.println("FbAction Insde OATH============="+response);
			//	   if(response.equals("share")){

			//					           	 mFacebook.dialog(Dispensary.this, "feed", param(),
			//				                        new SampleDialogListener());
			//   }
			//	           else if(response.equals("")){
			//mAsyncRunner.request("me", new SampleRequestListener());
			//				pDialog  = ProgressDialog.show(Dispensary.this, "", "");
			//				mAsyncRunner.request("me", new SampleRequestListener());
			//
			//				mAsyncRunner.request(((name == null) ? "me" : name) + "/feed", new WallPostRequestListener());

			//           }


			mFacebook.dialog(Score.this, "feed", param(),
					new SampleDialogListener());

		}


		public void onAuthFail(String error) {
			//  mText.setText("Login Failed: " + error);
		}

	}

	public class SampleLogoutListener implements LogoutListener {
		public void onLogoutBegin() {
			// mText.setText("Logging out...");
		}

		public void onLogoutFinish() {
			//  mText.setText("You have logged out! ");
			// mRequestButton.setVisibility(View.INVISIBLE);
			//  mUploadButton.setVisibility(View.INVISIBLE);
			//  mPostButton.setVisibility(View.INVISIBLE);
		}
	}

	public class SampleRequestListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {
			try {
				// process the response here: executed in background thread
				Log.d("Facebook-ExampleShare", "Response: " + response.toString());
				JSONObject json = Util.parseJson(response);
				final String name = json.getString("name");
				id = json.getString("id");
				System.out.println("id ="+id);

				//                final String fname=json.getString("first_name");
				//                System.out.println("fname ="+fname);
				//                final String lname=json.getString("last_name");
				//                System.out.println("lname ="+lname);
				//                final String email = json.getString("email");
				//                System.out.println("email ="+email);
				token = mFacebook.getAccessToken();
				AppUtils utils=new AppUtils(Score.this);
				pDialog.dismiss();
				//				utils.setFBAccessToken(token);
				//				utils.setFBId(id);

				//final String custid = json.getString("id");
				// then post the processed result back to the UI thread
				// if we do not do this, an runtime exception will be generated
				// e.g. "CalledFromWrongThreadException: Only the original
				// thread that created a view hierarchy can touch its views."
				Score.this.runOnUiThread(new Runnable() {
					public void run() {

						// mText.setText("Logged in as " + name + "!");
					}
				});
				//System.out.println("custid====="+custid);
				//Bundle bundle = new Bundle();
				///bundle.putString("CustId", custid);
				//Intent i = new Intent(ExampleShare.this, Loginfacebook.class);
				//i.putExtras(bundle);
				//startActivity(i);

			} catch (JSONException e) {
				Log.w("Facebook-ExampleShare", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Facebook-ExampleShare", "Facebook Error: " + e.getMessage());
			}
		}
	}




	public class SampleUploadListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {
			try {
				// process the response here: (executed in background thread)
				// Log.d("Facebook-ExampleShare", "Response: " + response.toString());
				JSONObject json = Util.parseJson(response);
				final String src = json.getString("src");

				// then post the processed result back to the UI thread
				// if we do not do this, an runtime exception will be generated
				// e.g. "CalledFromWrongThreadException: Only the original
				// thread that created a view hierarchy can touch its views."
				Score.this.runOnUiThread(new Runnable() {
					public void run() {
						// mText.setText("Hello there, photo has been uploaded at \n" + src);


					}
				});
			} catch (JSONException e) {
				Log.w("Facebook-ExampleShare", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Facebook-ExampleShare", "Facebook Error: " + e.getMessage());
			}
		}
	}
	public class WallPostRequestListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {
			System.out.println("wall post listener");
			Log.d("Facebook-ExampleShare", "Got response: " + response);
			String message = "<empty>";
			try {
				JSONObject json = Util.parseJson(response);
				message = json.getString("message");
				System.out.println("response shared=="+response.toString());
				System.out.println("message=="+message);
			} catch (JSONException e) {
				Log.w("Facebook-ExampleShare", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Facebook-ExampleShare", "Facebook Error: " + e.getMessage());
			}
			final String text = "Your Wall Post: " + message;
			//            ExampleShare.this.runOnUiThread(new Runnable() {
			//                public void run() {
			//                    mText.setText(text);
			//                }
			//            });
		}
	}

	public class WallPostDeleteListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {
			if (response.equals("true")) {
				Log.d("Facebook-ExampleShare", "Successfully deleted wall post");
				Score.this.runOnUiThread(new Runnable() {
					public void run() {
						//  mDeleteButton.setVisibility(View.INVISIBLE);
						//  mText.setText("Deleted Wall Post");
					}
				});
			} else {
				Log.d("Facebook-ExampleShare", "Could not delete wall post");
			}
		}
	}

	public class SampleDialogListener extends BaseDialogListener {

		public void onComplete(Bundle values) {
			final String postId = values.getString("post_id");
			System.out.println("dialog listener");
			if (postId != null) {
				Log.d("Facebook-ExampleShare", "Dialog Success! post_id=" + postId);
				mAsyncRunner.request(postId, new WallPostRequestListener());
				/*  mDeleteButton.setOnClickListener(new OnClickListener() {
	                    public void onClick(View v) {
	                        mAsyncRunner.request(postId, new Bundle(), "DELETE",
	                                new WallPostDeleteListener(), null);
	                    }
	                });
	                mDeleteButton.setVisibility(View.VISIBLE);*/
			} else {
				Log.d("Facebook-ExampleShare", "No wall post made");
			}
		}
	}
	public Bundle param(){
		System.out.println("fetching param");
		Bundle bun = new Bundle();
		bun.putString("picture", "http://reubro.tk/quizapp/images/ic_launcher.png");

		bun.putString("caption", "Welcome to challenger quiz");

		bun.putString("name", "Quiz");
		bun.putString("description","Hey! "+ userName +" scored "+ scoree +" in this Challenger Quiz. The quiz was really interesting. You may also try this at https://play.google.com/store/apps/details?id=com.reubro.quiz");
		bun.putString("link","https://play.google.com/store/apps/details?id=com.reubro.quiz");
		return bun;

	}
	private class LogoutRequestListener extends BaseRequestListener {
		public void onComplete(String response, final Object state) {
			// callback should be run in the original thread,
			// not the background thread
			mHandler.post(new Runnable() {
				public void run() {
					SessionEvents.onLogoutFinish();
				}
			});
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
				if(NetworkInformation.isNetworkAvailable(Score.this)){

					Response = Webservice.Scorelist(CatId,level);

					if(Response != null && Response.length()>0){
						parseres=Parser.parseScorelist(Response);
						System.out.println("parse response scorelist==="+parseres);

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
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case parserThread.SUCCESS:

				nscore=	Parser.getSscore();
				fname=Parser.getfname();
				Usid=Parser.getUsid();
				CCategory=Parser.getCcategory();
				Nname=Parser.getNname();
				d_added=Parser.getd_added();


				pDialog.dismiss();

				adapter=new ScorelistAdapter(Score.this,fname , nscore);
				listview.setAdapter(adapter);
//				adapter.setViewClickListener(Score.this);

			case parserThread.NO_NETWORK:
				//Toast.makeText(Score.this, "Network error", Toast.LENGTH_SHORT).show();
				pDialog.dismiss();
			}

		}
	};


	public void EmailButtonClicked(View v)
	{
		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (result == PackageManager.PERMISSION_GRANTED){
//			Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			View v1 = getWindow().getDecorView().getRootView();
			v1.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
			v1.setDrawingCacheEnabled(false);
			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Share.png";
			OutputStream out = null;
			File file=new File(path);
			try {
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			path=file.getPath();
			Uri bmpUri = FileProvider.getUriForFile(getApplicationContext(),"com.reubro.quiz.fileprovider", new File(path));
			Intent email;
			email = new Intent(android.content.Intent.ACTION_SEND);
			email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			email.putExtra(Intent.EXTRA_STREAM, bmpUri);
			email.putExtra(Intent.EXTRA_TEXT,"\n\n Hey! "+ userName +" scored "+ scoree +" in this Challenger Quiz, this is really interesting!  you can also try this at, https://play.google.com/store/apps/details?id=com.reubro.quiz");
			email.setType("image/png");
			try {
				startActivity(Intent.createChooser(email, "Send mail..."));

			}catch (android.content.ActivityNotFoundException ex) {

				Toast.makeText(Score.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(Score.this, "Permisssion is not granted.", Toast.LENGTH_SHORT).show();
		}


	}




	public void onBackPressed(){

		Intent i=new Intent(Score.this,CategorygridV2.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}

	// Asyntask for sending request....

	private class SendRequest extends AsyncTask<Void, Void, Void>{


		@Override
		public void onPreExecute() {
			super.onPreExecute();
			progressDialog=new ProgressDialog(Score.this);
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		public Void doInBackground(Void... params) {
			System.out.println("Inside do in background" );
			try {

				if (NetworkInformation.isNetworkAvailable(Score.this)) {

					System.out.println("The playid arraylist..."+playid_arrayList);

					playId = new String[playid_arrayList.size()];
					playId = playid_arrayList.toArray(playId);

					if(playId!=null)
						if(playId.length>0)
							data = Webservice.sendRequest(utils.getLoginuserid(), playId, CatId, level);
						else
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Score.this);

							// set dialog message
							alertDialogBuilder
							.setMessage("Select atleast one player")
							.setCancelable(false)
							.setPositiveButton("OK",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									Intent n = new Intent(Score.this,Categorygrid.class);
									Main screen = new Main();
									screen.finish();
									n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(n);
									Score.this.finish();
								}
							});


							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();

							// show it
							alertDialog.show();
						}
					if (data != null && data.length() > 0) {
						status = Parser.sendRequest(data);
						//System.out.println(status);

					}
				} else {
					//	Toast.makeText(Score.this,"No Internet Connectivity",Toast.LENGTH_LONG).show();
					progressDialog.dismiss();

				}
			} catch (Exception e) {
				//	Toast.makeText(Score.this,"No Internet Connectivity",Toast.LENGTH_LONG).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void args)
		{
			try {

				progressDialog.dismiss();


				if (status.equals("1"))
				{
					String message=Parser.get_SuccessSuggestMsg();
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Score.this);

					// set dialog message
					alertDialogBuilder
					.setMessage("Request sent successfully...")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent n = new Intent(Score.this,Categorygrid.class);
							Main screen = new Main();
							screen.finish();
							n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(n);
							Score.this.finish();
						}
					});


					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();


				}
				else if (status.equals("0"))
				{
					String message=Parser.get_errorSuggestMsg();
					Toast.makeText(Score.this,message,Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(Score.this,"Something went wrong, please try again later!! ",Toast.LENGTH_LONG).show();

				}
			}
			catch (Exception e)
			{
				Toast.makeText(Score.this,"Something went wrong, please try again later!! ",Toast.LENGTH_LONG).show();
			}
		}
	}

	private ViewClickListener mViewClickListener;

	public interface ViewClickListener {
		void onImageClicked(int position);
	}

	public void setViewClickListener (ViewClickListener viewClickListener) {
		mViewClickListener = viewClickListener;
	}

//	@Override
//	public void onCheckboxClicked(int position) {
//		// TODO Auto-generated method stub
//		//		Toast.makeText(Score.this, "Clicked is "+(position+1),Toast.LENGTH_SHORT).show();
//
////		if(playid_arrayList!=null)
////		{
//
//			if(playid_arrayList.contains(Usid[position]))
//			{
//				playid_arrayList.remove(Usid[position]);
//			}
//			else
//			{
//				playid_arrayList.add(Usid[position]);
//			}
////		}

//	}

}