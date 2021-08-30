package com.dxc.eproc.estimate;

// TODO: Auto-generated Javadoc
/**
 * The Class EstimateServiceConstants.
 */
public class EstimateServiceConstants {

	/** The Constant EPROC_MIN_DECIMAL_PATTERN. */
	public static final String EPROC_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,9}(?:\\.\\d{1,4})?$";
	
	public static final String OVERHEAD_APPROX_RATE = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,9}(?:\\.\\d{1,4})?$";

	/** The Constant LBD_PARTICULARS_PATTERN. */
	public static final String LBD_PARTICULARS_PATTERN = "^(?! )[A-Za-z0-9 \\s-+\\/&',.]*(?<! )$";

	/** The Constant LUMPSUM_NAME_PATTERN. */
	public static final String LUMPSUM_NAME_PATTERN = "^(?! )[\\'+\\\\\\/&,.A-Za-z0-9 \\s-]*(?<! )$";

	/** The Constant SR_ITEM_CODE. */
	public static final String SR_ITEM_CODE = "^(?! )[A-Za-z0-9.]*(?<! )$";

	/** The Constant BASE_RATE_PATTERN. */
	public static final String BASE_RATE_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,12}(?:\\.\\d{1,4})?$";

	/** The Constant EPROC_DECIMAL_PATTERN. */
	public static final String EPROC_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,12}(?:\\.\\d{1,4})?$";

	/** The Constant PERCENTAGE_PATTERN. */
	public static final String PERCENTAGE_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,5}(?:\\.\\d{1,2})?$";

	/** The Constant SCOPE_OF_WORK. */
	public static final String SCOPE_OF_WORK = "^(?! )[A-Za-z0-9 \\s-+,.\\\\&/]*(?<! )$";

	/** The Constant WORKESTIMATE_ITEM_DESCRIPTION. */
	public static final String WORKESTIMATE_ITEM_DESCRIPTION = "^(?! )[A-Za-z0-9 \\s-+\\\\\\/&,.]*(?<! )$";

	/** The Constant WORK_INDENT_SERIES. */
	public static final String WORK_INDENT_SERIES = "WORK_INDENT";
	
	public static final String RA_PREVAILING_RATE_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,6}(?:\\.\\d{1,4})?$";
	
	public static final String RA_ROYALTY_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,8}(?:\\.\\d{1,4})?$";
	
	public static final String RA_LIFT_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,8}(?:\\.\\d{1,4})?$";
	
	public static final String RA_LEAD_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,4}(?:\\.\\d{1,4})?$";
	
	public static final String RA_MIN_DECIMAL_PATTERN = "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,2}(?:\\.\\d{1,4})?$";

}
