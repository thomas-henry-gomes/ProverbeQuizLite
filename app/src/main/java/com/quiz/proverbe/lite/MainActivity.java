package com.quiz.proverbe.lite;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// findViewById(R.id.LinearLayout).getRootView().setBackgroundColor(0xFF000033);

		// Initialisation des variables applicatives
		Tools.initVars(this);

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		// Tout d'abord, il faut supprimer tous les elements du layout principal
		LinearLayout layoutOfDynamicContent = (LinearLayout) findViewById(R.id.LinearLayout);
		layoutOfDynamicContent.removeAllViewsInLayout();

		// Libellé Le jeu
		TextView social = new TextView(getApplicationContext());
		social.setText("Le jeu");
		social.setGravity(Gravity.CENTER_HORIZONTAL);
		social.setTextSize(25);
		social.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(social);

		// Bouton jouer
		Button bt1 = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params1.setMargins(10, 10, 10, 10);
		bt1.setLayoutParams(params1);
		// Couleur du bouton
		bt1.setBackgroundResource(R.drawable.blue_shape);
		bt1.setText(getResources().getString(R.string.Jouer));
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, LevelActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		});
		layoutOfDynamicContent.addView(bt1);

		// Bouton comment jouer ?
		Button bt3 = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		LayoutParams params3 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params3.setMargins(10, 10, 10, 10);
		bt3.setLayoutParams(params3);
		// Couleur du bouton
		bt3.setBackgroundResource(R.drawable.blue_shape);
		bt3.setText("Comment jouer ?");
		bt3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());

				adb.setMessage("Comment jouer ?\n\nAprès avoir choisi un proverbe dans les différents niveaux, vous devez trouver le mot manquant (indiqué par [...]) pour le compléter.\n\nLors du contrôle de saisie, les majuscules et les accents ne sont pas pris en compte. Vous avez également droit à une erreur dite \"de frappe\".\n\nUne fois un proverbe complété, il vous est possible de cliquer sur celui-ci pour avoir plus d'informations.");
				adb.setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(MainActivity.this, LevelActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.fadeout, R.anim.fadein);
					}
				});
				adb.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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
		layoutOfDynamicContent.addView(bt3);

		// Libellé Autre
		TextView autre = new TextView(getApplicationContext());
		autre.setText("\nAutre");
		autre.setGravity(Gravity.CENTER_HORIZONTAL);
		autre.setTextSize(25);
		autre.setTextColor(0xFF708090);
		layoutOfDynamicContent.addView(autre);

		// Bouton Paramètres
		Button bt2 = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params2.setMargins(10, 10, 10, 10);
		bt2.setLayoutParams(params2);
		// Couleur du bouton
		bt2.setBackgroundResource(R.drawable.blue_shape);
		bt2.setText(getResources().getString(R.string.Options));
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		});
		layoutOfDynamicContent.addView(bt2);

		// Bouton Noter
		Button bt4 = (Button) getLayoutInflater().inflate(R.layout.template_button, null);
		LayoutParams params4 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params4.setMargins(10, 10, 10, 10);
		bt4.setLayoutParams(params4);
		// Couleur du bouton
		bt4.setBackgroundResource(R.drawable.blue_shape);
		bt4.setText("Noter l'application");
		bt4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String url = "https://play.google.com/store/apps/details?id=com.quiz.proverbe.lite#?t=W251bGwsMSwxLDIxMiwiY29tLnF1aXoucHJvdmVyYmUubGl0ZSJd";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
				overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			}
		});
		layoutOfDynamicContent.addView(bt4);

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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	long time = 0;
	Toast quit = null;

	@Override
	public void onBackPressed() {
		// On quitte l'application si double click
		if (Calendar.getInstance().getTimeInMillis() - time > 3000) {
			quit = Toast.makeText(getApplicationContext(), "Appuyer encore sur RETOUR pour quitter", Toast.LENGTH_SHORT);
			quit.show();
			time = Calendar.getInstance().getTimeInMillis();
		}
		else {
			quit.cancel();
			moveTaskToBack(true);
		}
	}
}
