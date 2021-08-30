package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemLBDDTOTest.
 */
class WorkEstimateItemLBDDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateItemLBDDTO.class);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO1 = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO1.setId(1L);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO2 = new WorkEstimateItemLBDDTO();
		assertThat(workEstimateItemLBDDTO1).isNotEqualTo(workEstimateItemLBDDTO2);
		workEstimateItemLBDDTO2.setId(workEstimateItemLBDDTO1.getId());
		assertThat(workEstimateItemLBDDTO1).isEqualTo(workEstimateItemLBDDTO2);
		workEstimateItemLBDDTO2.setId(2L);
		assertThat(workEstimateItemLBDDTO1).isNotEqualTo(workEstimateItemLBDDTO2);
		workEstimateItemLBDDTO1.setId(null);
		assertThat(workEstimateItemLBDDTO1).isNotEqualTo(workEstimateItemLBDDTO2);
	}
}
