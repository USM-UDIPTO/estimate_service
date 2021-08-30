package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.SchemeCode;

// TODO: Auto-generated Javadoc
/**
 * The Class SchemeCodeTest.
 */
class SchemeCodeTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(SchemeCode.class);
		SchemeCode schemeCode1 = new SchemeCode();
		schemeCode1.setId(1L);
		SchemeCode schemeCode2 = new SchemeCode();
		schemeCode2.setId(schemeCode1.getId());
		assertThat(schemeCode1).isEqualTo(schemeCode2);
		schemeCode2.setId(2L);
		assertThat(schemeCode1).isNotEqualTo(schemeCode2);
		schemeCode1.setId(null);
		assertThat(schemeCode1).isNotEqualTo(schemeCode2);
	}
}
