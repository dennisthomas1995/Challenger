package com.Version2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;
import com.reubro.quiz.Level;
import com.reubro.quiz.R;
import com.reubro.quiz.Score;

public class QuestionV2 extends Activity {

	private boolean timerHasStarted = false;
	public	static String[] levelid;
	public	static String[] Categoryid;
	public	static String[] correctmark;
	public	static String[] ipaddress;
	public	static String[] dateadded;
	public	static String[] addedby;
	public	static String[] preference;
	public	static String[] questionId;
	public	static String[] questionText;
	public	static int[] optionslength;
	public	static int arrayLength;
	public	static int lastques2ndtime=0;
	public	static int oncecorrectans=0;
	public	static String[][] option;
	public	static String[][] optionid;
	public	static String[][] crctoption;
	public	static int[] optionsLength;
	public	static int[] crrctoption;
	public	static String[][]coption;
	public	static String[][]crctid;
	int clickcount=0;

	public static int skipcount=0;
	public static int notattendedcount=0;
	public static int wrongc,rightc;

	String errormesssage="";
	private CountDownTimer countDownTimer;

	ProgressDialog myProgressDialog;
	String Response;
	private AdView adView;
	WebView Question;
	Button Option1;
	Button Option2;
	Button Option3;
	Button Option4;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button btnn;
	Button btTimer;
	Button scbtn;
	RelativeLayout rl;
	RelativeLayout rl1;
	RelativeLayout rl2;
	RelativeLayout rl3;
	RelativeLayout rl4;
	WebView myWebView;
	public static int count=0;
	int qnslen;
	long seconds, fsec;
	private final long startTime = 60 * 1000;
	private final long interval = 1 * 1000;
	ProgressDialog pDialog;
	long s;
	String parseres;
	String parseres1;
	String catid;
	String levid;
	String CatId;
	int position;
	String level;
	String Levl_id;
	public static int score=0;
	public static int scorecount=0;

	static sscore sc1;
	String scoremess;
	String scorestatus;
	String uid;
	String scoree;
	Toast t1;
	boolean flaglen=true;
	Button levl;
	TextView sctxt;
	int num=0;
	TextView qnsnum;
	int mark;


	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		levl = (Button) findViewById(R.id.img22);

		//levl.setTextSize(20);
		myWebView = new WebView(this);

		button1 = new Button(this);
		button2 = new Button(this);
		button3 = new Button(this);
		button4 = new Button(this);
		button1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		button2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		button3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		button4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		//myWebView.setBackgroundColor(Color.parseColor("#FFEFD5"));
		myWebView.setBackgroundColor(Color.parseColor("#DF9216"));
		button1.setBackgroundColor(Color.parseColor("#FFEFD5"));
		button2.setBackgroundColor(Color.parseColor("#FFEFD5"));
		button3.setBackgroundColor(Color.parseColor("#FFEFD5"));
		button4.setBackgroundColor(Color.parseColor("#FFEFD5"));

		rl = (RelativeLayout) findViewById(R.id.relative1);
		rl1 = (RelativeLayout) findViewById(R.id.rel1);
		rl2 = (RelativeLayout) findViewById(R.id.rel2);
		rl3 = (RelativeLayout) findViewById(R.id.rel3);
		rl4 = (RelativeLayout) findViewById(R.id.rel4);
		Bundle bun =getIntent().getExtras();
		CatId=bun.getString("CatId");
		System.out.println("CatId qn"+CatId);
		level=bun.getString("Levl_id");
		System.out.println("level qn="+level);
		//check

		if(level.equals("1"))
		{
			levl.setText("Easy");
			scorecount = 15;
		}
		else if(level.equals("2"))
		{
			levl.setText("Medium");
			scorecount=15;

		}
		else if (level.equals("3"))
		{
			levl.setText("Hard");
			scorecount=12;

		}


		sc1= new sscore();

	//	btnn = (Button) findViewById(R.id.scrbtn);
		//Question = (WebView) findViewById(R.id.qbtn);
		//		Option1 = (Button) findViewById(R.id.opbtn1);
		//		Option2 = (Button) findViewById(R.id.opbtn2);
		//		Option3 = (Button) findViewById(R.id.opbtn3);
		//		Option4 = (Button) findViewById(R.id.opbtn4);
		btTimer = (Button) findViewById(R.id.timer);
		sctxt = (TextView) findViewById(R.id.scrtxt);
		qnsnum = (TextView) findViewById(R.id.txtqns);

		sctxt.setText(Integer.toString(score));


		//	countDownTimer = new MyCountDownTimer(startTime, interval);

		String a ="a15253c68ab3092";
		// Create the adView
		adView = new AdView(this, AdSize.BANNER, a);

		// Lookup your LinearLayout assuming it's been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout)findViewById(R.id.questionLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
		pDialog=new ProgressDialog(QuestionV2.this);
		pDialog.setMessage("Loading");
		pDialog.setCancelable(false);
		pDialog.show();
		Thread parserThread=new parserThread();
		parserThread.start();

		//		Question.setBackgroundColor(Color.parseColor("#FFEFD5"));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		count=0;
	}

	public void scoreButtonclicked(View v)
	{

	}

	public void skip(View v)
	{
		skipcount=skipcount+1;
		nextquestion1();
		timerHasStarted=false;
		timerbegins();
	}

	public void Validate(int value){
		if(count<arrayLength){
			if (timerHasStarted)
			{
				countDownTimer.cancel();
				timerHasStarted = false;

			}

			if(optionid[count][value].equals (crctid[count][0]))
			{

				oncecorrectans=0;
				flaglen=false;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				//		String rightcount1=crctid[].getCount();

				alertDialogBuilder
				.setMessage("Correct !!!")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();

						count=count+1;

						rightc=rightc+1;

						// changeddd

						score=	score+10;
						//						float f= Float.parseFloat(correctmark[count]);
						//							 mark=Integer.parseInt(correctmark[count]);

//						score=	score+mark;

						sctxt.setText(Integer.toString(score));
						if(count==arrayLength)
						{
							lastalert();
						}
						else{

							nextquestion();
							timerbegins();
						}

					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

				System.out.println("score"+score);
			}
			else
			{

				score=	score-2;
				sctxt.setText(Integer.toString(score));
				oncecorrectans=0;
				flaglen=false;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder
				.setMessage("Incorrect.The correct answer is "+coption[count][0])
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
						count=count+1;

						if(count==arrayLength)
						{
							lastalert();
						}
						else{
							nextquestion();
							timerbegins();
						}
					}
				});

				AlertDialog alertDialog1 = alertDialogBuilder.create();
				alertDialog1.show();

				System.out.println("score"+score);
			}
		}
		else
		{
			lastques2ndtime=1;
			if(oncecorrectans==1)
			{
				score=score-mark;
				oncecorrectans=0;
				sctxt.setText(Integer.toString(score));
			}
			if(optionid[14][value].equals (crctid[14][0]))
			{

				flaglen=false;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder
				.setMessage("Correct !!!")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
						oncecorrectans=1;

						rightc=rightc+1;

						// changeddd

//							score=	score+10;

						//float f= Float.parseFloat(correctmark[count]);
						//	 mark=Integer.parseInt(correctmark[count]);
						//System.out.println("correctmark=="+test);
//						score=	score+mark;

						sctxt.setText(Integer.toString(score));

						lastalert();



					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();


				System.out.println("score"+score);

			}
			else
			{
				score=	score-2;
				sctxt.setText(Integer.toString(score));
				oncecorrectans=0;
				flaglen=false;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder
				.setMessage("Incorrect.The correct answer is "+coption[14][0])
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						dialog.cancel();
						lastalert();

					}
				});

				AlertDialog alertDialog1 = alertDialogBuilder.create();
				alertDialog1.show();

				System.out.println("score"+score);

			}
		}
	}
	public void ButtonOneClick(View v){
		Validate(0);


	}
	public void nextquestion()
	{

		if(count!=arrayLength)
		{
			qnsnum.setText(Integer.toString(count+1)+"/"+Integer.toString(arrayLength));
			mark=Integer.parseInt(correctmark[count]);
			//Question.setText(questionText[count]);
			boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
			boolean bigtabletSize = getResources().getBoolean(R.bool.isbigTablet);
			rl.removeView(myWebView);
			rl1.removeView(button1);
			rl2.removeView(button2);
			rl3.removeView(button3);
			rl4.removeView(button4);
			myWebView = new WebView(this);
			//			button1 = new Button(this);
			//			button2 = new Button(this);
			//			button3 = new Button(this);
			//			button4 = new Button(this);
			//			button1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//myWebView.setBackgroundColor(Color.parseColor("#FFEFD5"));
			myWebView.setBackgroundColor(Color.parseColor("#DF9216"));
			//			button1.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button2.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button3.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button4.setBackgroundColor(Color.parseColor("#FFEFD5"));
			myWebView.setPadding(5, 15, 5, 15);
			//			button1.setPadding(5, 15, 5, 15);
			//			button2.setPadding(5, 15, 5, 15);
			//			button3.setPadding(5, 15, 5, 15);
			//			button4.setPadding(5, 15, 5, 15);
			if (tabletSize) {

				if(bigtabletSize)
				{
					button1.setTextSize(25);
					button2.setTextSize(25);
					button3.setTextSize(25);
					button4.setTextSize(25);
					String text = "<html>" +
							"<head>"+
							"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
							"</head>"+
							"<body>"+ "<p align=\"left\"><font size='6'>"+questionText[count]+"</font></p>" +
							"</font>"+
							"</p> "+
							"</body>" +
							"</html>";
					//Question.loadData(text, "text/html", "utf-8");
					myWebView.loadData(text, "text/html", "utf-8");
					rl.addView(myWebView);
				}
				else
				{
					button1.setTextSize(20);
					button2.setTextSize(20);
					button3.setTextSize(20);
					button4.setTextSize(20);
					String text = "<html>" +
							"<head>"+
							"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
							"</head>"+
							"<body>"+ "<p align=\"left\"><font size='5'>"+questionText[count]+"</font></p>" +

							"</body>" +
							"</html>";
					//Question.loadData(text, "text/html", "utf-8");
					myWebView.loadData(text, "text/html", "utf-8");
					rl.addView(myWebView);

				}
			}
			else{
				String text = "<html>" +
						"<head>"+
						"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:12px;font-family: Verdana;}</style>"+
						"</head>"+
						"<body>"+ "<p align=\"left\">"+questionText[count]+"</p>" +
						"</font>"+
						"</p> "+
						"</body>" +
						"</html>";
				//Question.loadData(text, "text/html", "utf-8");
				myWebView.loadData(text, "text/html", "utf-8");
				rl.addView(myWebView);
			}
			System.out.println("question 2"+questionText[count]);
			//			Option1.setText(option[count][0]);
			//			Option2.setText(option[count][1]);
			//			Option3.setText(option[count][2]);
			//			Option4.setText(option[count][3]);

			button1.setText(option[count][0]);
			button2.setText(option[count][1]);
			button3.setText(option[count][2]);
			button4.setText(option[count][3]);
			rl1.addView(button1);
			rl2.addView(button2);
			rl3.addView(button3);
			rl4.addView(button4);
			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(0);
				}

			});
			button2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(1);
				}

			});
			button3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(2);
				}

			});
			button4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(3);
				}

			});

		}
	}

	public void nextquestion1()
	{
		count=count+1;

		if(count==arrayLength)
		{
			lastalert();
		}
		if(count!=arrayLength)
		{
			qnsnum.setText(Integer.toString(count+1)+"/"+Integer.toString(arrayLength));
			mark=Integer.parseInt(correctmark[count]);
			//Question.setText(questionText[count]);
			boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
			boolean bigtabletSize = getResources().getBoolean(R.bool.isbigTablet);
			rl.removeView(myWebView);
			rl1.removeView(button1);
			rl2.removeView(button2);
			rl3.removeView(button3);
			rl4.removeView(button4);
			myWebView = new WebView(this);
			//			button1 = new Button(this);
			//			button2 = new Button(this);
			//			button3 = new Button(this);
			//			button4 = new Button(this);
			//			button1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//			button4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//myWebView.setBackgroundColor(Color.parseColor("#FFEFD5"));
			myWebView.setBackgroundColor(Color.parseColor("#DF9216"));
			//			button1.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button2.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button3.setBackgroundColor(Color.parseColor("#FFEFD5"));
			//			button4.setBackgroundColor(Color.parseColor("#FFEFD5"));
			myWebView.setPadding(5, 15, 5, 15);
			//			button1.setPadding(5, 15, 5, 15);
			//			button2.setPadding(5, 15, 5, 15);
			//			button3.setPadding(5, 15, 5, 15);
			//			button4.setPadding(5, 15, 5, 15);
			if (tabletSize) {

				if(bigtabletSize)
				{
					button1.setTextSize(25);
					button2.setTextSize(25);
					button3.setTextSize(25);
					button4.setTextSize(25);
					String text = "<html>" +
							"<head>"+
							"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
							"</head>"+
							"<body>"+ "<p align=\"left\"><font size='6'>"+questionText[count]+"</font></p>" +
							"</font>"+
							"</p> "+
							"</body>" +
							"</html>";
					//Question.loadData(text, "text/html", "utf-8");
					myWebView.loadData(text, "text/html", "utf-8");
					rl.addView(myWebView);
				}
				else
				{
					button1.setTextSize(20);
					button2.setTextSize(20);
					button3.setTextSize(20);
					button4.setTextSize(20);
					String text = "<html>" +
							"<head>"+
							"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
							"</head>"+
							"<body>"+ "<p align=\"left\"><font size='5'>"+questionText[count]+"</font></p>" +

							"</body>" +
							"</html>";
					//Question.loadData(text, "text/html", "utf-8");
					myWebView.loadData(text, "text/html", "utf-8");
					rl.addView(myWebView);

				}
			}
			else{
				String text = "<html>" +
						"<head>"+
						"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:12px;font-family: Verdana;}</style>"+
						"</head>"+
						"<body>"+ "<p align=\"left\">"+questionText[count]+"</p>" +
						"</font>"+
						"</p> "+
						"</body>" +
						"</html>";
				//Question.loadData(text, "text/html", "utf-8");
				myWebView.loadData(text, "text/html", "utf-8");
				rl.addView(myWebView);
			}
			System.out.println("question 2"+questionText[count]);
			//			Option1.setText(option[count][0]);
			//			Option2.setText(option[count][1]);
			//			Option3.setText(option[count][2]);
			//			Option4.setText(option[count][3]);

			button1.setText(option[count][0]);
			button2.setText(option[count][1]);
			button3.setText(option[count][2]);
			button4.setText(option[count][3]);
			rl1.addView(button1);
			rl2.addView(button2);
			rl3.addView(button3);
			rl4.addView(button4);



			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(0);
				}

			});
			button2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(1);
				}

			});
			button3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(2);
				}

			});
			button4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Validate(3);
				}

			});

		}
	}


	public void lastalert()
	{

		countDownTimer.cancel();
		timerHasStarted = false;

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder
		.setMessage("Thanks for attending the Quiz. Your score is "+String.valueOf(score))
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();

				if(!(sc1.getStatus() == AsyncTask.Status.RUNNING)){
					if(lastques2ndtime==1)
					{

						//wrongc=15-(rightc+skipcount);

						new sscore().execute();
					}
					else
						sc1.execute();
				}

				wrongc=scorecount-(rightc+skipcount+notattendedcount);
				int wrongtwo=2*wrongc;
			}
		});

		AlertDialog alertDialog2 = alertDialogBuilder.create();
		alertDialog2.show();

	}
	public void ButtonTwoClick(View v){
		Validate(1);


	}
	public void timerbegins(){
		if(timerHasStarted)
		{
			countDownTimer.cancel();
			timerHasStarted = false;
		}else{
			//		countDownTimer = new MyCountDownTimer(startTime, interval);
			countDownTimer.start();
			timerHasStarted = true;
		}
	}


	public void ButtonThreeClick(View v){
		Validate(2);

	}
	public void ButtonFourClick(View V){
		Validate(3);

	}

	@Override
	public void onBackPressed(){
		this.finish();
		if(timerHasStarted)
			countDownTimer.cancel();
		super.onBackPressed();
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}



	public void getQuestions(){



	}






	private	class parserThread extends Thread {
		public static final int SUCCESS = 0;
		public static final int NO_DATA 	  = 1;
		public static final int NO_NETWORK 	  = 2;

		public parserThread() {
		}
		@Override
		public void run() {



			try {
				if(NetworkInformation.isNetworkAvailable(QuestionV2.this)){
					System.out.println("catidd="+CatId);
					System.out.println("lev id="+level);

					Response = Webservice.Question(CatId, level);

					/*if (Response.equals("No Records Found !"))
					{
						Toast.makeText(Question.this, "No records found!" , Toast.LENGTH_SHORT).show();
						Intent in=new Intent(Question.this,Level.class);
		                   startActivity(in);

					}*/
					if(Response != null && Response.length()>0){
						parseres=Parser.parseQuestion(Response);



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

				pDialog.dismiss();


				if (parseres.equals("0"))
				{
					Toast.makeText(QuestionV2.this, "No records found!" , Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(QuestionV2.this,Level.class);
					Bundle bundle=new Bundle();
					bundle.putString("CatId",CatId);
					bundle.putInt("Position",position);
					intent.putExtras(bundle);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
					QuestionV2.this.finish();


				}
				else{
					levelid=Parser.getlevelid();
					Categoryid=Parser.getCategoryid();
					correctmark=Parser.getcorrectmark();
					ipaddress=Parser.getipaddress();
					dateadded=Parser.getdateadded();
					addedby=Parser.getaddedby();
					preference=Parser.getpreference();
					questionId=Parser.getquestionId();
					questionText=Parser.getquestionText();
					optionslength=Parser.getoptionslength();
					arrayLength=Parser.getarrayLength();
					option=Parser.getoption();
					optionid=Parser.getoptionid();
					crctoption=Parser.getcrctoption();
					optionsLength=Parser.getoptionsLength();
					crrctoption=Parser.getcrrctoption();
					coption=Parser.getcoption();
					crctid=Parser.getcrctid();


					qnsnum.setText(Integer.toString(count+1)+"/"+Integer.toString(arrayLength));
					mark=Integer.parseInt(correctmark[0]);
					boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
					boolean bigtabletSize = getResources().getBoolean(R.bool.isbigTablet);

					if (tabletSize) {
						if(bigtabletSize)
						{
							button1.setTextSize(25);
							button2.setTextSize(25);
							button3.setTextSize(25);
							button4.setTextSize(25);
							String text = "<html>" +
									"<head>"+
									"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
									"</head>"+
									"<body>"+ "<p align=\"left\"><font size='6'>"+questionText[count]+"</font></p>" +
									"</font>"+
									"</p> "+
									"</body>" +
									"</html>";
							//	Question.loadData(text, "text/html", "utf-8");
							myWebView.loadData(text, "text/html", "utf-8");
							rl.addView(myWebView);

						}
						else
						{
							button1.setTextSize(20);
							button2.setTextSize(20);
							button3.setTextSize(20);
							button4.setTextSize(20);
							String text = "<html>" +
									"<head>"+
									"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
									"</head>"+
									"<body>"+ "<p align=\"left\"><font size='5'>"+questionText[count]+"</font></p>" +
									"</font>"+
									"</p> "+
									"</body>" +
									"</html>";
							//							Question.loadData(text, "text/html", "utf-8");
							myWebView.loadData(text, "text/html", "utf-8");
							rl.addView(myWebView);
						}
					}
					else{
						String text = "<html>" +
								"<head>"+
								"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:12px;font-family: Verdana;}</style>"+
								"</head>"+
								"<body>"+ "<p align=\"left\">"+questionText[0]+"</p>" +
								"</font>"+
								"</p> "+
								"</body>" +
								"</html>";
						//Question.loadData(text, "text/html", "utf-8");
						myWebView.loadData(text, "text/html", "utf-8");
						rl.addView(myWebView);
					}
					//  Question.loadData(questionText[0],"questionText[0]/html","utf-8");
					//					Option1.setText(option[0][0]);
					//					Option2.setText(option[0][1]);
					//					Option3.setText(option[0][2]);
					//					Option4.setText(option[0][3]);

					button1.setText(option[0][0]);
					button2.setText(option[0][1]);
					button3.setText(option[0][2]);
					button4.setText(option[0][3]);
					rl1.addView(button1);
					rl2.addView(button2);
					rl3.addView(button3);
					rl4.addView(button4);
					button1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {
							Validate(0);
						}

					});
					button2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {
							Validate(1);
						}

					});
					button3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {
							Validate(2);
						}

					});
					button4.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {
							Validate(3);
						}

					});
					countDownTimer = new MyCountDownTimer(startTime, interval);

					if (!timerHasStarted) {
						countDownTimer.start();
						timerHasStarted = true;

					} else {
						countDownTimer.cancel();
						timerHasStarted = false;

					}
				}
				break;
			case parserThread.NO_DATA:
				pDialog.dismiss();

				Toast.makeText(QuestionV2.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
				break;
			case parserThread.NO_NETWORK:
				Toast.makeText(QuestionV2.this, "Network error", Toast.LENGTH_SHORT).show();
				pDialog.dismiss();
				break;
			default:
				//pDialog.dismiss();
				break;

			}

		}
	};


	public void main(long seconds)
	{
		if(fsec>=1)
		{
			s=fsec;
		}
		else
		{
			s=0;
		}

		//  Time.setText(Long.toString(s));

	}
	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			btTimer.setText("0");
			DisplayAlert();
			notattendedcount=notattendedcount+1;

		}

		@Override
		public void onTick(long millisUntilFinished) {
			btTimer.setText("" + millisUntilFinished/1000);
		}
	}
	public void DisplayAlert(){
		flaglen=false;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionV2.this);
		alertDialogBuilder
		.setMessage("The quiz has timed out")
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				count=count+1;
				if(count==arrayLength)
				{
//					lastalert();
				}
				else{
					nextquestion();
					timerHasStarted=false;
					timerbegins();
				}

			}
		});

		AlertDialog alertDialog3 = alertDialogBuilder.create();
		alertDialog3.show();
	}


	private class sscore extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(QuestionV2.this);
			pDialog.setMessage("Updating your score...");
			pDialog.setCancelable(false);
			pDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... s ) {
			// TODO Auto-generated method stub
			try {
				if(NetworkInformation.isNetworkAvailable(QuestionV2.this)){

					AppUtils utils=new AppUtils(QuestionV2.this);

					uid=utils.getLoginuserid();
					Response = Webservice.Score(CatId, level,uid,Integer.toString(score));
					//Response = Webservice.Score(CatId, level,uid,Integer.toString(120));

					if(Response != null && Response.length()>0){
						parseres1=Parser.parseScore(Response);

					}
				}
				else{
					Toast.makeText(QuestionV2.this, "No Internet Connectivity",Toast.LENGTH_LONG).show();
					pDialog.dismiss();
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return parseres1;
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(String Userid) {
			try
			{
				if(parseres1.equals("1")){
					pDialog.dismiss();

					scoremess=Parser.getscoremessage();
					scorestatus=Parser.getscore_status();
					pDialog.dismiss();

					Intent in=new Intent(QuestionV2.this,Score.class);
					Bundle bundle=new Bundle();
					bundle.putString("CatId",CatId);
					bundle.putString("Levl_id",level);
					bundle.putInt("score",score);

					in.putExtras(bundle);
					in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(in);



				}
				else{
					pDialog.dismiss();
					Toast.makeText(QuestionV2.this, "Update failed", Toast.LENGTH_SHORT).show();
				}

				sc1=new sscore();
			} catch(Exception e)
			{
				pDialog.dismiss();
				Toast.makeText(QuestionV2.this, "Update failed", Toast.LENGTH_SHORT).show();
			}
		}
	}


}





















