package com.quiz.proverbe.lite;

import java.text.Normalizer;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class ResponseActivity extends ActionBarActivity {

	final String LEVEL_ID = "level_id";
	final String PROVERB_ID = "proverb_id";

	String levelId = "";
	String proverbId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response);

		// Initialisation des variables applicatives
		if (Tools.initVars(this) == -1) {
			Intent intent = new Intent(ResponseActivity.this, LevelActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
		}

		// Set up the action bar
		Tools.setupActionBar(this, getSupportActionBar());

		MyApplication myApp = (MyApplication) getApplicationContext();

		Intent intent = getIntent();
		if (intent != null) {
			levelId = intent.getStringExtra(LEVEL_ID);
			proverbId = intent.getStringExtra(PROVERB_ID);
		}

		// Affichage du proverbe partiel
		TextView tv = (TextView) findViewById(R.id.textView);
		tv.setTextSize(1, 20);
		tv.setGravity(Gravity.CENTER);
		tv.setText(myApp.getDictionary().getLevel(levelId).getProverb(proverbId).getPartiel());

		// Mise en place de l'aide
		String aide = myApp.getDictionary().getLevel(levelId).getProverb(proverbId).getAide();

        // Aide #1
		if ("".equals(aide)) {
			TextView tva = (TextView) findViewById(R.id.textViewAide1);
			tva.setTextSize(1, 12);
			tva.setGravity(Gravity.CENTER);
			tva.setText("");
		}
		else {
			TextView tva = (TextView) findViewById(R.id.textViewAide1);
			tva.setTextSize(1, 12);
			tva.setGravity(Gravity.CENTER);
			tva.setText("Besoin d'aide ? Cliquer ici pour obtenir un indice.");
			tva.setTag(aide);
			tva.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Message d'aide
					AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());

					adb.setMessage((String) v.getTag());

					adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
					adb.setCancelable(false);
					AlertDialog ad = adb.show();
					TextView messageText = (TextView) ad.findViewById(android.R.id.message);
					messageText.setPadding(15, 15, 15, 15);
					messageText.setGravity(Gravity.CENTER);
					messageText.setTextColor(Color.BLACK);
					messageText.setTextSize(18);
					ad.show();
				}
			});
		}

        // Aide #2
        TextView tva2 = (TextView) findViewById(R.id.textViewAide2);
        tva2.setTextSize(1, 12);
        tva2.setGravity(Gravity.CENTER);
        tva2.setText("Besoin d'aide ? Cliquer ici pour obtenir la première lettre.");
        tva2.setTag(myApp.getDictionary().getLevel(levelId).getProverb(proverbId).getComplement().substring(0, 1));
        tva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Message d'aide
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());

                adb.setMessage("La première lettre du mot est : \"" + ((String) v.getTag()).toUpperCase(Locale.FRENCH) + "\".");

                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.setCancelable(false);
                AlertDialog ad = adb.show();
                TextView messageText = (TextView) ad.findViewById(android.R.id.message);
                messageText.setPadding(15, 15, 15, 15);
                messageText.setGravity(Gravity.CENTER);
                messageText.setTextColor(Color.BLACK);
                messageText.setTextSize(18);
                ad.show();
            }
        });

        // Aide #3
        TextView tva3 = (TextView) findViewById(R.id.textViewAide3);
        tva3.setTextSize(1, 12);
        tva3.setGravity(Gravity.CENTER);
        tva3.setText("Besoin d'aide ? Cliquer ici pour obtenir le nombre de lettres.");
        tva3.setTag("" + myApp.getDictionary().getLevel(levelId).getProverb(proverbId).getComplement().length());
        tva3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Message d'aide
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());

                adb.setMessage("Le mot possède " + (String) v.getTag() + " lettres.");

                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.setCancelable(false);
                AlertDialog ad = adb.show();
                TextView messageText = (TextView) ad.findViewById(android.R.id.message);
                messageText.setPadding(15, 15, 15, 15);
                messageText.setGravity(Gravity.CENTER);
                messageText.setTextColor(Color.BLACK);
                messageText.setTextSize(18);
                ad.show();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.response, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(ResponseActivity.this, ProverbActivity.class);
			intent.putExtra(LEVEL_ID, "" + levelId);
			startActivity(intent);
			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(ResponseActivity.this, ProverbActivity.class);
		intent.putExtra(LEVEL_ID, "" + levelId);
		startActivity(intent);
		overridePendingTransition(R.anim.fadeout, R.anim.fadein);
	}

	public void clickValider(View v) {
		boolean valid = false;
		int nbError = 0;
		int j = -10;
		int k = -10;
		MyApplication myApp = (MyApplication) getApplicationContext();
		String complement = myApp.getDictionary().getLevel(levelId).getProverb(proverbId).getComplement();
		String saisie = ((EditText) findViewById(R.id.editText)).getText().toString();

		// Suppression des accents et des majuscules
		complement = Normalizer.normalize(complement, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "").toLowerCase(Locale.getDefault());
		saisie = Normalizer.normalize(saisie, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "").toLowerCase(Locale.getDefault());

		if (Math.abs(complement.length() - saisie.length()) <= 1) {
			for (int i = 0; i < Math.min(complement.length(), saisie.length()); i++) {
				if (complement.getBytes()[i] != saisie.getBytes()[i]) {
					if (j == -10) {
						nbError++;
						saisie = saisie.substring(0, i) + saisie.substring(i + 1);
						i--;
						j = i;
					}
					else {
						if ((i == j + 1) && (k == -10)) {
							complement = complement.substring(0, i) + complement.substring(i + 1);
							i--;
							k = i;
						}
						else {
							nbError++;
						}
					}
				}
				if (nbError > 1) {
					break;
				}
			}
			if ((complement.length() != saisie.length()) && (j == -10)) {
				nbError++;
			}
			if (nbError <= 1) {
				valid = true;
			}
		}

		if (valid) {
			myApp.writeUserDataFile(levelId, proverbId);
			myApp.addProverbToUserData(levelId, proverbId);

			// Masquage du clavier
			EditText et = (EditText) findViewById(R.id.editText);
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(et.getWindowToken(), 0);

			// Message de réussite
			AlertDialog.Builder adb = new AlertDialog.Builder(this);

			if (nbError == 0)
				adb.setMessage("Bravo, vous avez trouvé le mot manquant =)");
			else
				adb.setMessage("Vous avez presque trouvé le mot manquant\n Aller, je vous l'accorde ;)");
			adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(ResponseActivity.this, ProverbActivity.class);
					intent.putExtra(LEVEL_ID, "" + levelId);
					startActivity(intent);
					overridePendingTransition(R.anim.fadeout, R.anim.fadein);
				}
			});
			adb.setCancelable(false);
			AlertDialog ad = adb.show();
			TextView messageText = (TextView) ad.findViewById(android.R.id.message);
			messageText.setPadding(15, 15, 15, 15);
			messageText.setGravity(Gravity.CENTER);
			messageText.setTextColor(Color.BLACK);
			messageText.setTextSize(18);
			ad.show();
		}
		else {
			// Message d'erreur
			AlertDialog.Builder adb = new AlertDialog.Builder(this);

			adb.setMessage("Mot incorrect, veuillez réessayer !!!");
			adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			adb.setCancelable(false);
			AlertDialog ad = adb.show();
			TextView messageText = (TextView) ad.findViewById(android.R.id.message);
			messageText.setPadding(15, 15, 15, 15);
			messageText.setGravity(Gravity.CENTER);
			messageText.setTextColor(Color.BLACK);
			messageText.setTextSize(18);
			ad.show();
		}
	}
}
