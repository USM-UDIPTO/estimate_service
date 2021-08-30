package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupLumpsumDTOTest.
 */
class WorkSubEstimateGroupLumpsumDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroupLumpsumDTO.class);
		WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO1 = new WorkSubEstimateGroupLumpsumDTO();
		workSubEstimateGroupLumpsumDTO1.setId(1L);
		WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO2 = new WorkSubEstimateGroupLumpsumDTO();
		assertThat(workSubEstimateGroupLumpsumDTO1).isNotEqualTo(workSubEstimateGroupLumpsumDTO2);
		workSubEstimateGroupLumpsumDTO2.setId(workSubEstimateGroupLumpsumDTO1.getId());
		assertThat(workSubEstimateGroupLumpsumDTO1).isEqualTo(workSubEstimateGroupLumpsumDTO2);
		workSubEstimateGroupLumpsumDTO2.setId(2L);
		assertThat(workSubEstimateGroupLumpsumDTO1).isNotEqualTo(workSubEstimateGroupLumpsumDTO2);
		workSubEstimateGroupLumpsumDTO1.setId(null);
		assertThat(workSubEstimateGroupLumpsumDTO1).isNotEqualTo(workSubEstimateGroupLumpsumDTO2);
	}
}
