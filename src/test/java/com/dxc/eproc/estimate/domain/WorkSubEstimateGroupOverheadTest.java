package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupOverheadTest.
 */
class WorkSubEstimateGroupOverheadTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroupOverhead.class);
		WorkSubEstimateGroupOverhead workSubEstimateGroupOverhead1 = new WorkSubEstimateGroupOverhead();
		workSubEstimateGroupOverhead1.setId(1L);
		WorkSubEstimateGroupOverhead workSubEstimateGroupOverhead2 = new WorkSubEstimateGroupOverhead();
		workSubEstimateGroupOverhead2.setId(workSubEstimateGroupOverhead1.getId());
		assertThat(workSubEstimateGroupOverhead1).isEqualTo(workSubEstimateGroupOverhead2);
		workSubEstimateGroupOverhead2.setId(2L);
		assertThat(workSubEstimateGroupOverhead1).isNotEqualTo(workSubEstimateGroupOverhead2);
		workSubEstimateGroupOverhead1.setId(null);
		assertThat(workSubEstimateGroupOverhead1).isNotEqualTo(workSubEstimateGroupOverhead2);
	}
}
