package com.reubro.quiz;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PieChart1 extends Activity {
	public static int skipcount =Question.skipcount;
	public int rightcount=Question.rightc;

	public static int wrongcount=Question.wrongc;
	public static int notattcount=Question.notattendedcount;


	private PieChart mChart;
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA, Color.CYAN };

	private  int[] VALUES = new int[] {skipcount,rightcount,wrongcount,notattcount};

	private static String[] NAME_LIST = new String[] { "Skipped", "Right", "Wrong", "Not attended" };

	private CategorySeries mSeries = new CategorySeries("");

	private DefaultRenderer mRenderer = new DefaultRenderer();

	private GraphicalView mChartView;

	public EditText rightv;
	public EditText wrongv;
	public EditText skipv;
	public EditText totalv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piechart);

		totalv=(EditText)findViewById(R.id.totalValue);
		totalv.setText(""+Question.scorecount);

		skipv=(EditText)findViewById(R.id.skipvalue);
		skipv.setText(""+skipcount);

		rightv=(EditText)findViewById(R.id.rightvalue);
		rightv.setText(""+rightcount);

		wrongv=(EditText)findViewById(R.id.wrongvalue);
		wrongv.setText(""+wrongcount);

		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(0);
		mRenderer.setLegendTextSize(40);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setStartAngle(90);
		mRenderer.setShowLabels(false);

		try {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < VALUES.length; i++) {
			mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
			mRenderer.addSeriesRenderer(renderer);
		}

		if (mChartView != null) {
			mChartView.repaint();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);

//			mChartView.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
//
//					if (seriesSelection == null) {
//						Toast.makeText(PieChart1.this,"No chart element was clicked",Toast.LENGTH_SHORT).show();
//					}
//					else {
//						Toast.makeText(PieChart1.this,"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
//					}
//				}
//			});

//			mChartView.setOnLongClickListener(new View.OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View v) {
//					SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
//					if (seriesSelection == null) {
//						Toast.makeText(PieChart1.this,"No chart element was long pressed", Toast.LENGTH_SHORT);
//						return false;
//					}
//					else {
//						Toast.makeText(PieChart1.this,"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);
//						return true;
//					}
//				}
//			});
			layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		else {
			mChartView.repaint();
		}
	}
    @Override
    public void onBackPressed() {
           // super.onBackPressed();
    	rightv.setText("");
    	wrongv.setText("");
    	skipv.setText("");
    	totalv.setText("");
		System.out.println("backpress");
		this.finish();
            
    }


}