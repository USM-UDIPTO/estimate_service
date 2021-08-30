package com.dxc.eproc.estimate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class SeriesTable.
 */
@Entity
@Table(name = "series_table")
public class SeriesTable {

	/** The name. */
	private String name;

	/** The prefix. */
	private String prefix;

	/** The next series. */
	private int nextSeries;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Id
	@Column(name = "name")
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the next series.
	 *
	 * @return the next series
	 */
	@Column(name = "next_series")
	public int getNextSeries() {
		return nextSeries;
	}

	/**
	 * Sets the next series.
	 *
	 * @param nextSeries the new next series
	 */
	public void setNextSeries(int nextSeries) {
		this.nextSeries = nextSeries;
	}

	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	@Column(name = "prefix")
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix.
	 *
	 * @param prefix the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
