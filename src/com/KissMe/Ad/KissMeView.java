package com.KissMe.Ad;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class KissMeView extends View {

	private Drawable mDrawable;

	public KissMeView(Context context, int drawable, int x, int y) {
		super(context);

		
		Resources res = context.getResources();
		
		int width = res.getDrawable(drawable).getIntrinsicWidth();
		int height = res.getDrawable(drawable).getIntrinsicHeight();
		
		x = x - width/2;
		y = y - height/2;
		
		mDrawable = res.getDrawable(drawable);
		mDrawable.setBounds( x, y , x  + width   , y  + height );

	}

	@Override
	protected void onDraw(Canvas canvas) {
		mDrawable.draw(canvas);

	}
}