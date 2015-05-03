/**
 * 
 */
package com.quiz.proverbe.lite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Application;

import com.quiz.proverbe.lite.dictionary.Dictionary;
import com.quiz.proverbe.lite.dictionary.Level;
import com.quiz.proverbe.lite.dictionary.Proverb;

public class MyApplication extends Application {
	// Dictionary: XML File
	private Dictionary dictionary = null;
	// userData: Intern File
	private HashMap<String, Long> userData = null;
	// proverb last scroll
	private int proverbLastScroll = 0;

	public MyApplication() {
		super();
		if (dictionary == null)
			dictionary = new Dictionary();
		if (userData == null)
			userData = new HashMap<String, Long>();
	}

	/**
	 * Read the XML File to init the dictionary
	 */
	public void initDictionary() {
		Level level = null;
		Proverb proverb = null;
		String partiel = "";
		String complement = "";
		String aide = "";

		InputStream is = getResources().openRawResource(R.raw.proverbe);

		// Using factory get an instance of document builder
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// Parse using builder to get dom representation of the xml file
			Document doc = builder.parse(is, null);

			doc.getDocumentElement().normalize();

			NodeList xmlLevels = doc.getElementsByTagName("Niveau");

			for (int i = 0; i < xmlLevels.getLength(); i++) {
				Node xmlLevel = xmlLevels.item(i);
				level = new Level(xmlLevel.getAttributes().getNamedItem("id").getNodeValue());

				NodeList xmlProverbs = xmlLevel.getChildNodes();

				for (int j = 0; j < xmlProverbs.getLength(); j++) {

					// Process only proverbs (not the text in)
					if (xmlProverbs.item(j).getNodeType() == Node.ELEMENT_NODE) {
						NodeList xmlProverbInfos = xmlProverbs.item(j).getChildNodes();

						partiel = "";
						complement = "";
						aide = "";
						for (int k = 0; k < xmlProverbInfos.getLength(); k++) {

							// Process only proverbInfos (not the text in)
							if (xmlProverbInfos.item(k).getNodeType() == Node.ELEMENT_NODE) {
								if ("partiel".equals(xmlProverbInfos.item(k).getNodeName())) {
									partiel = xmlProverbInfos.item(k).getTextContent();
								}
								else if ("complement".equals(xmlProverbInfos.item(k).getNodeName())) {
									complement = xmlProverbInfos.item(k).getTextContent();
								}
								else if ("aide".equals(xmlProverbInfos.item(k).getNodeName())) {
									aide = xmlProverbInfos.item(k).getTextContent();
								}
							}

						}

						proverb = new Proverb(xmlProverbs.item(j).getAttributes().getNamedItem("id").getNodeValue(), partiel, complement, aide);
						level.addProverb(proverb);
					}
				}
				dictionary.addLevel(level);
			}

			is.close();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary
	 *            the dictionary to set
	 */
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	/* FILE MANAGEMENT */

	// Les différents modes d'ouverture d'un fichier sont:
	// MODE_PRIVATE: permet de créer un fichier ou de le remplacer s'il existe déjà et surtout rend le fichier privé à votre application.
	// MODE_APPEND: possède le même principe de fonctionnement que MODE_PRIVATE mais offre la possibilité d'écrire à la fin du fichier plutôt que de l'écraser.
	// MODE_WORLD_READABLE: Offre la possibilité à toutes les autres applications de lire ce fichier.
	// MODE_WORLD_WRITEABLE: Offre la possibilité à toutes les autres applications d'éditer ce fichier.

	String FILENAME = "proverbe_data_file";

	/**
	 * @return the userData
	 */
	public HashMap<String, Long> getUserData() {
		return userData;
	}

	/**
	 * @param userData
	 *            the userData to set
	 */
	public void setUserData(HashMap<String, Long> userData) {
		this.userData = userData;
	}

	/**
	 * Initialize the user data file
	 */
	public void initUserDataFile() {
		// Chargement du fichier utilisateur en mémoire
		try {
			FileInputStream in = openFileInput(FILENAME);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				userData.put(line.substring(0, 4), Long.valueOf(line.substring(4)));
			}

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a proverb to the user data
	 * 
	 * @param levelId
	 *            Level ID of the finding proverb
	 * @param proverbId
	 *            ID of the finding proverb
	 */
	public void addProverbToUserData(String levelId, String proverbId) {
		if (levelId.length() == 1)
			levelId = "0" + levelId;

		if (proverbId.length() == 1)
			proverbId = "0" + proverbId;

		Long date = System.currentTimeMillis();

		userData.put(levelId + proverbId, date);
	}

	/**
	 * Clear the user data file
	 */
	public void clearUserDataFile() {
		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, MODE_PRIVATE);
			fos.close();

			userData = new HashMap<String, Long>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write into the user data file
	 * 
	 * @param levelId
	 *            Level ID of the finding proverb
	 * @param proverbId
	 *            ID of the finding proverb
	 */
	public void writeUserDataFile(String levelId, String proverbId) {
		try {
			FileOutputStream out = openFileOutput(FILENAME, MODE_APPEND);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));

			if (levelId.length() == 1)
				levelId = "0" + levelId;

			if (proverbId.length() == 1)
				proverbId = "0" + proverbId;

			Long date = System.currentTimeMillis();

			bufferedWriter.write(levelId + proverbId + date.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();

			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the number of completed proverb for the given level
	 * 
	 * @param levelId
	 *            The level
	 */
	public int getCompleteProverbNumberOfLevel(String levelId) {
		if (levelId.length() == 1)
			levelId = "0" + levelId;

		int result = 0;

		for (Entry<String, Long> entry : userData.entrySet()) {
			if (((String) entry.getKey()).substring(0, 2).equals(levelId))
				result++;
		}

		return result;
	}

	/**
	 * Indicate if the proverb is complete
	 * 
	 * @param levelId
	 *            Level ID of the proverb
	 * @param proverbId
	 *            ID of the proverb
	 */
	public boolean isProverbComplete(String levelId, String proverbId) {
		if (levelId.length() == 1)
			levelId = "0" + levelId;

		if (proverbId.length() == 1)
			proverbId = "0" + proverbId;

		return userData.containsKey(levelId + proverbId);
	}

	/**
	 * @return the proverbLastScroll
	 */
	public int getProverbLastScroll() {
		return proverbLastScroll;
	}

	/**
	 * @param proverbLastScroll
	 *            the proverbLastScroll to set
	 */
	public void setProverbLastScroll(int proverbLastScroll) {
		this.proverbLastScroll = proverbLastScroll;
	}

	/**
	 * Return the number of completed proverbs
	 */
	public int getProverbsCompleteNumber() {
		int number = 0;
		for (int i = 0; i < dictionary.getLevels().size(); i++) {
			for (int j = 0; j < dictionary.getLevels().get(i).getProverbs().size(); j++) {
				if (isProverbComplete(dictionary.getLevels().get(i).getId(), dictionary.getLevels().get(i).getProverbs().get(j).getId()))
					number++;
			}
		}

		return number;
	}

	/**
	 * Return the date of the first completed proverb
	 */
	public String getFirstProverbDate() {
		Long longDate = null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.FRENCH);

		for (Entry<String, Long> entry : userData.entrySet()) {
			if (longDate == null) {
				longDate = entry.getValue();
			}
			else if (entry.getValue() < longDate) {
				longDate = entry.getValue();
			}
		}

		if (longDate == null)
			return "-";
		else
			return dateFormat.format(new Date(longDate));

	}

	/**
	 * Return the date of the last completed proverb
	 */
	public String getLastProverbDate() {
		Long longDate = null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.FRENCH);

		for (Entry<String, Long> entry : userData.entrySet()) {
			if (longDate == null) {
				longDate = entry.getValue();
			}
			else if (entry.getValue() > longDate) {
				longDate = entry.getValue();
			}
		}

		if (longDate == null)
			return "-";
		else
			return dateFormat.format(new Date(longDate));

	}

	/**
	 * Return the number of the totally completed levels
	 */
	public int getTotallyCompletedLevelsNumber() {
		int number = 0;
		boolean tmp = true;

		for (int i = 0; i < dictionary.getLevels().size(); i++) {
			tmp = true;
			for (int j = 0; j < dictionary.getLevels().get(i).getProverbs().size(); j++) {
				if (!isProverbComplete(dictionary.getLevels().get(i).getId(), dictionary.getLevels().get(i).getProverbs().get(j).getId())) {
					tmp = false;
					break;
				}
			}
			if (tmp)
				number++;
		}

		return number;
	}

	/**
	 * Return the number of the non begin levels
	 */
	public int getNonBeginLevelsNumber() {
		int number = 0;
		boolean tmp = true;

		for (int i = 0; i < dictionary.getLevels().size(); i++) {
			tmp = true;
			for (int j = 0; j < dictionary.getLevels().get(i).getProverbs().size(); j++) {
				if (isProverbComplete(dictionary.getLevels().get(i).getId(), dictionary.getLevels().get(i).getProverbs().get(j).getId())) {
					tmp = false;
					break;
				}
			}
			if (tmp)
				number++;
		}

		return number;
	}
}
