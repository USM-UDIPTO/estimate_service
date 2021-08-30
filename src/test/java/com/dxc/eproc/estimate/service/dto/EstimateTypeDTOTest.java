package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EstimateTypeDTOTest.
 */
class EstimateTypeDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(EstimateTypeDTO.class);
		EstimateTypeDTO estimateTypeDTO1 = new EstimateTypeDTO();
		estimateTypeDTO1.setId(1L);
		EstimateTypeDTO estimateTypeDTO2 = new EstimateTypeDTO();
		assertThat(estimateTypeDTO1).isNotEqualTo(estimateTypeDTO2);
		estimateTypeDTO2.setId(estimateTypeDTO1.getId());
		assertThat(estimateTypeDTO1).isEqualTo(estimateTypeDTO2);
		estimateTypeDTO2.setId(2L);
		assertThat(estimateTypeDTO1).isNotEqualTo(estimateTypeDTO2);
		estimateTypeDTO1.setId(null);
		assertThat(estimateTypeDTO1).isNotEqualTo(estimateTypeDTO2);
	}
}
