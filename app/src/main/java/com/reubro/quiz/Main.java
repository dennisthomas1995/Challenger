package com.reubro.quiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.Version2.RegisterV2;

public class Main extends Activity {

	ImageView logn;
	ImageView regs;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		logn =(ImageView) findViewById(R.id.loginbtn1);
		regs = (ImageView) findViewById(R.id.regstbtn1);
	}

	public void loginButtonClicked(View v)
	{
		Intent intent= new Intent(Main.this, LoginV2.class);
		startActivity(intent);
        Main.this.finish();
	}

	public void registerButtonClicked(View v)
	{
		Intent intent= new Intent(Main.this, RegisterV2.class);
		startActivity(intent);
		Main.this.finish();
	}
	

}
