package com.netconnect;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Webservice  {

	public static String pswd = null;
	static String response;
	static String str;

	//static String postURL ="http://50.63.103.36/quizapp/webService/?";
	// static String postURL ="http://50.62.229.135/quizapp/webService/?";
	static String postURL ="https://reubro.tk/quizapp/webService/?";

	//------------------------------------------------------------------	

	//Login
	public static String Login(String email,String pwd) throws IllegalStateException, ClientProtocolException,
	IOException{
		try{





			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingLogin(email,pwd).toString();

			params.add(new BasicNameValuePair("Data",str));   
			System.out.println("String returned in login===="+postURL+params);
			System.out.println("String returned in loginwebservice===="+str);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Loginwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
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

		System.out.println(">>>>>>>>>>>>>>>>>>>>. "+obj);
		return(obj);
	}


	//-----------------------------------------------------------------------------

	//Register
	public static String Register(String name,String eemail, String pswdd) throws IllegalStateException, 
	ClientProtocolException, IOException{
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  


			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingRegister(name,eemail,pswdd).toString();

			params.add(new BasicNameValuePair("Data",str));     
			System.out.println("String returned===="+postURL+params);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Registerwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
	}

	public static JSONObject JsonEncodingRegister(String your_name,String your_eemail,String your_password){

		/** Called when the activity is first created. */

		JSONObject obj=new JSONObject();
		try{

			JSONObject argsArray=new JSONObject();	
			argsArray.put("firstname",your_name);
			System.out.println("n ===" + your_name);
			argsArray.put("email",your_eemail);
			System.out.println("e= ===" + your_eemail);
			argsArray.put("password",your_password);

			System.out.println("p= ===" + your_password); 

			obj.put("argsArray", argsArray);
			obj.put("methodName","registration");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}	

	//-------------------------------------------------------------------------

	//categorygrid
	public static String Categorygrid() throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingCategory().toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Category Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingCategory(){	

		JSONObject obj=new JSONObject();
		try{



			obj.put("argsArray","");
			obj.put("methodName","Category");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}
	//-------------------------------------------------------------------------------------------

	//Level
	public static String Level() throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingLevel().toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Level Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingLevel(){	

		JSONObject obj=new JSONObject();
		try{


			obj.put("argsArray","");
			obj.put("methodName","level");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}

	//--------------------------------------------------------------------------

	//question
	public static String Question(String catid, String levid) throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingQuestion(catid,levid).toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Question Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingQuestion(String Cat_id, String Lev_id){	

		JSONObject obj=new JSONObject();
		try{


			JSONArray argsArray = new JSONArray();

			argsArray.put(Cat_id);
			argsArray.put(Lev_id);


			obj.put("argsArray",argsArray);
			obj.put("methodName","question");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}

	//-----------------------------------------------------------------------------

	//Logout
	public static String Logout(String userid ) throws IllegalStateException, ClientProtocolException,
	IOException{
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingLogout(userid).toString();
			System.out.println("String returned===="+str);
			params.add(new BasicNameValuePair("Data",str));     

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Logoutwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
	}

	public static JSONObject JsonEncodingLogout(String user_id){

		/** Called when the activity is first created. */
		//JSONObject obj=new JSONObject();
		JSONObject obj=new JSONObject();
		try{

			// JSONArray argsArray = new JSONArray();
			JSONArray argsArray = new JSONArray();
			argsArray.put(user_id);

			// argsArray.put(id);


			obj.put("argsArray", argsArray);
			obj.put("methodName","logout");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}


	//---------------------------------------------------------------------------

	//Score
	public static String Score(String Catid, String levlid,String userid, String score) throws IllegalStateException, ClientProtocolException,
	IOException{
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingScore(Catid,levlid,userid,score).toString();
			System.out.println("String returned score===="+str);
			params.add(new BasicNameValuePair("Data",str));     
			System.out.println("String returned score===="+str);
			System.out.println("String returned score===="+str+params);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Scorewebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
	}
	public static JSONObject JsonEncodingScore(String Cat_id, String levl_id,String user_id, String score){

		/** Called when the activity is first created. */
		//JSONObject obj=new JSONObject();
		JSONObject obj=new JSONObject();
		try{

			// JSONArray argsArray = new JSONArray();
			/*	JSONArray argsArray = new JSONArray();
			argsArray.put(user_id);
			 */
			// argsArray.put(id);


			JSONObject argsArray=new JSONObject();	
			argsArray.putOpt("cat_id", Cat_id);
			System.out.println("cat_id score===="+Cat_id);

			argsArray.putOpt("level_id", levl_id);
			System.out.println("level_id score ===="+levl_id);

			argsArray.putOpt("user_id", user_id);
			System.out.println("user_id score===="+user_id);

			argsArray.putOpt("score", score);
			System.out.println("score score===="+score);


			obj.put("argsArray", argsArray);
			obj.put("methodName","score");
			obj.put("className","quizapp");	

		}
		catch(Exception e){

		}
		return(obj);
	}


	//----------------------------------------------------------------------------

	//Scorelist
	public static String Scorelist(String catid, String levlid) throws IllegalStateException, ClientProtocolException,
	IOException{
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingScorelist(catid,levlid).toString();
			System.out.println("String returned Scorelist===="+str);
			params.add(new BasicNameValuePair("Data",str));     
			System.out.println("String returned Scorelist===="+str+params);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Scorelistwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
	}
	public static JSONObject JsonEncodingScorelist(String cat_id, String lev_id){

		/** Called when the activity is first created. */
		//JSONObject obj=new JSONObject();
		JSONObject obj=new JSONObject();
		try{

			// JSONArray argsArray = new JSONArray();
			/*	JSONArray argsArray = new JSONArray();
			argsArray.put(user_id);
			 */
			// argsArray.put(id);
			JSONObject argsArray=new JSONObject();
			argsArray.putOpt("cat_id", cat_id);
			argsArray.putOpt("level_id", lev_id);

			obj.put("argsArray", argsArray);
			obj.put("methodName","scoreList");
			obj.put("className","quizapp");	

		}
		catch(Exception e){

		}
		return(obj);
	}

	// event list


	public static String getEvents() throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingEvents().toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Event Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingEvents(){	

		JSONObject obj=new JSONObject();
		try{
			obj.put("argsArray","");
			obj.put("methodName","quizContext");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}

	// winners

	public static String getWinners() throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingNotification().toString();
			System.out.println("str="+str);
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Winner Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingNotification(){	

		JSONObject obj=new JSONObject();
		try{



			obj.put("argsArray","");
			obj.put("methodName","winners");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}
	
	
	

	// notification

	public static String getNotification(String userid) throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingNotification(userid).toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Winner Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingNotification(String userid){	

		JSONObject obj=new JSONObject();
		JSONObject argobj=new JSONObject();
		try{


			argobj.put("userid",userid);
			obj.put("argsArray",argobj);
			obj.put("methodName","shownotifications");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}
	
//delete notification
	
	public static String deletetNotification(String userid) throws IllegalStateException, 
	ClientProtocolException, IOException{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncodingdeleteNotification(userid).toString();
			params.add(new BasicNameValuePair("Data",str));    


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("Winner Response==" + str);

		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}		
	public static JSONObject JsonEncodingdeleteNotification(String userid){	

		JSONObject obj=new JSONObject();
		JSONObject argobj=new JSONObject();
		try{


			argobj.put("notification_id",userid);
			obj.put("argsArray",argobj);
			obj.put("methodName","deletenotification");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}
	
	

	//Get list of chapters...

	public static String get_chapterslist(String categoryID) throws IllegalStateException, ClientProtocolException,
	IOException{
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncoding_get_chapterslist(categoryID).toString();

			params.add(new BasicNameValuePair("Data",str));   
			System.out.println("String returned in login===="+postURL+params);
			System.out.println("String returned in loginwebservice===="+str);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Loginwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;  
	}

	public static JSONObject JsonEncoding_get_chapterslist(String categoryID){

		/** Called when the activity is first created. */

		JSONObject obj=new JSONObject();
		try{


			JSONArray argsArray = new JSONArray();
			argsArray.put(categoryID);

			obj.put("argsArray", argsArray);
			obj.put("methodName","login");
			obj.put("className","quizapp");	


		}
		catch(Exception e){

		}
		return(obj);
	}

	public static String addsuggestions( String userid, String namep,String emaill, String commentp) 
	{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncoding_addsuggestions(userid,emaill,namep,commentp).toString();

			params.add(new BasicNameValuePair("Data",str));   
			System.out.println("String returned in login===="+postURL+params);
			System.out.println("String returned in loginwebservice===="+str);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Loginwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return str;  
	}

	public static JSONObject JsonEncoding_addsuggestions(String userid,String suggestiontext,String email,String name){

		/** Called when the activity is first created. */

		JSONObject obj=new JSONObject();
		try{

			JSONObject argsArray = new JSONObject();
			argsArray.put("suggestion_msg",suggestiontext);
			argsArray.put("email",email);
			argsArray.put("user_name",name);
			argsArray.put("userid",userid);

			obj.put("argsArray", argsArray);
			obj.put("methodName","categorySuggestion");
			obj.put("className","quizapp");	

		}
		catch(Exception e){

		}
		return(obj);
	}


	public static String sendOTP( String userid, String otp) 
	{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncoding_sendOTP(userid,otp).toString();

			params.add(new BasicNameValuePair("Data",str));   
			System.out.println("String returned in login===="+postURL+params);
			System.out.println("String returned in loginwebservice===="+str);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Loginwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return str;  
	}

	public static JSONObject JsonEncoding_sendOTP(String userid,String otp){

		/** Called when the activity is first created. */

		JSONObject obj=new JSONObject();
		try{

			JSONObject argsArray = new JSONObject();
			argsArray.put("otp",otp);
			argsArray.put("userid",userid);

			obj.put("argsArray", argsArray);
			obj.put("methodName","registrationsteptwo");
			obj.put("className","quizapp");	

		}
		catch(Exception e){

		}
		return(obj);
	}

	// webservice for sendrequest

	public static String sendRequest( String userid, String[] playId,String categoryId, String levelId ) 
	{

		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000); 
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpClient client = new DefaultHttpClient(httpParameters);  

			HttpPost post = new HttpPost(postURL);  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			str=JsonEncoding_sendrequesting(userid,playId,categoryId,levelId).toString();
			System.out.println("Send Requests Json is..."+str);
			params.add(new BasicNameValuePair("Data",str));   
			System.out.println("String returned in send requestss===="+postURL+params);
			System.out.println("String returned in send request 2 2 : ===="+str);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);            
			HttpEntity resEntity = responsePOST.getEntity();  
			str=EntityUtils.toString(resEntity);
			str=str.trim();  
			System.out.println("RESPONSE in Loginwebservice= ===" + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return str;  
	}

	public static JSONObject JsonEncoding_sendrequesting(String userid,String[] playId, String categoryId,String levelId){

		/** Called when the activity is first created. */

		JSONObject obj=new JSONObject();
		JSONObject argsArray = new JSONObject();
		String players_str=null;
		try{

//			if(playId!=null)
//				if(playId.length>0)
//				{
//					JSONArray players=new JSONArray();
//					for(int i=0;i<playId.length;i++)
//					{
//						players.put(playId[i]);
//						players.put(playId[i]);
//					}
//					argsArray.put("playerId",players);
//				}
			
			if(playId!=null)
				if(playId.length>0)
				{
					
					for(int i=0;i<playId.length;i++)
					{
						if(i==0)
						{
							players_str=playId[i];
						}
						else
							players_str=players_str+","+playId[i];
					}
					argsArray.put("playerId",players_str);
				}

			argsArray.put("UserId",userid);
//		argsArray.put("playerId",players);
			argsArray.put("categoryID",categoryId);
			argsArray.put("level",levelId);


			obj.put("argsArray", argsArray);
			obj.put("methodName","sendrequesting");
			obj.put("className","quizapp");	

		}
		catch(Exception e){

		}
		return(obj);
	}

}




