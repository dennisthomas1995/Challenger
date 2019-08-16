package com.reubro.quiz;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.adapter.ChapterList_Adapter;
import com.netconnect.NetworkInformation;
import com.netconnect.Webservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Chapters_List extends Activity{
	ListView list;
	ProgressDialog progressDialog;
	String data,status;
	String chosen_catid;
	String[] chapter_ids,chapter_names;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chapters_list);

		list=(ListView)findViewById(R.id.chapter_list);
		
		Bundle bun =getIntent().getExtras();
		chosen_catid=bun.getString("CatId");

		if(NetworkInformation.isNetworkAvailable(Chapters_List.this))
		{
			new chapterlist().execute();
		}
		else
		{
			Toast.makeText(Chapters_List.this,"Failed to connect to server",Toast.LENGTH_LONG).show();
		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent n = new Intent(Chapters_List.this,Chapter.class);
				Bundle b1=new Bundle();

				b1.putString("ChapterID",chapter_ids[arg2]);
				b1.putString("CategoryID",chosen_catid);
				n.putExtras(b1);	
				n.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(n);
			}

		});

	}


	private class chapterlist extends AsyncTask<String, String,String>
	{

		@Override
		public void onPreExecute() 
		{
			super.onPreExecute();
			progressDialog=new ProgressDialog(Chapters_List.this);
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false); 
			progressDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {

			try {

				if (NetworkInformation.isNetworkAvailable(Chapters_List.this)) {

					data = Webservice.get_chapterslist(chosen_catid);
				} else 
				{
					if ((progressDialog != null) && progressDialog.isShowing())
						progressDialog.dismiss();
				}
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (ClientProtocolException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return data;
		}
		@Override
		protected void onPostExecute(String response) {

			try
			{
				JSONObject dataObject	= new JSONObject(response);
				System.out.println("Reached inside parser category");
				status = dataObject.optString("status").toString();

				if(status.equals("1")){
					JSONArray dataArray 	= dataObject.getJSONArray("categories"); 

					//					for (int count = 0; count<dataArray.length(); count++) {
					//						Category_GridInfo gridviewinfo=new Category_GridInfo();
					//						gridviewinfo.setCat_id(dataArray.getJSONObject(count).optString("id").toString());
					//						gridviewinfo.setCat_name(dataArray.getJSONObject(count).optString("category").toString());
					//						gridviewinfo.setCat_image(dataArray.getJSONObject(count).optString("image").toString());
					//
					//						Category_GridInfo.grid_list.add(gridviewinfo);
					//						if (!database.isavailable_category(gridviewinfo.getCat_id())) {
					//							database.insert_categories(gridviewinfo.getCat_id(), gridviewinfo.getCat_name(), gridviewinfo.getCat_image());
					//						}
					//
					//
					//					}
					//
					//					JSONArray tableArray 	= dataObject.getJSONArray("tables"); 
					//					for (int count = 0; count<tableArray.length(); count++) {
					//						Tablelist_Info tableinfo=new Tablelist_Info();
					//						tableinfo.setTable_id(tableArray.getJSONObject(count).optString("id").toString());
					//						tableinfo.setTable_name(tableArray.getJSONObject(count).optString("name").toString());
					//
					//						Tablelist_Info.table_list.add(tableinfo);
					//						if (!database.isavailable_table(tableinfo.getTable_id())) {
					//							database.insert_table(tableinfo.getTable_id(),tableinfo.getTable_name());
					//						}
					//					}

					ChapterList_Adapter adapter = new ChapterList_Adapter(Chapters_List.this,chapter_ids,chapter_names);
					list.setAdapter(adapter);

					if ((progressDialog != null) && progressDialog.isShowing())
						progressDialog.dismiss();

					//	TableId_Setting();

				}
				else if (status.equals("0")) {

					if ((progressDialog != null) && progressDialog.isShowing())
						progressDialog.dismiss();

					//	Toast.makeText(Chapters_List.this,R.string.no_records_found,Toast.LENGTH_LONG).show();

					final AlertDialog.Builder builder = new AlertDialog.Builder(Chapters_List.this);
					builder.setMessage("No records found");
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog,
								int which)
						{
							onBackPressed();
							dialog.cancel();
						}
					});
					builder.setCancelable(false);
					builder.show();

				} else {
					if ((progressDialog != null) && progressDialog.isShowing())
						progressDialog.dismiss();

					Toast.makeText(Chapters_List.this,"Failed to connect to server",Toast.LENGTH_LONG).show();

				}

			} catch (Exception e) {
				if ((progressDialog != null) && progressDialog.isShowing())
					progressDialog.dismiss();			

				Toast.makeText(Chapters_List.this,"Failed to connect to server",Toast.LENGTH_LONG).show();
			}
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
