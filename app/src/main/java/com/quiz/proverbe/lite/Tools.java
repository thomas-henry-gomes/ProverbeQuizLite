package com.quiz.proverbe.lite;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.google.android.gms.ads.*;
import android.support.v7.app.ActionBar;
import com.quiz.proverbe.lite.dictionary.Dictionary;

import java.util.HashMap;

public class Tools {

	/**
	 * Initialize applicatives variables
	 * 
	 * @param activity
	 *            The activity
	 * @return 0 if nothing is doing
	 *         -1 else
	 */
	public static int initVars(Activity activity) {
		int returnValue = 0;
		MyApplication myApp = (MyApplication) activity.getApplicationContext();

		// Init the dictionary
		if (myApp.getDictionary() == null) {
			myApp.setDictionary(new Dictionary());
			returnValue = -1;
		}

		if (myApp.getDictionary().getLevels().size() == 0) {
			myApp.initDictionary();
			returnValue = -1;
		}

		// Init the data file
		if (myApp.getUserData() == null) {
			myApp.setUserData(new HashMap<String, Long>());
			returnValue = -1;
		}

		if (myApp.getUserData().isEmpty()) {
			myApp.initUserDataFile();
		}

		return returnValue;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 * 
	 * @param activity
	 *            The activity
	 * 
	 * @param actionBar
	 *            The actionBar to set up
	 */
	public static void setupActionBar(Activity activity, ActionBar actionBar) {
		// On désactive le bouton home de l'activité principale
		if (activity.getClass().equals(MainActivity.class)) {
			actionBar.setDisplayHomeAsUpEnabled(false);
			// actionBar.setDisplayShowHomeEnabled(false);
			// actionBar.hide();
		}

		// On initialise les sous-titres
		if (activity.getClass().equals(LevelActivity.class)) {
			actionBar.setSubtitle("Choisissez un niveau");
		}
		else if (activity.getClass().equals(ProverbActivity.class)) {
			actionBar.setSubtitle("Choisissez un proverbe à compléter");
		}
		else if (activity.getClass().equals(ResponseActivity.class)) {
			actionBar.setSubtitle("Entrer le mot manquant pour compléter le proverbe");
		}
		else if (activity.getClass().equals(SettingsActivity.class)) {
			actionBar.setSubtitle("Choisissez un paramètre");
		}
		else if (activity.getClass().equals(StatisticActivity.class)) {
			actionBar.setSubtitle("Statistiques utilisateur");
		}

	}

	/**
	 * Set up the Ads.
	 * 
	 * @param activity
	 *            The activity
	 * 
	 * @param adView
	 *            The adView
	 */
	public static void setupAds(Activity activity, AdView adView) {
		LinearLayout layoutMain = (LinearLayout) activity.findViewById(R.id.LinearLayoutAds);

		adView = new AdView(activity);
        adView.setAdUnitId("ca-app-pub-3881234064367598/2026539460");
        adView.setAdSize(AdSize.BANNER);
		//adView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

		layoutMain.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("01ad0cf6cc11d80e").build(); // Pour les tests
        adView.loadAd(adRequest);
	}

	/**
	 * Return the Interstitial Ads Id
	 * 
	 * @return The interstitial Ads Id
	 */
	public static String getInterstitialAdsId() {
		return "ca-app-pub-3881234064367598/5197855060";
	}
}