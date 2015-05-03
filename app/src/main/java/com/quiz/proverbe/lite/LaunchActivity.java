package com.quiz.proverbe.lite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		// Initialisation des variables applicatives
		Tools.initVars(this);

		// Passage à l'activité principale
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		}, 0);
	}

	@Override
	public void onBackPressed() {
	}
}
