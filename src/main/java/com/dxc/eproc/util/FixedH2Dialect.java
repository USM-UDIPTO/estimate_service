package com.dxc.eproc.util;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * FixedH2Dialect class.
 * </p>
 */
public class FixedH2Dialect extends H2Dialect {

	/**
	 * <p>
	 * Constructor for FixedH2Dialect.
	 * </p>
	 */
	public FixedH2Dialect() {
		super();
		registerColumnType(Types.FLOAT, "real");
	}
}
