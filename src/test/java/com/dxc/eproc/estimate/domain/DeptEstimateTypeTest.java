package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.DeptEstimateType;

// TODO: Auto-generated Javadoc
/**
 * The Class DeptEstimateTypeTest.
 */
class DeptEstimateTypeTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(DeptEstimateType.class);
		DeptEstimateType deptEstimateType1 = new DeptEstimateType();
		deptEstimateType1.setId(1L);
		DeptEstimateType deptEstimateType2 = new DeptEstimateType();
		deptEstimateType2.setId(deptEstimateType1.getId());
		assertThat(deptEstimateType1).isEqualTo(deptEstimateType2);
		deptEstimateType2.setId(2L);
		assertThat(deptEstimateType1).isNotEqualTo(deptEstimateType2);
		deptEstimateType1.setId(null);
		assertThat(deptEstimateType1).isNotEqualTo(deptEstimateType2);
	}
}
