package com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

//import com.reubro.quiz.Question;
import com.reubro.quiz.R;
import com.reubro.quiz.Score;

public class ScorelistAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater=null;
	private String[] nam;
	public static String[] Scoree;
	boolean[] checkBoxState;
	boolean[] scoreState;

	public ScorelistAdapter(Activity s, String[] nname, String[] sscore)
	{

		activity=s;
		nam=nname;
		Scoree=sscore;
		scoreState=new boolean[3];
		checkBoxState=new boolean[Scoree.length];

		inflater 	= 	(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}


	public int getCount() {
		return Scoree.length;
		
	}
	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class viewHolder
	{
		public TextView sNam,number;
		public TextView cScore;
		public CheckBox checkb;
	}


	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		viewHolder holder;
		if(convertView==null){
			vi = inflater.inflate(R.layout.list_score, null);
			holder=new viewHolder();
			holder.sNam=(TextView)vi.findViewById(R.id.Text1);
			holder.number=(TextView)vi.findViewById(R.id.number);
			holder.cScore=(TextView)vi.findViewById(R.id.Text2);
			//holder.checkb=(CheckBox)vi.findViewById(R.id.checkBox1);

			//holder.checkb.setTag(new Integer(position));
			holder.number.setTag(new Integer(position));  


			vi.setTag(holder);

		}
		else

			holder=(viewHolder)vi.getTag();

		if((position+1)==1)
		{
			holder.number.setText("");
			holder.number.setBackgroundResource(R.drawable.gold);
		}
		else if((position+1)==2)
		{
			holder.number.setText("");
			holder.number.setBackgroundResource(R.drawable.silver);

		}
		else if((position+1)==3)
		{
			holder.number.setText("");
			holder.number.setBackgroundResource(R.drawable.bronze);
		}
		else
		{
		holder.number.setText("    "+(position+1)+".");
		holder.number.setBackgroundColor(Color.parseColor("#00000000"));
		}
		holder.sNam.setText(" "+nam[position]);
		holder.cScore.setText("     "+Scoree[position]);

		int[] scoreint = new int[Scoree.length];

		scoreint[position] = Integer.parseInt(Scoree[position]);
		/*if(scoreint[position]>=Question.score){
			holder.checkb.setEnabled(false);
		}
		else
		{
			holder.checkb.setEnabled(true);
		}

		 if(checkBoxState[position])           
			 holder.checkb.setChecked(true);      
		 else             
			 holder.checkb.setChecked(false);
		
		holder.checkb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				Integer pos = (Integer)buttonView.getTag();     

				if(isChecked){                      
					checkBoxState[pos.intValue()]=true;      
				} else{             
					checkBoxState[pos.intValue()]=false;   
				}

				if(Score.playid_arrayList.contains(Score.Usid[position]))
				{
					Score.playid_arrayList.remove(Score.Usid[position]);
				}
				else
				{
					Score.playid_arrayList.add(Score.Usid[position]);
				}
			}
		});*/


		return vi;

	}

	//	private ViewClickListener mViewClickListener;

	//	public interface ViewClickListener {
	//	    void onCheckboxClicked(int position);
	//	}
	//
	//	public void setViewClickListener (ViewClickListener viewClickListener) {
	//	    mViewClickListener = viewClickListener;
	//	}




}







