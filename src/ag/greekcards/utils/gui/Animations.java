package ag.greekcards.utils.gui;

import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;

public final class Animations {
	private Animations() {}
	private static final int UN_SEGUNDO_Y_MEDIO = 1500;
	private static final float ALPHA_NOT_VISIBLE = 0.0f;
	private static final float ALPHA_FULLY_VISIBLE = 1.0f;
	
	public static final Animation FADE_IN;
	public static final Animation FADE_OUT_NOW;
	
	static {
		FADE_IN = new AlphaAnimation(ALPHA_NOT_VISIBLE, ALPHA_FULLY_VISIBLE);
		FADE_IN.setInterpolator(new DecelerateInterpolator());
		FADE_IN.setFillAfter(true);
		FADE_IN.setDuration(UN_SEGUNDO_Y_MEDIO);
		FADE_IN.setAnimationListener(new AnimationListener() {
			private static final String TAG = "ANIMATION_LISTENER";
			
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d(TAG, "onAnimationStart-FADE_IN");
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				Log.d(TAG, "onAnimationRepeat-FADE_IN");
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d(TAG, "onAnimationEnd-FADE_IN");
			}
		});
		
		FADE_OUT_NOW = new AlphaAnimation(ALPHA_FULLY_VISIBLE, ALPHA_NOT_VISIBLE);
		FADE_OUT_NOW.setDuration(0);
		FADE_OUT_NOW.setFillAfter(true);
		FADE_OUT_NOW.setAnimationListener(new AnimationListener() {
			private static final String TAG = "ANIMATION_LISTENER";
			
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d(TAG, "onAnimationStart-FADE_OUT_NOW");
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				Log.d(TAG, "onAnimationRepeat-FADE_OUT_NOW");
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d(TAG, "onAnimationEnd-FADE_OUT_NOW");
			}
		});
	}
}
