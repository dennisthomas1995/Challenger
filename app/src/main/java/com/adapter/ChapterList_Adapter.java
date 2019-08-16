package com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reubro.quiz.R;

public class ChapterList_Adapter  extends BaseAdapter {
	
	private Activity activity;
    private String[] name;
    private String[] id;
   
    Context context;
    Typeface font1;
	static String posClicked;
	ViewHolder holder;
    private static LayoutInflater inflater=null;
    
    public ChapterList_Adapter(Activity a,String[] cid, String[] cname) {
        activity = a;
        name=cname;
        id=cid;   
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   
    }

    public int getCount() {
    
        return id.length;
        
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
public static class ViewHolder{
		public TextView name_text;
		public TextView title_text;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View vi=convertView;
		System.out.println("Reached at conertview of disp adapter==");
		if(convertView==null){
		System.out.println("Reached at conertview==null");
		vi = inflater.inflate(com.reubro.quiz.R.layout.chapterlist_adapter,parent, false);

		holder=new ViewHolder();
		System.out.println("Reached listview");
		holder.name_text=(TextView)vi.findViewById(R.id.heading);
		holder.title_text=(TextView)vi.findViewById(R.id.name);
		
	
		vi.setTag(holder);
	}
	else
	{
	holder=(ViewHolder)vi.getTag();
	}
		
		
		holder.name_text.setText(""+name[position]);
		holder.title_text.setText("Chapter: "+id[position]);
			 	

	return vi;
	} 
	
}