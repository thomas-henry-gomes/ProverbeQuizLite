package com.quiz.proverbe.lite;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;


import com.google.android.gms.ads.*;
import com.quiz.proverbe.lite.dictionary.Dictionary;
import com.quiz.proverbe.lite.dictionary.Level;

public class LevelActivity extends ActionBarActivity {
	private AdView adView;
	private InterstitialAd interstitial;

	final String LEVEL_ID = "level_id";

	final String INTERSTITIAL_ADS = "interstitial_ads";
	String interstitialAds = "";

	Toast checkToast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);

		Intent intent = getIntent();
		if (intent != null) {
			interstitialAds = intent.getStringExtra(INTERSTITIAL_ADS);
		}
		else {
			interstitialAds = "";
		}

		if ("true".equals(interstitialAds)) {
			interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(Tools.getInterstitialAdsId());
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitial.setAdListener(new AdListener() {
                /**
                 * Called when an Activity is created in front of your app,
                 * presenting the user with a full-screen ad UI in response to their touching ad.
                 */
                public void onAdOpened() {
                    interstitialAds = "false";
                    //Tools.showToast(getApplicationContext(), "onPresentScreen", Toast.LENGTH_LONG);
                }

                /**
                 * Called when the full-screen Activity presented with onPresentScreen has been dismissed
                 * and control is returning to your app.
                 */
                public void onAdClosed() {
                    //Tools.showToast(getApplicationContext(), "onDismissScreen", Toast.LENGTH_LONG);
                }

                /**
                 * Called when an Ad touch will launch a new application.
                 */
                public void onAdLeftApplication() {
                    //Tools.showToast(getApplicationContext(), "onLeaveApplication", Toast.LENGTH_LONG);
                }

                /**
                 * Sent when AdView.loadAd has succeeded
                 */
                public void onAdLoaded() {
                    interstitial.show();
                }

                /**
                 * Sent when loadAd has failed,
                 * typically because of network failure, an application configuration error, or a lack of ad inventory.
                 */
                public void onAdFailedToLoad(int errorCode) {
                    //Tools.showToast(getApplicationContext(), "onFailedToReceiveAd Code:" + arg1, Toast.LENGTH_LONG);
                }
            });
            interstitial.loadAd(adRequest);
		}

		// Initialisation des variables applicatives
		Tools.initVars(this);

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		// Tout d'abord, il faut supprimer tous les elements du layout principal
		LinearLayout layoutOfDynamicContent = (LinearLayout) findViewById(R.id.LinearLayout);
		layoutOfDynamicContent.removeAllViewsInLayout();

		MyApplication myApp = (MyApplication) getApplicationContext();
		Dictionary dictionary = myApp.getDictionary();

		ArrayList<Level> levels = dictionary.getLevels();

		float prevLevelPercent = 100.0f;
		float actualLevelPercent = 0.0f;

		for (int i = 0; i < levels.size(); i++) {

			Level level = levels.get(i);

			Button bt = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 10, 10, 10);
			bt.setLayoutParams(params);

			// Couleur du bouton
			if (prevLevelPercent < 75.0f) {
				bt.setBackgroundResource(R.drawable.grey_shape);
			}
			else {
				if (((MyApplication) getApplicationContext()).getCompleteProverbNumberOfLevel(level.getId()) == level.getProverbs().size()) {
					bt.setBackgroundResource(R.drawable.green_shape);
				}
				else {
					bt.setBackgroundResource(R.drawable.red_shape);
				}
			}

			actualLevelPercent = ((((MyApplication) getApplicationContext()).getCompleteProverbNumberOfLevel(level.getId()) * 1.00f) * 100.00f) / (level.getProverbs().size() * 1.00f);

			if (prevLevelPercent < 75.0f) {
				// Niveau Verrouillé
				bt.setText(getResources().getString(R.string.Niveau) + " " + level.getId() + "\n--- Verrouillé ---");
				bt.setId(Integer.parseInt(level.getId()));
				bt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (checkToast != null)
							checkToast.cancel();
						checkToast = Toast.makeText(getApplicationContext(), "Pour déverrouiller le niveau, vous devez finir le précédent à 75%", Toast.LENGTH_LONG);
						checkToast.show();
					}
				});
			}
			else {
				// Niveau Déverrouillé
				bt.setText(getResources().getString(R.string.Niveau) + " " + level.getId() + " - " + ((MyApplication) getApplicationContext()).getCompleteProverbNumberOfLevel(level.getId()) + "/"
						+ level.getProverbs().size() + "\n--- Effectué à " + actualLevelPercent + "% ---");
				bt.setId(Integer.parseInt(level.getId()));
				bt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Reinitialisation du scroll des proverbes
						MyApplication myApp = (MyApplication) getApplicationContext();
						myApp.setProverbLastScroll(0);

						Intent intent = new Intent(LevelActivity.this, ProverbActivity.class);
						intent.putExtra(LEVEL_ID, "" + view.getId());
						startActivity(intent);
						overridePendingTransition(R.anim.fadeout, R.anim.fadein);
					}
				});
			}

			layoutOfDynamicContent.addView(bt);

			prevLevelPercent = actualLevelPercent;
		}

		// Ajout de la PUB
		Tools.setupAds(this, adView);
	}

	@Override
	public void onDestroy() {
		if (adView != null)
			adView.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// INFLATE THE MENU; THIS ADDS ITEMS TO THE ACTION BAR IF IT IS PRESENT.
		getMenuInflater().inflate(R.menu.level, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(LevelActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(LevelActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fadeout, R.anim.fadein);
	}
}
