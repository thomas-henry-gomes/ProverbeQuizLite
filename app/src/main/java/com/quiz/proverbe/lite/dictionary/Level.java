package com.quiz.proverbe.lite.dictionary;

import java.util.ArrayList;

public class Level {
	// Attributes
	private String id = "";

	// Children
	private ArrayList<Proverb> proverbs = null;

	// Methods
	public Level(String id) {
		super();
		this.id = id;
		proverbs = new ArrayList<Proverb>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the proverbs
	 */
	public ArrayList<Proverb> getProverbs() {
		return proverbs;
	}

	/**
	 * @return the proverb with the given ID
	 * @param proverbId
	 *            The ID for the proverb
	 */
	public Proverb getProverb(String proverbId) {
		Proverb returnProverb = null;
		for (int i = 0; i < proverbs.size(); i++) {
			if (proverbs.get(i).getId().equals(proverbId)) {
				returnProverb = proverbs.get(i);
				break;
			}
		}
		return returnProverb;
	}

	/**
	 * @param proverbs
	 *            the proverbs to set
	 */
	public void setProverbs(ArrayList<Proverb> proverbs) {
		this.proverbs = proverbs;
	}

	/**
	 * @param proverb
	 *            the proverb to add
	 */
	public void addProverb(Proverb proverb) {
		proverbs.add(proverb);
	}
}
