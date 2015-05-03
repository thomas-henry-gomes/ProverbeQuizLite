package com.quiz.proverbe.lite;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.*;


public class SettingsActivity extends ActionBarActivity {
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// Initialisation des variables applicatives
		Tools.initVars(this);

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		// Tout d'abord, il faut supprimer tous les elements du layout principal
		LinearLayout layoutOfDynamicContent = (LinearLayout) findViewById(R.id.LinearLayout);
		layoutOfDynamicContent.removeAllViewsInLayout();

		LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params1.setMargins(10, 10, 10, 10);

		// Libellé Social
		TextView social = new TextView(getApplicationContext());
		social.setText("Social");
		social.setGravity(Gravity.CENTER_HORIZONTAL);
		social.setTextSize(25);
		social.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(social);

		// Bouton Statistiques
		Button btStat = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		btStat.setLayoutParams(params1);
		// Couleur du bouton
		btStat.setBackgroundResource(R.drawable.blue_shape);
		btStat.setText("Statistiques");
		btStat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SettingsActivity.this, StatisticActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		});
		layoutOfDynamicContent.addView(btStat);

		// Bouton Facebook
		Button btFB = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		btFB.setLayoutParams(params1);
		// Couleur du bouton
		btFB.setBackgroundResource(R.drawable.blue_shape);
		btFB.setText("Aime la page Facebook");
		btFB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String url = "http://www.facebook.com/pages/Proverbe-quiz/305099979617529";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		});
		layoutOfDynamicContent.addView(btFB);

		// Bouton Partager
		Button bt4 = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		bt4.setLayoutParams(params1);
		// Couleur du bouton
		bt4.setBackgroundResource(R.drawable.blue_shape);
		bt4.setText("Partager");
		bt4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.quiz.proverbe.lite#?t=W251bGwsMSwxLDIxMiwiY29tLnF1aXoucHJvdmVyYmUubGl0ZSJd");
				view.getContext().startActivity(Intent.createChooser(i, "Partager avec ..."));
			}
		});
		layoutOfDynamicContent.addView(bt4);

		// Libellé Autre
		TextView autre = new TextView(getApplicationContext());
		autre.setText("\nAutre");
		autre.setGravity(Gravity.CENTER_HORIZONTAL);
		autre.setTextSize(25);
		autre.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(autre);

		// Bouton reinitialiser
		Button btInitData = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		btInitData.setLayoutParams(params1);
		// Couleur du bouton
		btInitData.setBackgroundResource(R.drawable.blue_shape);
		btInitData.setText(getResources().getString(R.string.initData));
		btInitData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				initData();
			}
		});
		layoutOfDynamicContent.addView(btInitData);

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
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void initData() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);

		TextView title = new TextView(this);
		title.setText("--- ATTENTION ---");
		title.setPadding(15, 15, 15, 15);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.BLACK);
		title.setTextSize(19);
		adb.setCustomTitle(title);

		adb.setMessage("Voulez-vous réinitialiser les données ?\nCette action est irréversible : vous perdrez toutes vos données.");
		adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				((MyApplication) getApplicationContext()).clearUserDataFile();
				Toast.makeText(getApplicationContext(), "Données utilisateur effacées avec succès", Toast.LENGTH_LONG).show();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fadeout, R.anim.fadein);
	}

}
