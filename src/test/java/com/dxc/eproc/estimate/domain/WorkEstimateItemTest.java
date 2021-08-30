package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateItem;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemTest.
 */
class WorkEstimateItemTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateItem.class);
		WorkEstimateItem workEstimateItem1 = new WorkEstimateItem();
		workEstimateItem1.setId(1L);
		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2.setId(workEstimateItem1.getId());
		assertThat(workEstimateItem1).isEqualTo(workEstimateItem2);
		workEstimateItem2.setId(2L);
		assertThat(workEstimateItem1).isNotEqualTo(workEstimateItem2);
		workEstimateItem1.setId(null);
		assertThat(workEstimateItem1).isNotEqualTo(workEstimateItem2);
	}
}
