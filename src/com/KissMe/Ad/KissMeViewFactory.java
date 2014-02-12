package com.KissMe.Ad;

import android.content.Context;

public class KissMeViewFactory {

	public static KissMeView getKissMeView(Context context, int drawable,
			int x, int y) {

		return new KissMeView(context, drawable, x, y);

	}
}