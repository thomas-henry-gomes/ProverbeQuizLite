package com.quiz.proverbe.lite.dictionary;

import java.util.ArrayList;

public class Dictionary {
	// Children
	private ArrayList<Level> levels = null;

	// Methods
	public Dictionary() {
		super();
		levels = new ArrayList<Level>();
	}

	/**
	 * @return the levels
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}

	/**
	 * @return the level with the given ID
	 * @param levelId
	 *            The ID for the level
	 */
	public Level getLevel(String levelId) {
		Level returnLevel = null;
		for (int i = 0; i < levels.size(); i++) {
			if (levels.get(i).getId().equals(levelId)) {
				returnLevel = levels.get(i);
				break;
			}
		}
		return returnLevel;
	}

	/**
	 * @param levels
	 *            the levels to set
	 */
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}

	/**
	 * @param level
	 *            the level to add
	 */
	public void addLevel(Level level) {
		levels.add(level);
	}

	/**
	 * Return the number of all the proverbs of the dictionary
	 */
	public int getProverbsNumber() {
		int number = 0;
		for (int i = 0; i < levels.size(); i++) {
			number += levels.get(i).getProverbs().size();
		}
		return number;
	}

	/**
	 * Return the number of all the levels of the dictionary
	 */
	public int getLevelsNumber() {
		return levels.size();
	}
}
