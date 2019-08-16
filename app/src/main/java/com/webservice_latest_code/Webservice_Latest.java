package com.webservice_latest_code;


import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.Request.TAG;

/**
 * Created by reubro on 4/7/17.
 */

public class Webservice_Latest extends Activity {

    String email;
    String pwd;
    static String str;
    static JSONArray argsArray = new JSONArray();
    static JSONObject obj;
    static String getResponse;
    static String postURL ="https://reubro.tk/quizapp/webService/?";

    public static String Login(final String email, final String pwd){


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                postURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        getResponse = response.toString();
                        Log.d(TAG, response.toString());
                        System.out.println("RESPOSE_NEW++++++ "+response);


                     //   pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               // pDialog.hide();
            }
        }) {

            /**
             * Passing some request headers
             * */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
               // str=JsonEncodingLogin(email,pwd).toString();

                try{


                    JSONArray argsArray = new JSONArray();
                    argsArray.put(email);
                    argsArray.put(pwd);

                    obj=new JSONObject();
                    obj.put("argsArray", argsArray);
                    obj.put("methodName","login");
                    obj.put("className","quizapp");


                }
                catch(Exception e){

                }

                headers.put("Data",obj.toString());
                headers.put("argsArray", argsArray.toString());
                headers.put("methodName","login");
                headers.put("className","quizapp");
                return headers;
            }


        };
       AppController.getInstance().addToRequestQueue(jsonObjReq);
        System.out.println("RETURNNNN " + getResponse);

        return getResponse;


    }

    public static JSONObject JsonEncodingLogin(String your_email,String your_password){

        /** Called when the activity is first created. */

        JSONObject obj=new JSONObject();
        try{


            JSONArray argsArray = new JSONArray();
            argsArray.put(your_email);
            argsArray.put(your_password);


            obj.put("argsArray", argsArray);
            obj.put("methodName","login");
            obj.put("className","quizapp");


        }
        catch(Exception e){

        }
        return(obj);
    }



}
