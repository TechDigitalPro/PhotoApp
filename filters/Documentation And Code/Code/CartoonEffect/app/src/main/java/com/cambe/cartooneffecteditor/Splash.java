package com.cambe.cartooneffecteditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.spalshh);
		Thread timer= new Thread()
		{
			public void run()
			{
				try
				{
					//Display for 3 seconds
					sleep(3000);
				}
				catch (InterruptedException e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				finally
				{
					//Goes to Activity  StartingPoint.java(STARTINGPOINT)
					Intent openstartingpoint=new Intent(getApplicationContext(),ImagePikActivity.class);
					startActivity(openstartingpoint);
					finish();
				}
			}
		};
		timer.start();
	}


	//Destroy Welcome_screen.java after it goes to next activity
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();

	}



}
