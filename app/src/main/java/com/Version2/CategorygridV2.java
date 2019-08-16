package com.Version2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.AppUtils.AppUtils;
import com.adapter.CategoryGridAdapter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;
import com.parser.Parser;
import com.reubro.quiz.EventList;
import com.reubro.quiz.Level;
import com.reubro.quiz.Main;
import com.reubro.quiz.Notificationlist;
//import com.reubro.quiz.Question;
import com.reubro.quiz.Question;
import com.reubro.quiz.R;
import com.reubro.quiz.Score;
import com.reubro.quiz.Suggestion;
import com.reubro.quiz.WinnerList;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.facebook.Request.TAG;

/**
 * Created by reubro on 7/7/17.
 */

public class CategorygridV2 extends Activity implements AdapterView.OnItemClickListener {

    String Response,LogoutResponse;
    static String[] Category_name;
    static String[] Category_id;
    static String[] Cat_img;
    static String[] pref;
    boolean parseres;
    private int STORAGE_CODE = 1;
    ProgressDialog pDialog;
    GridView gridview;
    static int Categolen=0;
    Button logoutbtn;
    TextView NotifTv;
    String userid;
    private Boolean neterror = false;
    static String parserResp="";

    static String	response;
    String jsonResponse;
    String getNotificationURL;

    String Data;
    AppUtils utils;
    public static int notifyno=0;
    static String postURL ="https://reubro.tk/quizapp/webService/?";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorygrid);


        gridview = (GridView) findViewById(R.id.gridView1);
        gridview.setOnItemClickListener(CategorygridV2.this);
        NotifTv=(TextView)findViewById(R.id.notno);
        logoutbtn=(Button) findViewById(R.id.logoutbtnn);

        utils=new AppUtils(CategorygridV2.this);

        userid=utils.getLoginuserid();

       // new Notification1().execute();

        Question.score=0;
        Question.skipcount=0;
        Question.notattendedcount=0;
        Question.wrongc=0;
        Question.rightc=0;

        pDialog = new ProgressDialog(CategorygridV2.this);
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        if (NetworkInformation.isNetworkAvailable(CategorygridV2.this)) {
            neterror = false;
            getNotification();
            getData();
            if (permissionAlreadyGranted()) {
//            Toast.makeText(CategorygridV2.this, "Permission is already granted!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                requestPermission();

            }

        }else {
            neterror = true;
            Toast.makeText(CategorygridV2.this, "No Internet Connectivity", Toast.LENGTH_LONG).show();
            //					pDialog.dismiss();
        }
    }


    public void getNotification(){



        JSONObject obj=new JSONObject();
        JSONObject argobj=new JSONObject();
        try{


            argobj.put("userid",userid);
            obj.put("argsArray",argobj);
            obj.put("methodName","shownotifications");
            obj.put("className","quizapp");
            getNotificationURL = obj.toString();

        }
        catch(Exception e){

        }


        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                postURL +"Data="+getNotificationURL,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("Debug", response.toString());

                if (response != null && response.length() > 0) {
                    try {
                        parserResp = Parser.ReturnNotification(response);
                        System.out.println("inside!!!!!!!!!!!!!!!!!!!!!!!!0000: "+parserResp.toString());
                        if (parserResp.equals("1")) {
                            //				pDialog.dismiss();
                            System.out.println("inside!!!!!!!!!!!!!!!!!!!!!!!!11111");
                            notifyno = Parser.namelen;

                            NotifTv.setText("" + notifyno);
                        }else if(parserResp.toString().equals("0")){
                            System.out.println("inside else if 0000");
                            NotifTv.setVisibility(View.GONE);
                        }else if (neterror) {
                            Toast.makeText(CategorygridV2.this, "No Internet Connectivity", Toast.LENGTH_LONG).show();
                            //				pDialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("RESPOSE_NEW++++++ " + response.toString());
                pDialog.dismiss();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        });

        RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(CategorygridV2.this);
        requestQueue.add(jsonObjReq);

    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }
//
    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CategorygridV2.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("The app requires this permission. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }



    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.WRITE_EXTERNAL_STORAGE );
                if (! showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }


/*
    private class Notification1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //			pDialog=new ProgressDialog(Categorygrid.this);
            //			pDialog.setMessage("Loading...");
            //			pDialog.setCancelable(false);
            //			pDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... s) {
            // TODO Auto-generated method stub
            try {
                if (NetworkInformation.isNetworkAvailable(CategorygridV2.this)) {

                    neterror = false;

                    response = Webservice.getNotification(userid);

                    System.out.println("responsee dep===" + response);
                    if (response != null && response.length() > 0) {
                        parserResp = Parser.ReturnNotification(response);
                    }
                } else {
                    neterror = true;
                    Toast.makeText(CategorygridV2.this, "No Internet Connectivity", Toast.LENGTH_LONG).show();
                    //					pDialog.dismiss();
                }

            } catch (Exception e) {
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
            if (parserResp.equals("1")) {
                //				pDialog.dismiss();

                notifyno = Parser.namelen;

                NotifTv.setText("" + notifyno);
            } else if (neterror) {
                Toast.makeText(CategorygridV2.this, "No Internet Connectivity", Toast.LENGTH_LONG).show();
                //				pDialog.dismiss();
            }
        }
    }
*/

        public void getData() {

            JSONObject obj = new JSONObject();
            try {


                obj.put("argsArray", "");
                obj.put("methodName", "Category");
                obj.put("className", "quizapp");
                jsonResponse = obj.toString();
                System.out.println("jsonresponse:"+jsonResponse);

            } catch (Exception e) {

            }
            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    postURL +"Data="+jsonResponse,new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    System.out.println("haiiiiiiiiiiii VVVVVVV22222222222");
                    Log.d("Debug", response.toString());

                    if (response != null && response.length() > 0) {
                        try {
                            parseres = Parser.parseCategory(response.toString());

                            System.out.println("Parserrrrrrrrrrrrrr "+ parseres);

                            if (parseres == false) {

                                pDialog.dismiss();
                                Toast.makeText(CategorygridV2.this, "Sorry! No Data available", Toast.LENGTH_LONG).show();

                            }
                            else if (parseres == true) {

                                Categolen = Parser.getCategoryLength();

                                if (Categolen == 0) {
                                    pDialog.dismiss();

                                    Toast.makeText(CategorygridV2.this, "Sorry! No Data found", Toast.LENGTH_LONG).show();
                                } else {

                                    Category_name = Parser.getCatname();
                                    Category_id = Parser.getCatId();
                                    Cat_img = Parser.getCatimg();
                                    pref = Parser.getpref();


                                    pDialog.dismiss();

                                    gridview.setAdapter(new CategoryGridAdapter(CategorygridV2.this, Category_name, Cat_img,getApplicationContext()));
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }/*else{

                        pDialog.dismiss();
                        Toast.makeText(CategorygridV2.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();

                    }
*/
                    System.out.println("RESPOSE_NEW++++++ " + response.toString());
                    pDialog.dismiss();
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.dismiss();
                }
            });

            RetryPolicy policy = new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);

            RequestQueue requestQueue = Volley.newRequestQueue(CategorygridV2.this);
            requestQueue.add(jsonObjReq);

            System.out.println("???????????????? " + jsonObjReq);


        }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        System.out.println("pos clicked===="+arg2);
        Intent intent=new Intent(CategorygridV2.this,LevelV2.class);
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

                        Intent n= new Intent(CategorygridV2.this,Main.class);
                        n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(n);
                        CategorygridV2.this.finish();

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
        Logout();
    }

    public void notificationButtonClicked(View view){

        Intent n=new Intent(CategorygridV2.this,Notificationlist.class);
        n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(n);

    }


    public void Logout(){


        if(NetworkInformation.isNetworkAvailable(CategorygridV2.this)){
            JSONObject obj=new JSONObject();
            try{
                System.out.println("utils uid=="+userid);
                LogoutResponse = Webservice.Logout(userid);
                System.out.println("response of logout in reslistclass=="+Response);
                // JSONArray argsArray = new JSONArray();
                JSONArray argsArray = new JSONArray();
                argsArray.put(userid);

                // argsArray.put(id);


                obj.put("argsArray", argsArray);
                obj.put("methodName","logout");
                obj.put("className","quizapp");


            }
            catch(Exception e){

            }
            System.out.println("logout response:"+LogoutResponse);
            if(LogoutResponse != null && LogoutResponse.length()>0){
                Data=Parser.parseLogout(LogoutResponse);
                System.out.println("parse status=="+Data);


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

                    Intent n= new Intent(CategorygridV2.this,Main.class);
                    n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(n);
                    CategorygridV2.this.finish();

                    //					Intent intent = new Intent("CLOSE_ALL");
                    //					this.sendBroadcast(intent);
                    //System.exit(0);

                }
                else if(Data.equals("1")){

                    Toast.makeText(CategorygridV2.this,"Logout failed",Toast.LENGTH_LONG).show();
                }

            }
            else{
                pDialog.dismiss();
                Toast.makeText(CategorygridV2.this, "Sorry! No Data found",Toast.LENGTH_LONG).show();
            }
        }
        else{

            pDialog.dismiss();
            Toast.makeText(CategorygridV2.this, "Sorry! Network Error",Toast.LENGTH_LONG).show();

        }

        pDialog.dismiss();

    }
    public void onBackPressed(){

        this.finish();

    }
    public void eventsClick(View view){
        Intent n=new Intent(CategorygridV2.this,EventListV2.class);
        n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(n);
    }

    public void winnersClick(View view){

        Intent n=new Intent(CategorygridV2.this,WinnerList.class);
        n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(n);
    }

    public void suggestionClick(View view){
        Intent n=new Intent(CategorygridV2.this,SuggestionV2.class);
        n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(n);
    }
}

