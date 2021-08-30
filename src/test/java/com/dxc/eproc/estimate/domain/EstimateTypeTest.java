package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.EstimateType;

// TODO: Auto-generated Javadoc
/**
 * The Class EstimateTypeTest.
 */
class EstimateTypeTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(EstimateType.class);
		EstimateType estimateType1 = new EstimateType();
		estimateType1.setId(1L);
		EstimateType estimateType2 = new EstimateType();
		estimateType2.setId(estimateType1.getId());
		assertThat(estimateType1).isEqualTo(estimateType2);
		estimateType2.setId(2L);
		assertThat(estimateType1).isNotEqualTo(estimateType2);
		estimateType1.setId(null);
		assertThat(estimateType1).isNotEqualTo(estimateType2);
	}
}
