package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimate;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateTest.
 */
class WorkEstimateTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimate.class);
		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1.setId(1L);
		WorkEstimate workEstimate2 = new WorkEstimate();
		workEstimate2.setId(workEstimate1.getId());
		assertThat(workEstimate1).isEqualTo(workEstimate2);
		workEstimate2.setId(2L);
		assertThat(workEstimate1).isNotEqualTo(workEstimate2);
		workEstimate1.setId(null);
		assertThat(workEstimate1).isNotEqualTo(workEstimate2);
	}
}
