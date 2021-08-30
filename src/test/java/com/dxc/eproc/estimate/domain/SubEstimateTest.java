package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.SubEstimate;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateTest.
 */
class SubEstimateTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(SubEstimate.class);
		SubEstimate subEstimate1 = new SubEstimate();
		subEstimate1.setId(1L);
		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2.setId(subEstimate1.getId());
		assertThat(subEstimate1).isEqualTo(subEstimate2);
		subEstimate2.setId(2L);
		assertThat(subEstimate1).isNotEqualTo(subEstimate2);
		subEstimate1.setId(null);
		assertThat(subEstimate1).isNotEqualTo(subEstimate2);
	}
}
