package com.KissMe.Ad;

import java.util.ArrayList;

import com.KissMe.Ad.R;
import com.adwhirl.AdWhirlLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KissMe extends Activity {
	public static final String PREFS_NAME = "KissMeFile";

	ArrayList<View> kissMeView;
	private long drawable;
	ImageView iv;
	String url;
	String mainPic;
	String deletePic;
	boolean markDelete;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		markDelete = false;
		kissMeView = new ArrayList<View>();

		setContentView(R.layout.main);
		
		AdWhirlLayout adWhirlLayout = new AdWhirlLayout(this, "ae3265939add47abb0ce27275d8e8f92");
		
		RelativeLayout.LayoutParams adWhirlLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, 75);
		
	
		addContentView(adWhirlLayout, adWhirlLayoutParams);
		
		
		SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,
				MODE_WORLD_READABLE);

		mainPic = settings.getString("MainPic", null);
		deletePic = settings.getString("deletePic", null);

		SharedPreferences.Editor editor = settings.edit();
		iv = (ImageView) findViewById(R.id.lips);

		if (mainPic != null) {
			iv.setImageURI(Uri.parse(mainPic));
		}

		if (deletePic != null) {
			deletePic(deletePic);

			editor.putString("deletePic", null);
			editor.commit();

		}

		editor.putBoolean("multiple", false);
		editor.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,
				MODE_WORLD_READABLE);

		drawable = settings.getLong("LipPic", R.drawable.icon);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		kissMeView.add(KissMeViewFactory.getKissMeView(this, (int) drawable,
				(int) event.getX(), (int) event.getY()));
		
		addContentView(kissMeView.get(kissMeView.size() - 1), new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		return true;
	}

	@Override
	public void onBackPressed() {

		SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,
				MODE_WORLD_READABLE);

		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean("multiple", true);
		editor.commit();

		startActivity(new Intent("com.KissMe.Ad.KISSMELIPS"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save:
			markDelete = false;
			savePic();
			displayStatus("Picture Saved");
			return true;
		case R.id.send:
			markDelete = true;
			sendPic();
			return true;
		case R.id.background:
			markDelete = true;
			setWallPaper();
			return true;
		case R.id.cancel:
			finish();
			return true;
		case R.id.clear:
			clearScreen();
			return true;

		}
		return false;
	}
	void displayStatus(String state)
	{
		TextView tv = new TextView(this);
		PopupWindow popUp = new PopupWindow(this);
		LinearLayout layout = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	    tv.setText(state);
	    tv.setGravity(Gravity.CENTER);
		layout.addView(tv, params);
		layout.setPadding(8, 8, 8, 8);
		layout.setGravity(Gravity.CENTER);
		popUp.setContentView(layout);
		popUp.setFocusable(true);
		popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
		popUp.update(0, 0,200,100);
	}

	public boolean clearScreen() {
		
		for(View v: kissMeView)
		{
			v.setVisibility(View.GONE);
		}
	
		return true;
	}
	
	void sendPic() {
		savePic();

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
		sendIntent.setType("image/*");

		startActivity(sendIntent);
		
		
	}

	void savePic() {

		Bitmap screenshot;

		View view = iv.getRootView();

		view.setDrawingCacheEnabled(true);

		screenshot = Bitmap.createBitmap(view.getDrawingCache());

		url = MediaStore.Images.Media.insertImage(getContentResolver(),
				screenshot, "KISSED"
						+ String.valueOf(System.currentTimeMillis()), "KISSED"
						+ String.valueOf(System.currentTimeMillis()));

		screenshot.recycle();

		String[] proj = { MediaStore.Images.Media.DATA };
		Uri uri = Uri.parse(url);
		Cursor cursor = managedQuery(uri, proj, null, null, null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		String path = cursor.getString(index);

		MediaScannerNotifier scanner = new MediaScannerNotifier(this, path,
				null);
		scanner.toString();

		if (markDelete) {

			SharedPreferences settings = getSharedPreferences(PREFS_NAME,
					MODE_WORLD_READABLE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("deletePic", url);
			editor.commit();
		}

	}

	void setWallPaper() {

		savePic();

		Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse(url));
		startActivity(intent);
		

	}

	void deletePic(String url) {
		getContentResolver().delete(Uri.parse(url), null, null);

	}

}