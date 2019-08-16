package com.reubro.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;



public class EventDetails  extends Activity{
	TextView EventName,EventDate,EventLocation;
	WebView EventDes;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventdetails);
	    Bundle bun 	=getIntent().getExtras();
	    EventName 		= (TextView)findViewById(R.id.txv1);
	    EventDes 		= (WebView)findViewById(R.id.txv3);
	    EventDate 		= (TextView)findViewById(R.id.txv5);
	    EventLocation 		= (TextView)findViewById(R.id.txv7);
		EventName.setText(bun.getString("EventName"));
		//EventDes.setText(bun.getString("EventDes"));
		EventDate.setText(bun.getString("EventDate"));
		EventLocation.setText(bun.getString("EventLocation"));
		EventDes.setBackgroundColor(0x00000000);
		boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
		if (tabletSize) {
			String text = 
//					"<html>" +
//							"<head>"+
//							"</head>"+
//							"<body>"+ "<p style='font-size:40px'>"+bun.getString("EventDes")+"</p>" +
//								"</font>"+ 
//								"</p> "+ 
//							"</body>" +
//							"</html>";
//					"<html>" +
//					"<head>"+
//					"<style  type='text/css'>span {	text-align: left;line-height: auto;width: 260px;height:580px auto;float: left;font-size:20px;font-family: Verdana;}</style>"+
//					"</head>"+
//					"<body>"+ "<p align=\"left\">"+bun.getString("EventDes")+"</p>" +
//						"</font>"+ 
//						"</p> "+ 
//					"</body>" +
//					"</html>";
					"<html>" +
					"<head>"+
					"<style  type='text/css'>span {	line-height: auto;height:580px auto;float: left;font-size:23px;font-family: Verdana;}</style>"+
					"</head>"+
					"<body>"+ "<p ><font size='5'>"+bun.getString("EventDes")+"</font></p>" +
						
					"</body>" +
					"</html>";
			
			EventDes.loadData(text, "text/html", "utf-8");
		}
		else{
			String text = "<html>" +
					"<head>"+
					"<style  type='text/css'>span {	text-align: justify;line-height: auto;width: 300px;height:580px auto;float: left;font-size:16px;font-family: Arial;}</style>"+
					"</head>"+
					"<body style='text-align: justify;'>"+ "<p align=\"justify\">"+bun.getString("EventDes")+"</p>" +
						"</font>"+ 
						"</p> "+ 
					"</body>" +
					"</html>";
			EventDes.loadData(text, "text/html", "utf-8");
		}
		
		
		
		
	}
	

}
