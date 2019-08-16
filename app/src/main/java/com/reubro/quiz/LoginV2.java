package com.reubro.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.Version2.CategorygridV2;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.netconnect.NetworkInformation;
import com.parser.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.Request.TAG;

/**
 * Created by reubro on 6/7/17.
 */

public class LoginV2 extends Activity {

    ProgressDialog pDialog;
    EditText email;
    EditText password;
    String emaill;
    String pass;
    String jsonResponse;
    String getResponse;
    String parseres;
    String userid;
    String	firstname;
    static String postURL ="https://reubro.tk/quizapp/webService/?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logins);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.pswd);

        if(!isInternetOn()){

            Toast.makeText(LoginV2.this,"No internet access",Toast.LENGTH_LONG).show();
        }

    }

    public void  Submit(View v)
    {
        emaill=email.getText().toString().trim();
        pass=password.getText().toString().trim();

        boolean flagemail=true;
        boolean flagplen=true;
        boolean flagtlen=true;
        boolean flagelen=true;
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emaill);
        if(emaill.trim().equals("")){

            flagelen=false;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Please enter your Email!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(!(matcher.matches()==true)){
            System.out.println("inside matches true");
            flagemail=false;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Please enter Valid Email!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else if(pass.trim().equals("")){
            flagplen=false;


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Please enter a password!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(pass.length()<6){
            flagtlen=false;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Please enter at least 6 characters for password !!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(flagplen==true&&flagemail==true&&flagtlen==true){

           if(NetworkInformation.isNetworkAvailable(LoginV2.this)){

            pDialog=new ProgressDialog(LoginV2.this);
            pDialog.setMessage("Logging in...");
            pDialog.setCancelable(false);
            pDialog.show();


            try{

                JSONArray argsArray = new JSONArray();
                argsArray.put(emaill);
                argsArray.put(pass);

                JSONObject obj=new JSONObject();
                obj.put("argsArray", argsArray);
                obj.put("methodName","login");
                obj.put("className","quizapp");

                System.out.println("OOOOOOOOOOOOOOOOOOO "+obj.toString());
                jsonResponse = obj.toString();

            }
            catch(Exception e){

            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    postURL+"Data="+jsonResponse,null,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    if(response != null && response.length()>0) {
                        parseres = Parser.parseLoginResponse(response.toString());

                        if(parseres.equals("1")){
                            userid=Parser.getUserId();
                            firstname=Parser.getfirstname();


                            System.out.println("user id in parser class"+userid);
                            Toast.makeText(LoginV2.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
                            Intent n = new Intent(LoginV2.this,CategorygridV2.class);
                            Main screen = new Main();
                            screen.finish();
                            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(n);
                            LoginV2.this.finish();
                            System.out.println("Success");

                            AppUtils utils=new AppUtils(LoginV2.this);
                            utils.setLoginuserid(userid);


                            utils.setLoginpassword(pass);


                            utils.setLoginemailid(emaill);


                            utils.setLoginusername(firstname);

                            LoginV2.this.finish();


                        }
                        else if(parseres.equals("0")){
                            pDialog.dismiss();
                            String message=Parser.getLoginMessage();
                            Toast.makeText(LoginV2.this,message,Toast.LENGTH_LONG).show();
                        }

                        else{
                            pDialog.dismiss();
                            //	String error=Parser.getLoginError();
                            Toast.makeText(LoginV2.this,"emailid or password incorrect !!!! ",Toast.LENGTH_LONG).show();
                        }

                    }

                    getResponse = response.toString();
                    Log.d(TAG, response.toString());
                    System.out.println("RESPOSE_NEW++++++ "+ getResponse);
                    pDialog.dismiss();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.dismiss();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(LoginV2.this);
            requestQueue.add(jsonObjReq);

            System.out.println("???????????????? " + jsonObjReq);



        }
        }
           else{
               Toast.makeText(LoginV2.this,"No internet available",Toast.LENGTH_LONG).show();
           }
        }



    public final boolean isInternetOn()
    {
        ConnectivityManager connec = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // ARE WE CONNECTED TO THE NET
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED )
        {
            // MESSAGE TO SCREEN FOR TESTING (IF REQ)
            //Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
            return true;
        }
        else if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  )
        {
            return false;
        }

        return false;
    }


}

