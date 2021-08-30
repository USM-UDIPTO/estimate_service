package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemLBDTest.
 */
class WorkEstimateItemLBDTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateItemLBD.class);
		WorkEstimateItemLBD workEstimateItemLBD1 = new WorkEstimateItemLBD();
		workEstimateItemLBD1.setId(1L);
		WorkEstimateItemLBD workEstimateItemLBD2 = new WorkEstimateItemLBD();
		workEstimateItemLBD2.setId(workEstimateItemLBD1.getId());
		assertThat(workEstimateItemLBD1).isEqualTo(workEstimateItemLBD2);
		workEstimateItemLBD2.setId(2L);
		assertThat(workEstimateItemLBD1).isNotEqualTo(workEstimateItemLBD2);
		workEstimateItemLBD1.setId(null);
		assertThat(workEstimateItemLBD1).isNotEqualTo(workEstimateItemLBD2);
	}
}
