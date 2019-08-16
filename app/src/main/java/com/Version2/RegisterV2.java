package com.Version2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.netconnect.NetworkInformation;
import com.parser.Parser;
import com.reubro.quiz.Categorygrid;
import com.reubro.quiz.LoginV2;
import com.reubro.quiz.Main;
import com.reubro.quiz.OneTimePassword;
import com.reubro.quiz.R;
import com.reubro.quiz.Register;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.Request.TAG;

/**
 * Created by reubro on 7/7/17.
 */

public class RegisterV2 extends Activity {

    EditText Name;
    EditText Eemail;
    EditText Passwd;
    ProgressDialog pDialog;
    String response;
    String parseres;
    String name;
    String e_mail;
    String psswd;
    String emailid;
    String jsonResponse;
    static String postURL ="https://reubro.tk/quizapp/webService/?";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Name = (EditText) findViewById(R.id.regusernam);

        Eemail = (EditText) findViewById(R.id.email);

        Passwd = (EditText) findViewById(R.id.regpswd);
    }

    public void  submitClicked(View v)
    {
        name=Name.getText().toString().trim();
        e_mail=Eemail.getText().toString().trim();
        psswd= Passwd.getText().toString().trim();

        boolean flagname=true;
        boolean flagplen=true;
        boolean flageemail=true;
        boolean flagelen=true;
        boolean flagemail=true;
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(e_mail);

        if(name.trim().equals("")){
            //					Eusername.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter your Name</font> "));
            //					Eusername.setTypeface(font);
            flagname=false;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Please enter your Name!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(e_mail.trim().equals("")){

            flageemail=false;
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

        else if(psswd.trim().equals("")){
            flagplen=false;
            //						Epassword.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter a password</font>"));
            //						Epassword.setTypeface(font);

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
        else if(psswd.length()<6){
            flagelen=false;
            //						Epassword.setText("");
            //						Epassword.setHint(Html.fromHtml("<font color='#BE3C3A'>Please enter at least 6 characters</font>"));
            //						Epassword.setTypeface(font);

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

        else if(flagplen==true&&flagemail==true&&flagname==true&&flagelen==true){

            if(NetworkInformation.isNetworkAvailable(RegisterV2.this)){

                pDialog=new ProgressDialog(RegisterV2.this);
                pDialog.setMessage("Registering...");
                pDialog.setCancelable(false);
                pDialog.show();


                JSONObject obj=new JSONObject();
                try{

                    JSONObject argsArray=new JSONObject();
                    argsArray.put("firstname",name);
                    System.out.println("n ===" + name);
                    argsArray.put("email",e_mail);
                    System.out.println("e= ===" + e_mail);
                    argsArray.put("password",psswd);

                    System.out.println("p= ===" + psswd);

                    obj.put("argsArray", argsArray);
                    obj.put("methodName","registration");
                    obj.put("className","quizapp");

                    jsonResponse = obj.toString();

                }
                catch(Exception e){

                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    postURL+"Data="+jsonResponse,null,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(response != null && response.length()>0) {
                            parseres = Parser.parseRegisterResponse(response.toString());
                            if(parseres.equals("1")){
                                emailid=Parser.getRegId();

                                System.out.println("user id in parser class"+	emailid);
                                Toast.makeText(RegisterV2.this,"Registered successfully!",Toast.LENGTH_SHORT).show();
//						        Intent n = new Intent(Register.this,Categorygrid.class);
                                Intent n = new Intent(RegisterV2.this,CategorygridV2.class);
                                startActivity(n);
                                RegisterV2.this.finish();
                                AppUtils utils=new AppUtils(RegisterV2.this);
                                utils.setLoginemailid(e_mail);

                                utils.setLoginpassword(psswd);

                                utils.setLoginuserid(emailid);

                                utils.setLoginusername(name);


                                RegisterV2.this.finish();

                            }
					        else if(parseres.equals("0")){
                                pDialog.dismiss();
                                String message=Parser.getRegMessage();
                                Toast.makeText(RegisterV2.this,message,Toast.LENGTH_LONG).show();
                            }

                            else{
                                pDialog.dismiss();
                                String error=Parser.getRegMessage();
                                Toast.makeText(RegisterV2.this,error,Toast.LENGTH_LONG).show();
                            }

                        }

                        System.out.println("RESPOSE_NEW++++++ "+ response.toString());
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.dismiss();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterV2.this);
            requestQueue.add(jsonObjReq);

            System.out.println("???????????????? " + jsonObjReq);

        }
    }
        else{
            Toast.makeText(RegisterV2.this,"No internet available",Toast.LENGTH_LONG).show();
        }


        }
    }
