package com.reubro.quiz;


import com.AppUtils.AppUtils;
import com.Version2.CategorygridV2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class Description extends Activity {

    WebView web;
    String emailid;
    String Password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);


        AppUtils utils = new AppUtils(Description.this);
        emailid = utils.getLoginemailid();
        System.out.println("emailid===" + emailid);
        Password = utils.getLoginpassword();
        System.out.println("Password===" + Password);

        web = (WebView) findViewById(R.id.webview);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        boolean bigtabletSize = getResources().getBoolean(R.bool.isbigTablet);
        if (tabletSize) {
            if (bigtabletSize) {
                String text = "<html>" +
                        "<head>" +
                        "<body>" +
                        "<p align=\"justify\">" + "<font color='#000000'>" + "<font size='6'>" + "<b>Welcome to Challenger!</b>" +
                        "<p align=\"justify\">Your daily online quiz application. Challenger will bring out different questions daily covering the daily sports, news, science and technology, gk etc. By playing challenger daily you will be well informed.</p>" +
                        "<p align=\"justify\">-Challenge friends in any topic for a quick, real-time match or randomly play other people from around the world. Climb the rank and claim your titles.</p>" +

                        "</body>" +
                        "</html>";
                web.loadData(text, "text/html", "utf-8");
                web.setBackgroundColor(0);
            } else {
                String text = "<html>" +
                        "<head>" +
                        "<body>" +
                        "<p align=\"justify\">" + "<font color='#000000'>" + "<font size='5'>" + "<b>Welcome to Challenger!</b>" +
                        "<p align=\"justify\">Your daily online quiz application. Challenger will bring out different questions daily covering the daily sports, news, science and technology, gk etc. By playing challenger daily you will be well informed.</p>" +
                        "<p align=\"justify\">-Challenge friends in any topic for a quick, real-time match or randomly play other people from around the world. Climb the rank and claim your titles.</p>" +

                        "</body>" +
                        "</html>";
                web.loadData(text, "text/html", "utf-8");
                web.setBackgroundColor(0);
            }

        } else {
            String text = "<html>" +
                    "<head>" +
                    "<body>" +
                    "<p align=\"justify\">" + "<font color='#000000'>" + "<b>Welcome to Challenger!</b>" +
                    "<p align=\"justify\">Your daily online quiz application. Challenger will bring out different questions daily covering the daily sports, news, science and technology, gk etc. By playing challenger daily you will be well informed.</p>" +
                    "<p align=\"justify\">-Challenge friends in any topic for a quick, real-time match or randomly play other people from around the world. Climb the rank and claim your titles.</p>" +

                    "</body>" +
                    "</html>";
            web.loadData(text, "text/html", "utf-8");
            web.setBackgroundColor(0);

        }


    }

    public void Buttonclicked(View v) {
        if (emailid != null && emailid.length() != 0) {
            //Intent mainIntent = new Intent(Description.this, WinnerList.class);
            Intent mainIntent = new Intent(Description.this, CategorygridV2.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            Description.this.startActivity(mainIntent);
        } else {
            Intent mainIntent = new Intent(Description.this, Main.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            Description.this.startActivity(mainIntent);
        }

    }


}