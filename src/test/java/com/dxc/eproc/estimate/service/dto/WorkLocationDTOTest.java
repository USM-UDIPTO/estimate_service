package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkLocationDTOTest.
 */
class WorkLocationDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkLocationDTO.class);
		WorkLocationDTO workLocationDTO1 = new WorkLocationDTO();
		workLocationDTO1.setId(1L);
		WorkLocationDTO workLocationDTO2 = new WorkLocationDTO();
		assertThat(workLocationDTO1).isNotEqualTo(workLocationDTO2);
		workLocationDTO2.setId(workLocationDTO1.getId());
		assertThat(workLocationDTO1).isEqualTo(workLocationDTO2);
		workLocationDTO2.setId(2L);
		assertThat(workLocationDTO1).isNotEqualTo(workLocationDTO2);
		workLocationDTO1.setId(null);
		assertThat(workLocationDTO1).isNotEqualTo(workLocationDTO2);
	}
}
