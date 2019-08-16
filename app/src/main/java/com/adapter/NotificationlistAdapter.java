package com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adapter.WinnerListAdapter.ViewHolder;
import com.reubro.quiz.R;

public class NotificationlistAdapter extends BaseAdapter{
	
	private Activity activity;
	private String[] nam;
	private String[] categoryname1;
	private String[] level1;
	private String[] id;
	private String[]imagArr;
	private static LayoutInflater inflater=null;
	 

	public NotificationlistAdapter(Activity a,  String[] firstname1,String[] categoryname,String[] level)

	{
		activity = a;
		nam = firstname1;		
		categoryname1=categoryname;
		level1=level;
		inflater 	= 	(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		  //System.out.println("image array====="+imagArr[0]);
	}

	public int getCount() {
		return nam.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder{
		public TextView cName;
		public TextView Catname;
		public TextView levelname;
		
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		ViewHolder holder;
		if(convertView==null){
			vi = inflater.inflate(R.layout.notification_listadapter, null);
			holder=new ViewHolder();
			holder.cName=(TextView)vi.findViewById(R.id.text2);
			holder.Catname=(TextView)vi.findViewById(R.id.catTv);
			holder.levelname=(TextView)vi.findViewById(R.id.LevelTv);

			vi.setTag(holder);
		}
		else

			holder=(ViewHolder)vi.getTag();
				
		holder.cName.setText("Dear User,You got a Challenge request from " +nam[position].toUpperCase());
		holder.Catname.setText("Category: "+categoryname1[position]);
		holder.levelname.setText("Level: "+level1[position]);

		return vi;
		

	}}

