package com.quiz.proverbe.lite.dictionary;

public class Proverb {
	// Attributes
	private String id = "";

	// Children
	private String partiel = "";
	private String complement = "";
	private String aide = "";

	// Methods
	public Proverb(String id, String partiel, String complement, String aide) {
		super();
		this.id = id;
		this.partiel = partiel;
		this.complement = complement;
		this.aide = aide;
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
	 * @return the partiel
	 */
	public String getPartiel() {
		return partiel;
	}

	/**
	 * @param partiel
	 *            the partiel to set
	 */
	public void setPartiel(String partiel) {
		this.partiel = partiel;
	}

	/**
	 * @return the complement
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * @param complement
	 *            the complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @return the aide
	 */
	public String getAide() {
		return aide;
	}

	/**
	 * @param aide
	 *            the aide to set
	 */
	public void setAide(String aide) {
		this.aide = aide;
	}

}
