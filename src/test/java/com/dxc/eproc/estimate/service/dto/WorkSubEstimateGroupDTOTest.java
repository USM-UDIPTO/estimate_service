package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupDTOTest.
 */
class WorkSubEstimateGroupDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkSubEstimateGroupDTO.class);
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO1 = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO1.setId(1L);
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO2 = new WorkSubEstimateGroupDTO();
		assertThat(workSubEstimateGroupDTO1).isNotEqualTo(workSubEstimateGroupDTO2);
		workSubEstimateGroupDTO2.setId(workSubEstimateGroupDTO1.getId());
		assertThat(workSubEstimateGroupDTO1).isEqualTo(workSubEstimateGroupDTO2);
		workSubEstimateGroupDTO2.setId(2L);
		assertThat(workSubEstimateGroupDTO1).isNotEqualTo(workSubEstimateGroupDTO2);
		workSubEstimateGroupDTO1.setId(null);
		assertThat(workSubEstimateGroupDTO1).isNotEqualTo(workSubEstimateGroupDTO2);
	}
}
