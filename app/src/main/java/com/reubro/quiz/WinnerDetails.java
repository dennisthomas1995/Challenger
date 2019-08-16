package com.reubro.quiz;

import com.adapter.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerDetails extends Activity {

	TextView EventName,EventDate,EventLocation,UserName;
	ImageView Userimage;
	ImageLoader imageLoader; 
	WebView EventDes,UserDes;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.winnerdetails);
		Bundle bun 	=getIntent().getExtras();
		EventName 		= (TextView)findViewById(R.id.txv8);
		EventDes 		= (WebView)findViewById(R.id.txv3);
		UserDes 		= (WebView)findViewById(R.id.txv9);
		EventDate 		= (TextView)findViewById(R.id.txv5);
		EventLocation 		= (TextView)findViewById(R.id.txv7);
		UserName 		= (TextView)findViewById(R.id.text1);
		Userimage 		= (ImageView)findViewById(R.id.ImageView1);
		imageLoader=new ImageLoader(this);

		EventName.setText(bun.getString("WinnerContest"));
		//EventDes.setText(bun.getString("ContestDes"));
		EventDate.setText(bun.getString("WinnerContestdate"));
		UserName.setText(bun.getString("WinnerName"));
		String img=bun.getString("WinnerImage");
		System.out.println("img:"+img);
		if(bun.getString("WinnerImage").equals("")){
			Userimage.setImageResource(R.drawable.ic_launcher);

		}
		else{
//			Userimage.setTag(bun.getString("WinnerImage"));
//			imageLoader.DisplayImage(bun.getString("WinnerImage"), this, Userimage);
//			System.out.println("check image");

			Glide.with(getApplicationContext())
					.load(img)
					.apply(new RequestOptions()
							.placeholder(R.drawable.ic_launcher))
					.into(Userimage);

		}
		EventDes.setBackgroundColor(0x00000000);
		boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
		if (tabletSize) {
			String text = 
					//			"<html>" +
					//					"<head>"+
					//					
					//					"</head>"+
					//					"<body>"+ "<p >"+bun.getString("ContestDes")+"</p>" +
					//						"</font>"+ 
					//						"</p> "+ 
					//					"</body>" +
					//					"</html>";
					"<html>" +
					"<head>"+
					"<style  type='text/css'>span {	line-height: auto;height:580px auto;float: left;font-size:40px;font-family: Verdana;}</style>"+
					"</head>"+
					"<body>"+ "<p ><font size='5'>"+bun.getString("ContestDes")+"</font></p>" +
					"</body>" +
					"</html>";
			EventDes.loadData(text, "text/html", "utf-8");


						UserDes.setBackgroundColor(0x00000000);
						//			String text1 = "<html>" +
						//					"<head>"+
						//					"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:20px;font-family: Verdana;}</style>"+
						//					"</head>"+
						//					"<body>"+ "<p align=\"left\">"+bun.getString("WinnerDes")+"</p>" +
						//						"</font>"+ 
						//						"</p> "+ 
						//					"</body>" +
						//					"</html>";
						//			UserDes.loadData(text1, "text/html", "utf-8");
						String text1 = "<html>" +
								"<head>"+
								"<style  type='text/css'>span {	line-height: auto;height:580px auto;float: left;font-size:20px;font-family: Verdana;}</style>"+
								"</head>"+
								"<body>"+ "<p ><font size='5'>"+bun.getString("WinnerDes")+"</font></p>" +
								"</body>" +
								"</html>";
			
						UserDes.loadData(text1, "text/html", "utf-8");	
		}
		else{
			String text = "<html>" +
					"<head>"+
					"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:12px;font-family: Verdana;}</style>"+
					"</head>"+
					"<body>"+ "<p align=\"left\">"+bun.getString("ContestDes")+"</p>" +
					"</font>"+ 
					"</p> "+ 
					"</body>" +
					"</html>";
			EventDes.loadData(text, "text/html", "utf-8");


			UserDes.setBackgroundColor(0x00000000);
			String text1 = "<html>" +
					"<head>"+
					"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:12px;font-family: Verdana;}</style>"+
					"</head>"+
					"<body>"+ "<p align=\"left\">"+bun.getString("WinnerDes")+"</p>" +
					"</font>"+ 
					"</p> "+ 
					"</body>" +
					"</html>";
			UserDes.loadData(text1, "text/html", "utf-8");

		}




	}

}
