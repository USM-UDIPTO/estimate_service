package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class DeptEstimateTypeDTOTest.
 */
class DeptEstimateTypeDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(DeptEstimateTypeDTO.class);
		DeptEstimateTypeDTO deptEstimateTypeDTO1 = new DeptEstimateTypeDTO();
		deptEstimateTypeDTO1.setId(1L);
		DeptEstimateTypeDTO deptEstimateTypeDTO2 = new DeptEstimateTypeDTO();
		assertThat(deptEstimateTypeDTO1).isNotEqualTo(deptEstimateTypeDTO2);
		deptEstimateTypeDTO2.setId(deptEstimateTypeDTO1.getId());
		assertThat(deptEstimateTypeDTO1).isEqualTo(deptEstimateTypeDTO2);
		deptEstimateTypeDTO2.setId(2L);
		assertThat(deptEstimateTypeDTO1).isNotEqualTo(deptEstimateTypeDTO2);
		deptEstimateTypeDTO1.setId(null);
		assertThat(deptEstimateTypeDTO1).isNotEqualTo(deptEstimateTypeDTO2);
	}
}
