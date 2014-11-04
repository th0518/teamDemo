package com.example.httpurlparserxml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class WelcomeActivity extends Activity{
	private ImageView mImageView;
	private Animation mAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		mImageView = (ImageView) findViewById(R.id.welecomeImg);
		mAnimation = new AlphaAnimation(0, 1);
		mAnimation.setDuration(3000);
		mImageView.startAnimation(mAnimation);
		mAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(in);
				finish();
			}
		});
	}
}
