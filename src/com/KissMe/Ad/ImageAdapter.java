package com.KissMe.Ad;





import com.KissMe.Ad.R;

import android.content.Context;
import android.content.res.TypedArray;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    
    private TypedArray mThumbIds;
    
    public ImageAdapter(Context c) {
        mContext = c;
        mThumbIds =  mContext.getResources().obtainTypedArray(
    			R.array.kiss_image);
    }	

    public int getCount() {
        return mThumbIds.length();
    }

    public Object getItem(int position) {
        return mThumbIds.getResourceId(position, 1);
    }

    public long getItemId(int position) {
    	return mThumbIds.getResourceId(position, 1);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) { 
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds.getResourceId(position, 1));
        return imageView;
    }

  
}