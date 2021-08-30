package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateDTOTest.
 */
class SubEstimateDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(SubEstimateDTO.class);
		SubEstimateDTO subEstimateDTO1 = new SubEstimateDTO();
		subEstimateDTO1.setId(1L);
		SubEstimateDTO subEstimateDTO2 = new SubEstimateDTO();
		assertThat(subEstimateDTO1).isNotEqualTo(subEstimateDTO2);
		subEstimateDTO2.setId(subEstimateDTO1.getId());
		assertThat(subEstimateDTO1).isEqualTo(subEstimateDTO2);
		subEstimateDTO2.setId(2L);
		assertThat(subEstimateDTO1).isNotEqualTo(subEstimateDTO2);
		subEstimateDTO1.setId(null);
		assertThat(subEstimateDTO1).isNotEqualTo(subEstimateDTO2);
	}
}
