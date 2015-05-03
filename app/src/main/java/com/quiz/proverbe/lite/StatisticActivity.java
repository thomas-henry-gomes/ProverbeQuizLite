package com.quiz.proverbe.lite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;


public class StatisticActivity extends ActionBarActivity {
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);

		// Initialisation des variables applicatives
		Tools.initVars(this);

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		// Tout d'abord, il faut supprimer tous les elements du layout principal
		LinearLayout layoutOfDynamicContent = (LinearLayout) findViewById(R.id.LinearLayout);
		layoutOfDynamicContent.removeAllViewsInLayout();

		MyApplication myApp = (MyApplication) getApplicationContext();

		// Libellé Niveaux
		TextView niveaux = new TextView(getApplicationContext());
		niveaux.setText("Niveaux");
		niveaux.setGravity(Gravity.CENTER_HORIZONTAL);
		niveaux.setTextSize(25);
		niveaux.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(niveaux);

		// Statistiques niveaux
		LinearLayout lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		TextView tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Totalement complétés</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		TextView tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText(myApp.getTotallyCompletedLevelsNumber() + "/" + myApp.getDictionary().getLevelsNumber() + " - "
				+ (((myApp.getTotallyCompletedLevelsNumber() * 1.00f) * 100.00f) / (myApp.getDictionary().getLevelsNumber() * 1.00f)) + "%");
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);
		// ------------------------------------------------------------------------------------------------------------
		lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Partiellement complétés</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText((myApp.getDictionary().getLevelsNumber() - myApp.getTotallyCompletedLevelsNumber() - myApp.getNonBeginLevelsNumber())
				+ "/"
				+ myApp.getDictionary().getLevelsNumber()
				+ " - "
				+ ((((myApp.getDictionary().getLevelsNumber() - myApp.getTotallyCompletedLevelsNumber() - myApp.getNonBeginLevelsNumber()) * 1.00f) * 100.00f) / (myApp.getDictionary()
						.getLevelsNumber() * 1.00f)) + "%");
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);
		// ------------------------------------------------------------------------------------------------------------
		lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Non commencés</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText(myApp.getNonBeginLevelsNumber() + "/" + myApp.getDictionary().getLevelsNumber() + " - "
				+ (((myApp.getNonBeginLevelsNumber() * 1.00f) * 100.00f) / (myApp.getDictionary().getLevelsNumber() * 1.00f)) + "%");
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);

		// Libellé Général
		TextView general = new TextView(getApplicationContext());
		general.setText("\nGénéral");
		general.setGravity(Gravity.CENTER_HORIZONTAL);
		general.setTextSize(25);
		general.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(general);

		// Statistiques générales
		lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Proverbes trouvés</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText(myApp.getProverbsCompleteNumber() + "/" + myApp.getDictionary().getProverbsNumber() + " - "
				+ (((myApp.getProverbsCompleteNumber() * 1.00f) * 100.00f) / (myApp.getDictionary().getProverbsNumber() * 1.00f)) + "%");
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);
		// ------------------------------------------------------------------------------------------------------------
		lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Premier proverbe trouvé</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText(myApp.getFirstProverbDate());
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);
		// ------------------------------------------------------------------------------------------------------------
		lyh = new LinearLayout(getApplicationContext());
		lyh.setOrientation(LinearLayout.HORIZONTAL);
		tv1 = new TextView(getApplicationContext());
		tv1.setGravity(Gravity.LEFT);
		tv1.setTextSize(17);
		tv1.setTextColor(0xFF708090);
		tv1.setText(Html.fromHtml("<b>Dernier proverbe trouvé</b>"));
		tv1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		lyh.addView(tv1);
		tv2 = new TextView(getApplicationContext());
		tv2.setGravity(Gravity.LEFT);
		tv2.setTextSize(17);
		tv2.setTextColor(0xFF708090);
		tv2.setText(myApp.getLastProverbDate());
		tv2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2));
		lyh.addView(tv2);
		layoutOfDynamicContent.addView(lyh);

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(StatisticActivity.this, SettingsActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(StatisticActivity.this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fadeout, R.anim.fadein);
	}

}
