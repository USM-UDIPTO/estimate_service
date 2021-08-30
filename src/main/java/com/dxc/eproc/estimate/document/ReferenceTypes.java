package com.dxc.eproc.estimate.document;

// TODO: Auto-generated Javadoc
/**
 * The Enum ReferenceTypes.
 */
public enum ReferenceTypes {

	/** The administrative approval. */
	ADMINSANCTION_PROCEEDINGS("Adminsanction proceedings"),

	/** The technical approval. */
	TECHSANCTION_PROCEEDINGS("Techsanction proceedings"),

	/** The indent works. */
	INDENT_WORKS("Indent works");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new reference types.
	 *
	 * @param value the value
	 */
	ReferenceTypes(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
