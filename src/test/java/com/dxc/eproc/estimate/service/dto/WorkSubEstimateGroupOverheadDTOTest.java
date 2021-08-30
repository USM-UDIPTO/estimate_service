package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupOverheadDTOTest.
 */
class WorkSubEstimateGroupOverheadDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroupOverheadDTO.class);
		WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO1 = new WorkSubEstimateGroupOverheadDTO();
		workSubEstimateGroupOverheadDTO1.setId(1L);
		WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO2 = new WorkSubEstimateGroupOverheadDTO();
		assertThat(workSubEstimateGroupOverheadDTO1).isNotEqualTo(workSubEstimateGroupOverheadDTO2);
		workSubEstimateGroupOverheadDTO2.setId(workSubEstimateGroupOverheadDTO1.getId());
		assertThat(workSubEstimateGroupOverheadDTO1).isEqualTo(workSubEstimateGroupOverheadDTO2);
		workSubEstimateGroupOverheadDTO2.setId(2L);
		assertThat(workSubEstimateGroupOverheadDTO1).isNotEqualTo(workSubEstimateGroupOverheadDTO2);
		workSubEstimateGroupOverheadDTO1.setId(null);
		assertThat(workSubEstimateGroupOverheadDTO1).isNotEqualTo(workSubEstimateGroupOverheadDTO2);
	}
}
