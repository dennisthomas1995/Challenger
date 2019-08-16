package com.adapter;



import com.reubro.quiz.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventListAdapter extends BaseAdapter {
	
	private Activity activity;
    private String[] data;
    private String[] eventname;
    private String[] eventid;
    private String[] eventdate;
   
    Context context;
    Typeface font1;
	static String posClicked;
	ViewHolder holder;
    private static LayoutInflater inflater=null;
    
    public EventListAdapter(Activity a, Context c,String[] id, String[] name ,String[] date) {
        activity = a;
        eventname=name;
        eventid=id;   
     
        eventdate=date;
        context=c;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   
    }

    public int getCount() {
    
        return eventid.length;
        
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
public static class ViewHolder{

		
		public TextView eventname;
		public TextView eventdate;
		

        		
		
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View vi=convertView;
		System.out.println("Reached at conertview of disp adapter==");
		if(convertView==null){
		System.out.println("Reached at conertview==null");
		vi = inflater.inflate(com.reubro.quiz.R.layout.eventlistadapter,parent, false);

		holder=new ViewHolder();
		System.out.println("Reached listview");
		holder.eventname=(TextView)vi.findViewById(R.id.txv1);
		holder.eventdate=(TextView)vi.findViewById(R.id.txv2);
		
	
		vi.setTag(holder);
	}
	else
	{
	holder=(ViewHolder)vi.getTag();
	}
		
		
		holder.eventname.setText(""+eventname[position]);
		holder.eventdate.setText(""+eventdate[position]);
			 	

	return vi;
	} 
	
	



	
	
}
