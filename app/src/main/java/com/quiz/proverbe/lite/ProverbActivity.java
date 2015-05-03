package com.quiz.proverbe.lite;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.*;
import com.quiz.proverbe.lite.dictionary.Dictionary;
import com.quiz.proverbe.lite.dictionary.Level;
import com.quiz.proverbe.lite.dictionary.Proverb;

public class ProverbActivity extends ActionBarActivity {
	private AdView adView;

	final String LEVEL_ID = "level_id";
	final String PROVERB_ID = "proverb_id";

	final String INTERSTITIAL_ADS = "interstitial_ads";
	String interstitialAds = "";

	String levelId = "";
	Toast checkToast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proverb);

		// Initialisation des variables applicatives
		if (Tools.initVars(this) == -1) {
			Intent intent = new Intent(ProverbActivity.this, LevelActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
		}

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		Intent intent = getIntent();
		if (intent != null) {
			levelId = intent.getStringExtra(LEVEL_ID);

			// TOUT D'ABORD, IL FAUT SUPPRIMER TOUS LES ELEMENTS DU LAYOUT PRINCIPAL
			LinearLayout layoutOfDynamicContent = (LinearLayout) findViewById(R.id.LinearLayout);
			layoutOfDynamicContent.removeAllViewsInLayout();

			MyApplication myApp = (MyApplication) getApplicationContext();
			Dictionary dictionary = myApp.getDictionary();

			ArrayList<Level> levels = dictionary.getLevels();
			for (int i = 0; i < levels.size(); i++) {
				Level level = levels.get(i);

				if (level.getId().equals(levelId)) {
					ArrayList<Proverb> proverbs = level.getProverbs();

					for (int j = 0; j < proverbs.size(); j++) {
						Proverb proverb = proverbs.get(j);

						Button bt = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
						LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						params.setMargins(10, 10, 10, 10);
						bt.setLayoutParams(params);

						String key = "";
						if (levelId.length() == 1)
							key = key + "0" + levelId;
						else
							key = key + levelId;
						if (proverb.getId().length() == 1)
							key = key + "0" + proverb.getId();
						else
							key = key + proverb.getId();

						// Si proberbe trouvé
						if (myApp.getUserData().containsKey(key)) {
							bt.setText(proverb.getPartiel().replace("[...]", proverb.getComplement()));
							bt.setId(Integer.parseInt(proverb.getId()));
							bt.setTag(key);
							bt.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// Message de fonctionnalité non présente dans cette version
									AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());

									String message = "Vous ne pouvez pas obtenir d'informations sur ce proverbe.\n";
									message += "Cette fonctionnalité n'est pas disponible dans cette version.\n\n";
									message += "Voulez vous télécharger la version complète ?";
									adb.setMessage(message);

									adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											String url = "https://play.google.com/store/apps/details?id=com.quiz.proverbe";
											Intent intent = new Intent(Intent.ACTION_VIEW);
											intent.setData(Uri.parse(url));
											startActivity(intent);
											overridePendingTransition(R.anim.fadeout, R.anim.fadein);
										}
									});
									adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {

										}
									});
									AlertDialog ad = adb.show();
									TextView messageText = (TextView) ad.findViewById(android.R.id.message);
									messageText.setPadding(15, 15, 15, 15);
									messageText.setGravity(Gravity.CENTER);
									messageText.setTextColor(Color.BLACK);
									messageText.setTextSize(18);
									ad.show();
								}
							});
							bt.setBackgroundResource(R.drawable.green_shape);
						}
						// Sinon
						else {
							bt.setText(proverb.getPartiel());
							bt.setId(Integer.parseInt(proverb.getId()));
							bt.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
									// Sauvegarde de la position du scroll
									MyApplication myApp = (MyApplication) getApplicationContext();
									ScrollView sv = (ScrollView) findViewById(R.id.ScrollView);
									myApp.setProverbLastScroll(sv.getScrollY());

									Intent intent = new Intent(ProverbActivity.this, ResponseActivity.class);
									intent.putExtra(LEVEL_ID, levelId);
									intent.putExtra(PROVERB_ID, "" + view.getId());
									startActivity(intent);
									overridePendingTransition(R.anim.fadeout, R.anim.fadein);
								}
							});
							bt.setBackgroundResource(R.drawable.red_shape);
						}

						layoutOfDynamicContent.addView(bt);

					}
					break;
				}
			}

			// Scroll si nécessaire
			ScrollView sv = (ScrollView) findViewById(R.id.ScrollView);
			sv.post(new Runnable() {
				public void run() {
					MyApplication myApp = (MyApplication) getApplicationContext();
					ScrollView sv = (ScrollView) findViewById(R.id.ScrollView);
					sv.scrollTo(0, myApp.getProverbLastScroll());
				}
			});

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proverb, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(ProverbActivity.this, LevelActivity.class);
			intent.putExtra(INTERSTITIAL_ADS, "true");
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(ProverbActivity.this, LevelActivity.class);
		intent.putExtra(INTERSTITIAL_ADS, "true");
		startActivity(intent);
		overridePendingTransition(R.anim.fadeout, R.anim.fadein);
	}
}
