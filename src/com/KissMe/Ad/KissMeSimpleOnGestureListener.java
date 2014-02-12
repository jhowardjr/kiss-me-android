package com.KissMe.Ad;



import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.GestureDetector.SimpleOnGestureListener;

class KissMeSimpleOnGestureListener extends SimpleOnGestureListener {

	Activity activity;

	private static final float DISTANCE_DIP = 16.0f;
	private static final float PATH_DIP = 60.0f;
	private int minScaledVelocity;
	private Intent leftActivity;
	private Intent rightActivity;

	public KissMeSimpleOnGestureListener(Activity activity,
			Intent leftActivity, Intent rightActivity) {
		this.activity = activity;
		this.leftActivity = leftActivity;
		this.rightActivity = rightActivity;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		minScaledVelocity = ViewConfiguration.get(activity)
				.getScaledMinimumFlingVelocity();

		double vertical = Math.abs(e1.getY() - e2.getY());
		double horizontal = Math.abs(e1.getX() - e2.getX());

		if (vertical > PATH_DIP) {
			return false;
		}

		else if (horizontal > DISTANCE_DIP
				&& Math.abs(velocityX) > minScaledVelocity) {

			if (velocityX < 0) {
				moveScreenLeft();
			}

			else {
				moveScreenRight();
			}
			return true;
		}
		return false;

	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event){
		super.onSingleTapConfirmed(event);
		
		activity.startActivity(leftActivity);
		activity.overridePendingTransition(R.anim.intoleft, R.anim.outtoleft);
		activity.finish();
		
		return true;
	}

	public void moveScreenLeft() {

		activity.startActivity(leftActivity);
		activity.overridePendingTransition(R.anim.intoleft, R.anim.outtoleft);
		activity.finish();

	}

	public void moveScreenRight() {

		activity.startActivity(rightActivity);
		activity.overridePendingTransition(R.anim.intoright, R.anim.outtoright);
		activity.finish();

	}

}