package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemDTOTest.
 */
class WorkEstimateItemDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateItemDTO.class);
		WorkEstimateItemDTO workEstimateItemDTO1 = new WorkEstimateItemDTO();
		workEstimateItemDTO1.setId(1L);
		WorkEstimateItemDTO workEstimateItemDTO2 = new WorkEstimateItemDTO();
		assertThat(workEstimateItemDTO1).isNotEqualTo(workEstimateItemDTO2);
		workEstimateItemDTO2.setId(workEstimateItemDTO1.getId());
		assertThat(workEstimateItemDTO1).isEqualTo(workEstimateItemDTO2);
		workEstimateItemDTO2.setId(2L);
		assertThat(workEstimateItemDTO1).isNotEqualTo(workEstimateItemDTO2);
		workEstimateItemDTO1.setId(null);
		assertThat(workEstimateItemDTO1).isNotEqualTo(workEstimateItemDTO2);
	}
}
