package com.dxc.eproc.estimate.config;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.dxc.eproc.estimate.model.SeriesTable;

/**
 * The Class SeriesGenerator.
 */
public final class SeriesGenerator {

	/**
	 * Instantiates a new series generator.
	 */
	private SeriesGenerator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Gets the series.
	 *
	 * @param em         the em
	 * @param seriesName the series name
	 * @return the series
	 */
	public static String getSeries(EntityManager em, String seriesName) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM series_table WHERE name = '").append(seriesName).append("' FOR UPDATE");
		Query query = em.createNativeQuery(sql.toString(), SeriesTable.class);
		SeriesTable series = (SeriesTable) query.getSingleResult();
		String value = null;
		if (series.getPrefix() != null) {
			value = series.getPrefix();
		}
		value += series.getNextSeries();
		series.setNextSeries(series.getNextSeries() + 1);

		return value;
	}

	public static String getIndentSeries(EntityManager em, String seriesName) {

		String querySql = "SELECT * FROM series_table WHERE name = '" + seriesName + "' FOR UPDATE";
		Query query = em.createNativeQuery(querySql, SeriesTable.class);
		List<SeriesTable> list = query.getResultList();
		SeriesTable series = null;
		if (!list.isEmpty()) {

			if (list.iterator().hasNext()) {
				series = list.iterator().next();
			}

			int nextSeries = 0;
			if (series != null) {
				nextSeries = series.getNextSeries();
			}
			String value = String.valueOf(nextSeries);
			series.setNextSeries(nextSeries + 1);
			System.out.println(seriesName + " ---------------- Series " + value);
			return value;

		} else {
			return getIndentSeriesOld(em, seriesName);
		}
	}

	private static String getIndentSeriesOld(EntityManager em, String seriesName) {

		StringBuilder sql = new StringBuilder();
		StringBuilder seriesNameParam = new StringBuilder();
		seriesNameParam.append(StringUtils.substringBefore(seriesName, "/")).append("/").append("%").append("/")
				.append(StringUtils.substring(seriesName, (StringUtils.lastIndexOf(seriesName, '/') + 1)));
		sql.append("SELECT * FROM series_table WHERE name like '").append(seriesNameParam.toString())
				.append("' order by next_series desc FOR UPDATE");
		Query query = em.createNativeQuery(sql.toString(), SeriesTable.class);
		SeriesTable series = null;
		List<SeriesTable> list = query.getResultList();
		if (list.iterator().hasNext()) {
			series = list.iterator().next();
		}
		int nextSeries = 0;
		if (series != null) {
			nextSeries = series.getNextSeries();
		}
		StringBuilder checkStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			SeriesTable seriesTable = list.get(i);
			if (seriesTable.getName().equals(seriesName)) {
				series = seriesTable;
				checkStr.append("true");
				break;
			} else {
				checkStr.append("false");
			}
		}
		String value = null;
		if (series == null || StringUtils.containsOnly(checkStr.toString(), "false")) { // TODO Unlock Table
			if (nextSeries == 0) {
				value = "1";
			} else {
				value = "" + nextSeries;
			}
			series = new SeriesTable();
			if (nextSeries == 0) {
				series.setNextSeries(2);
			} else {
				series.setNextSeries(nextSeries + 1);
			}
			series.setName(seriesName);
			em.persist(series);
		} else {
			value = String.valueOf(nextSeries);
			series.setNextSeries(nextSeries + 1);
		}
		System.out.println("---------------- Series " + value);
		return value;
	}
}
