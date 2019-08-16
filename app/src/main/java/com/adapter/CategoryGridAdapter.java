package com.adapter;



import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reubro.quiz.Categorygrid;
import com.reubro.quiz.R;


import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryGridAdapter extends BaseAdapter {


	private Activity activity;
	Context context;
	private String[] nam;
	private String[] id;
	private String[]imagArr;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 

	public CategoryGridAdapter(Activity a,  String[] name, String[] imageArray, Context cntxt)

	{
		activity = a;
		nam = name;		
		imagArr = imageArray;
		context=cntxt;
		inflater 	= 	(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  imageLoader=new ImageLoader(activity.getApplicationContext());
		  System.out.println("image array====="+imagArr[0]);
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
		public ImageView image;
		
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		ViewHolder holder;
		if(convertView==null){
			vi = inflater.inflate(R.layout.categorygridadapter, null);
			holder=new ViewHolder();
			holder.cName=(TextView)vi.findViewById(R.id.text1);
			holder.image=(ImageView)vi.findViewById(R.id.ImageView1);
			vi.setTag(holder);
		}
		else

			holder=(ViewHolder)vi.getTag();
		 System.out.println("image array in view====="+imagArr[0]);

		System.out.println("category list"+nam[position]);
		holder.cName.setText(nam[position].toUpperCase());

		if(imagArr.length>0){
			
			System.out.println("image values of array=="+imagArr[position]);
			//  	holder.image.setImageBitmap(imagArr[position]);
//			holder.image.setTag(imagArr[position]);
//			 imageLoader.DisplayImage(imagArr[position], activity, holder.image);

			Glide.with(context)
					.load(imagArr[position])
					.apply(new RequestOptions()
							.placeholder(R.drawable.ic_launcher))
					.into(holder.image);
		}
		else{
			System.out.println("check image");
			//holder.image.setImageResource(R.drawable.ic_launcher);
		}
		return vi;
		
//		else
//		{
//		holder=(ViewHolder)vi.getTag();
//		}
//
//			System.out.println("Reached at vieww image==="+imagArr[position]);
//			holder.image.setTag(imagArr[position]);
//			
//			holder.cName.setText(""+nam[position]);
//			
//			
//			imageLoader.DisplayImage(imagArr[position],activity, holder.image);
//			
//			return vi;
	}}
