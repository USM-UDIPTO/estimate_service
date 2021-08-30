package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupLumpsumTest.
 */
class WorkSubEstimateGroupLumpsumTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroupLumpsum.class);
		WorkSubEstimateGroupLumpsum workSubEstimateGroupLumpsum1 = new WorkSubEstimateGroupLumpsum();
		workSubEstimateGroupLumpsum1.setId(1L);
		WorkSubEstimateGroupLumpsum workSubEstimateGroupLumpsum2 = new WorkSubEstimateGroupLumpsum();
		workSubEstimateGroupLumpsum2.setId(workSubEstimateGroupLumpsum1.getId());
		assertThat(workSubEstimateGroupLumpsum1).isEqualTo(workSubEstimateGroupLumpsum2);
		workSubEstimateGroupLumpsum2.setId(2L);
		assertThat(workSubEstimateGroupLumpsum1).isNotEqualTo(workSubEstimateGroupLumpsum2);
		workSubEstimateGroupLumpsum1.setId(null);
		assertThat(workSubEstimateGroupLumpsum1).isNotEqualTo(workSubEstimateGroupLumpsum2);
	}
}
