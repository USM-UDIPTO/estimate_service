package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateDTOTest.
 */
class WorkEstimateDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateDTO.class);
		WorkEstimateDTO workEstimateDTO1 = new WorkEstimateDTO();
		workEstimateDTO1.setId(1L);
		WorkEstimateDTO workEstimateDTO2 = new WorkEstimateDTO();
		assertThat(workEstimateDTO1).isNotEqualTo(workEstimateDTO2);
		workEstimateDTO2.setId(workEstimateDTO1.getId());
		assertThat(workEstimateDTO1).isEqualTo(workEstimateDTO2);
		workEstimateDTO2.setId(2L);
		assertThat(workEstimateDTO1).isNotEqualTo(workEstimateDTO2);
		workEstimateDTO1.setId(null);
		assertThat(workEstimateDTO1).isNotEqualTo(workEstimateDTO2);
	}
}
