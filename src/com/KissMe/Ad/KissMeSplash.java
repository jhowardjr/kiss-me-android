package com.KissMe.Ad;



import com.KissMe.Ad.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class KissMeSplash extends Activity {

	private GestureDetector mGestureDetector;
	public static final String PREFS_NAME = "KissMeFile";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_WORLD_READABLE);

		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
		
		mGestureDetector = new GestureDetector(this,
				new KissMeSimpleOnGestureListener(this, new Intent(
						"com.KissMe.Ad.KISSMELIPS"), new Intent(
						"com.KissMe.Ad.KISSMELIPS")));

		
		this.setContentView(R.layout.splash);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		super.onKeyDown(keyCode, event);
		this.startActivity(new Intent("com.KissMe.Ad.KISSMELIPS"));

		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		mGestureDetector.onTouchEvent(event);
		return true;

	}
	
	

}