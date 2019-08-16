package com.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.ads.o;

import android.app.Activity;
import android.graphics.Paint.Join;

public class Parser  {
	static String[] Catename=null;
	static int Categolen=0;

	public static int resultarraylen=0;
	public static int namelen=0;
	static String[] CatId;
	static String[] Catimg=null;
	static String[] pref;

	static String[] levelid;
	static String[] Categoryid;
	static String[] correctmark;
	static String[] ipaddress;
	static String[] dateadded;
	static String[] addedby;
	static String[] preference;
	static String[] questionId;
	static String[] questionText;
	static int[] optionslength;
	static int arrayLength;
	static String[][] option;
	static String[][] optionid;
	static String[][] crctoption;
	static int[] optionsLength;
	static int[] crrctoption;
	static String[][]coption;
	static String[][]crctid;
	static String errormessage;

	static String loginstatus;
	static String loginerror;
	static String loginemailid;
	static String loginmessage;
	static String login_status;
	static String userid;
	static String firstname;
	static String logstatus;


	static String logoutmessage;
	static String logoutstatus;

	static String registerstatus;
	static String regerror;
	static String regid;
	static String regmessage;

	static int Levellen;
	static String[] Levlname=null;
	static String[] LevlId;


	static String scoremessage;
	static String score_status;

	static String scoreliststatus;
	static int Scorelen;
	static String[] Sscore;
	static String[] fname;
	static String[] Ccategory;
	static String[] Nname;
	static String[]	d_added;
	static String[]	Usid;


	public static int eventlistlen;			
	public static  String[] eventid	;	
	public static String[] eventname	;
	public  static String[] location;		
	public static String[] description	;	
	public static String[] date;
	public static String eventstatus;

	public static  String winnerstatus;


	public static int winnerlistlen;



	public static String[] winnerid	;		   
	public static String[] winnername	;		
	public static String[] winnerimage	;
	public static String[] winnerdes;	
	public static String[] titlecontest	;		
	public static String[] titledes;			
	public static String[] winnerdate	;


	public static String[] notification_id1=null;
	public static String[] notification_userid=null;
	public static String[] notification_categoryname=null;
	public static String[] firstname1=null;
	public static String[] player_id1=null;
	public static String[] category_id1=null;
	public static String[] level1=null;
	public static String[] name1=null;
	public static String[] category1=null;
	
	public static int firstnamecount=0;


	//--------------------------------------------
	//Login
	public static String parseLoginResponse(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);

			loginmessage=dataObject.optString("msg");
			login_status=dataObject.optString("login_status");
			System.out.println("login_status======="+login_status);

			JSONObject dataObject2	= new JSONObject(dataObject.optString("data"));
			firstname=dataObject2.optString("firstname");
			loginemailid=dataObject2.optString("email");
			System.out.println("email in parser======="+loginemailid);

			userid=dataObject2.optString("id");
			System.out.println("userid in parser======="+userid);

			loginerror=dataObject.optString("msg");
			System.out.println("error=="+loginerror);

			logstatus=dataObject.optString("status");
			System.out.println("error=="+logstatus);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return login_status;
	}	

	public static String getLoginemailId(){
		return loginemailid;
	}
	public static String getfirstname(){
		return firstname;
	}
	public static String getLoginMessage(){
		return loginmessage;
	}
	public static String getlogin_status(){
		return login_status;
	}
	public static String getLoginError(){
		return loginerror;
	}
	public static String getUserId(){
		return userid;
	}
	public static String getlogstatus(){
		return logstatus;
	}

	//-----------------------------------------------
	//Register

	public static String parseRegisterResponse(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);
			registerstatus=dataObject.optString("status");
			System.out.println("status getting in parser==="+registerstatus);
			regmessage=dataObject.optString("msg");

			regid=dataObject.optString("user_id");
			System.out.println("regIdN"+regid);
			regerror=dataObject.optString("error");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return registerstatus;
	}	

	public static String getRegId(){
		return regid;
	}
	public static String getRegStatus(){
		return registerstatus;
	}
	public static String getRegError(){
		return regerror;
	}
	public static String getRegMessage(){
		return regmessage;
	}

	//--------------------------------------------------------

	//Logout
	public static String parseLogout(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);
			logoutstatus=dataObject.optString("login_status");

			System.out.println("status getting in parser==="+logoutstatus);
			logoutmessage=dataObject.optString("msg");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return logoutstatus;
	}	
	public static String getlogoutmessage(){
		return logoutmessage;
	}
	/*public static String getlogoutstatus(){
			return logoutstatus;
		}
	 */

	//-------------------------------------------------------
	//category
	public static boolean parseCategory(String data)throws Exception{
		boolean catresponse=false;
		try{

			JSONArray catArray= new JSONArray(data);
			Categolen=catArray.length();
			if(Categolen!=0)
			{
				Catename				    = new String[Categolen];
				CatId                       =new String[Categolen];
				Catimg                      =new String[Categolen];
				pref                        =new String[Categolen];
			}
			for (int i = 0; i <Categolen; i++) {

				CatId         [i]  =catArray.getJSONObject(i).getString("id").toString();

				Catename      [i]	=catArray.getJSONObject(i).getString("category").toString();
				Catimg     [i]	=catArray.getJSONObject(i).getString("image").toString();
				pref    [i]=catArray.getJSONObject(i).getString("preference").toString();
				catresponse = true;

			}}

		catch(Exception e)
		{

		}	
		return catresponse;
	}
	public static String[] getCatname(){
		return Catename;
	}
	public static String[] getCatId(){
		return CatId;
	}
	public static String[] getCatimg(){
		return Catimg;
	}
	public static int getCategoryLength(){
		return Categolen;
	}
	public static String[] getpref(){
		return pref;
	}


	//-----------------------------------------------------------------


	//Level
	public static boolean parseLevel(String data)throws Exception{
		boolean levresponse=false;
		try{

			JSONArray levlArray= new JSONArray(data);
			Levellen=levlArray.length();
			if(Levellen!=0)
			{
				Levlname				    = new String[Levellen];
				LevlId                       =new String[Levellen];

			}
			for (int i = 0; i <Levellen; i++) {



				Levlname     [i]	=levlArray.getJSONObject(i).getString("name").toString();

				LevlId        [i]  =levlArray.getJSONObject(i).getString("id").toString();

				levresponse = true;

			}}

		catch(Exception e)
		{

		}	
		return levresponse;
	}
	public static String[] getLevlname(){
		return Levlname;
	}
	public static String[] getLevlId(){
		return LevlId;
	}

	public static int getLevelLength(){
		return Levellen;
	}

	//-----------------------------------------------------------------------

	//Question
	public static String parseQuestion(String data)throws Exception{
		String status = "0";
		try{
			JSONArray qArray = new JSONArray(data);
			arrayLength = qArray.length();
			System.out.println("arrayLength ***************** = "+arrayLength);

			questionId=new String[arrayLength];
			questionText= new String[arrayLength];
			levelid=new String[arrayLength];
			Categoryid=new String[arrayLength];
			correctmark=new String[arrayLength];
			ipaddress=new String[arrayLength];
			dateadded=new String[arrayLength];
			addedby=new String[arrayLength];
			preference=new String[arrayLength];

			optionslength=new int[arrayLength];
			option=new String[arrayLength][5];
			optionid=new String[arrayLength][5];


			crrctoption=new int[arrayLength];
			crctoption=new String[arrayLength][5];
			coption=new String[arrayLength][5];
			crctid=new String[arrayLength][5];



			for(int i=0;i<arrayLength;i++){
				System.out.println("status=");

				JSONObject kobj= qArray.getJSONObject(i);
				status = kobj.getString("status");
				System.out.println("status="+status);

				if(status.equals("1")){

					questionId[i]=kobj.getString("id");
					System.out.println("questionId"+questionId[i]);

					questionText[i]=kobj.getString("question");
					levelid[i]=kobj.getString("level_id");
					Categoryid[i]=kobj.getString("cat_id");
					correctmark[i]=kobj.getString("correct_mark");
					ipaddress[i]=kobj.getString("ip");
					dateadded[i]=kobj.getString("date_added");
					addedby[i]=kobj.getString("added_by");
					preference[i]=kobj.getString("preference");
					System.out.println("prefe .........  "+preference[i]);


					JSONArray cArray=kobj.getJSONArray("options");		
					optionslength[i] = cArray.length();
					JSONArray crctArray=kobj.getJSONArray("crctoption");
					crrctoption[i]=crctArray.length();


					for(int j=0; j<optionslength[i]; j++)
					{        
						System.out.println("reached here new");

						option   [i][j]	=cArray.getJSONObject(j).getString("options").toString();
						System.out.println("Option  new  [i] [j]  ="+option[i][j] );
						optionid [i][j]	=cArray.getJSONObject(j).getString("id").toString();
						System.out.println("optionid  [i] [j]  ="+optionid[i][j] );
					}
					for(int k=0;k<crrctoption[i];k++)
					{
						System.out.println("reached here new");

						coption  [i][k]	=crctArray.getJSONObject(k).getString("options").toString();
						System.out.println("coption  [i] [k]  ="+coption [i][k] );
						crctid  [i][k]	=crctArray.getJSONObject(k).getString("id").toString();
					}

				}

				else 
				{
					JSONArray eArray = new JSONArray(data);

					JSONObject error=eArray.getJSONObject(0);
					System.out.println("errrrrrr");
					errormessage=error.getString("msg");
					System.out.println("error mess in qns"+errormessage);
				}
			}
		}
		catch(Exception e)
		{

		}
		return status;	

	}


	public static String[] getlevelid(){
		return levelid;
	}
	public static String[] getCategoryid(){
		return Categoryid;
	}
	public	static String[] getcorrectmark(){
		return correctmark;
	}
	public	static String[] getipaddress(){
		return ipaddress;
	}
	public	static String[] getdateadded(){
		return dateadded;
	}
	public	static String[] getaddedby()
	{
		return ipaddress;
	}
	public	static String[] getpreference()
	{
		return	preference;
	}
	public	static String[] getquestionId()
	{
		return preference;
	}
	public	static String[] getquestionText()
	{
		return questionText;
	}
	public	static int[] getoptionslength()
	{
		return optionslength;
	}
	public	static int getarrayLength()
	{
		return arrayLength;
	}
	public	static String[][] getoption()
	{
		return option;
	}
	public	static String[][] getoptionid()
	{
		return optionid;
	}
	public	static String[][] getcrctoption()
	{
		return crctoption;
	}
	public	static int[] getoptionsLength()
	{
		return optionsLength;
	}
	public	static int[] getcrrctoption()
	{
		return	crrctoption;
	}
	public	static String[][] getcoption()
	{
		return	coption;
	}
	public	static String[][] getcrctid()
	{
		return	crctid;
	}
	public	static String geterrormessage()
	{
		return	errormessage;
	}


	///-------------------------------------------------------------
	//Parsing Score 
	public static String parseScore(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);

			scoremessage=dataObject.optString("msg");
			System.out.println("scoremessage  new"+scoremessage);
			score_status=dataObject.optString("status");
			System.out.println("reached here score_status"+score_status);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return score_status;
	}	
	public static String getscoremessage(){
		return scoremessage;
	}
	public static String getscore_status(){
		return score_status;
	}


	//-----------------------------------------------------------------------------
	//parsing the scorelist
	public static String parseScorelist(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);
			System.out.println("scoreliststatuscheck==");
			scoreliststatus=dataObject.getString("status");
			System.out.println("scoreliststatus=="+scoreliststatus);
			JSONArray scoreArray=dataObject.getJSONArray("Score");
			System.out.println("scorelistcheckcheck==");
			Scorelen=scoreArray.length();
			System.out.println("scorelength=="+Scorelen);
			if(Scorelen!=0)
			{
				Sscore     =new String[Scorelen]; 
				fname      =new String[Scorelen]; 
				Usid       =new String[Scorelen]; 
				Ccategory  =new String[Scorelen]; 
				Nname      =new String[Scorelen]; 
				d_added    =new String[Scorelen]; 
			}

			for (int i = 0; i <Scorelen; i++) {



				Sscore    [i]	=scoreArray.getJSONObject(i).getString("score").toString();

				fname        [i]  =scoreArray.getJSONObject(i).getString("firstname").toString();

				Usid        [i]  =scoreArray.getJSONObject(i).getString("user_id").toString();

				Ccategory        [i]  =scoreArray.getJSONObject(i).getString("category").toString();

				Nname       [i]  =scoreArray.getJSONObject(i).getString("level").toString();

				d_added       [i]  =scoreArray.getJSONObject(i).getString("date_added").toString();

			}	
		}
		catch(Exception e)
		{

		}
		return scoreliststatus;
	}	
	public	static String[] getSscore()
	{
		return	Sscore;
	}
	public	static String[] getfname ()
	{
		return	fname;
	}
	public	static String[] getUsid ()
	{
		return	Usid;
	}
	public	static String[] getCcategory()
	{
		return	Ccategory;
	}
	public	static String[] getNname()
	{
		return	Nname;
	}
	public	static String getscoreliststatus()
	{
		return	scoreliststatus;

	}
	public	static String[] getd_added()
	{
		return	d_added;

	}


	// event listi ng

	public static String getEventDetails(String data) {
		try{
			eventstatus="0";

			JSONArray dataArray= new JSONArray(data);
			eventlistlen				= dataArray.length();


			if(eventlistlen!=0){



				eventid			    = new String[eventlistlen];
				eventname			    = new String[eventlistlen];
				location			    = new String[eventlistlen];
				description			    = new String[eventlistlen];
				date			    = new String[eventlistlen];

				for (int count = 0; count< eventlistlen; count++) {

					eventid	[count] 	=	dataArray.getJSONObject(count).optString("id").toString();
					eventname	[count] 	=	dataArray.getJSONObject(count).optString("title").toString();
					location	[count] 	=	dataArray.getJSONObject(count).optString("location").toString();
					description	[count] 	=	dataArray.getJSONObject(count).optString("description").toString();
					date	[count] 	=	dataArray.getJSONObject(count).optString("event_date").toString();
					eventstatus = "1";

				}


			}
			else {

				eventstatus="0";

			}

		}


		catch(Exception e){

		}
		return eventstatus;

	}	

	// get winner details



	public static String getWinnerDetails(String data) {
		try{
			winnerstatus="0";

			JSONArray dataArray= new JSONArray(data);
			winnerlistlen				= dataArray.length();


			if(winnerlistlen!=0){



				winnerid			    = new String[winnerlistlen];
				winnername			    = new String[winnerlistlen];
				winnerimage			    = new String[winnerlistlen];
				winnerdes			    = new String[winnerlistlen];
				titlecontest			    = new String[winnerlistlen];
				titledes			    = new String[winnerlistlen];
				winnerdate			    = new String[winnerlistlen];

				for (int count = 0; count< winnerlistlen; count++) {

					winnerid	[count] 	=	dataArray.getJSONObject(count).optString("id").toString();
					winnername	[count] 	=	dataArray.getJSONObject(count).optString("name").toString();
					winnerimage	[count] 	=	dataArray.getJSONObject(count).optString("image").toString();
					winnerdes	[count] 	=	dataArray.getJSONObject(count).optString("user_description").toString();
					titlecontest	[count] 	=	dataArray.getJSONObject(count).optString("title_context").toString();
					titledes	[count] 	=	dataArray.getJSONObject(count).optString("description").toString();
					winnerdate	[count] 	=	dataArray.getJSONObject(count).optString("date_added").toString();

					winnerstatus = "1";

				}


			}
			else {

				winnerstatus="0";

			}

		}


		catch(Exception e){

		}
		return winnerstatus;

	}	

	static String suugestion_message=null, error_message=null;
	// Suggestion response parsing
	public static String parseSuggestion(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);

			suugestion_message=dataObject.optString("msg");
			login_status=dataObject.optString("status");

			JSONObject dataObject2	= new JSONObject(dataObject.optString("response"));
			error_message=dataObject2.optString("message");
			login_status=dataObject2.optString("status");
			System.out.println("dataobject:"+dataObject);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return login_status;
	}	

	public	static String get_SuccessSuggestMsg()
	{
		return	suugestion_message;

	}
	public	static String get_errorSuggestMsg()
	{
		return	error_message;

	}

	// Sending OTP
	public static String sendOTP(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);

			suugestion_message=dataObject.optString("msg");
			login_status=dataObject.optString("status");

			JSONObject dataObject2	= new JSONObject(dataObject.optString("response"));
			error_message=dataObject2.optString("message");
			login_status=dataObject2.optString("status");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return login_status;
	}	


	//send challenge request

	public static String sendRequest(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);

			suugestion_message=dataObject.optString("msg");
			login_status=dataObject.optString("status");

			JSONObject dataObject2	= new JSONObject(dataObject.optString("response"));
			error_message=dataObject2.optString("message");
			login_status=dataObject2.optString("status");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return login_status;
	}	


	//notification

	public static String ReturnNotification(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);
			login_status = dataObject.optString("status").toString();
				JSONArray resultarray=dataObject.getJSONArray("result");
			resultarraylen=resultarray.length();
						if(resultarraylen!=0)
			{
				namelen=resultarraylen;
				notification_id1 = new String[resultarraylen];
				firstname1				    = new String[resultarraylen];
				player_id1                      =new String[resultarraylen];
				category_id1                      =new String[resultarraylen];
				level1                        =new String[resultarraylen];
				name1                        =new String[resultarraylen];
				category1                        =new String[resultarraylen];
				notification_userid= new String[resultarraylen];
				notification_categoryname=new String[resultarraylen];
			}


			for (int i = 0; i <resultarraylen; i++) {
				
				firstname1[i]  =resultarray.getJSONObject(i).getString("firstname").toString();
				player_id1      [i]	=resultarray.getJSONObject(i).getString("player_id").toString();
				category_id1     [i]	=resultarray.getJSONObject(i).getString("category_id").toString();
				level1    [i]=resultarray.getJSONObject(i).getString("level").toString();
				notification_id1[i]=resultarray.getJSONObject(i).getString("notification_id").toString();
				notification_userid[i]=resultarray.getJSONObject(i).getString("userid").toString();
				notification_categoryname[i]=resultarray.getJSONObject(i).getString("category").toString();
			}
						
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return login_status;
	}
	//delete notification
	

	public static String DeleteNotification(String data) {
		try{

			JSONObject dataObject	= new JSONObject(data);
			login_status = dataObject.optString("status").toString();
				JSONArray resultarray=dataObject.getJSONArray("result");
			resultarraylen=resultarray.length();
			if(resultarraylen!=0)
			{
				notification_id1 = new String[resultarraylen];
				firstname1				    = new String[resultarraylen];
				player_id1                      =new String[resultarraylen];
				category_id1                      =new String[resultarraylen];
				level1                        =new String[resultarraylen];
				name1                        =new String[resultarraylen];
				category1                        =new String[resultarraylen];
			}


			for (int i = 0; i <resultarraylen; i++) {
				
				firstname1[i]  =resultarray.getJSONObject(i).getString("firstname").toString();

				player_id1      [i]	=resultarray.getJSONObject(i).getString("player_id").toString();
				category_id1     [i]	=resultarray.getJSONObject(i).getString("category_id").toString();
				level1    [i]=resultarray.getJSONObject(i).getString("level").toString();
				
			}

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return login_status;
}	


}

