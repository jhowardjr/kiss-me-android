package com.KissMe.Ad;

import com.KissMe.Ad.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.GridView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class KissMeLips extends Activity {
	private static final int SELECT_PICTURE = 1;
	public static final String PREFS_NAME = "KissMeFile";

	private String selectedImagePath;
	private ImageAdapter ia;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lips);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		ia = new ImageAdapter(this);
		gridview.setAdapter(ia);
		

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				SharedPreferences settings = getSharedPreferences(PREFS_NAME,
						MODE_WORLD_READABLE);

				SharedPreferences.Editor editor = settings.edit();
				editor.putLong("LipPic", ia.getItemId(position));
				editor.commit();
				
				
				if(settings.getBoolean("multiple", false))
				{
					finish();
					
				}else
				{
				
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, SELECT_PICTURE);
				}

			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);

				SharedPreferences settings = this.getSharedPreferences(
						PREFS_NAME, MODE_WORLD_READABLE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("MainPic", selectedImagePath);

				editor.commit();

				startActivity(new Intent("com.KissMe.Ad.KISSME"));
				finish();
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}