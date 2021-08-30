package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupTest.
 */
class WorkSubEstimateGroupTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroup.class);
		WorkSubEstimateGroup workSubEstimateGroup1 = new WorkSubEstimateGroup();
		workSubEstimateGroup1.setId(1L);
		WorkSubEstimateGroup workSubEstimateGroup2 = new WorkSubEstimateGroup();
		workSubEstimateGroup2.setId(workSubEstimateGroup1.getId());
		assertThat(workSubEstimateGroup1).isEqualTo(workSubEstimateGroup2);
		workSubEstimateGroup2.setId(2L);
		assertThat(workSubEstimateGroup1).isNotEqualTo(workSubEstimateGroup2);
		workSubEstimateGroup1.setId(null);
		assertThat(workSubEstimateGroup1).isNotEqualTo(workSubEstimateGroup2);
	}
}
