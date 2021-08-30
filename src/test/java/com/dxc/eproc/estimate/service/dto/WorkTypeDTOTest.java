package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkTypeDTOTest.
 */
class WorkTypeDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkTypeDTO.class);
		WorkTypeDTO workTypeDTO1 = new WorkTypeDTO();
		workTypeDTO1.setId(1L);
		WorkTypeDTO workTypeDTO2 = new WorkTypeDTO();
		assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
		workTypeDTO2.setId(workTypeDTO1.getId());
		assertThat(workTypeDTO1).isEqualTo(workTypeDTO2);
		workTypeDTO2.setId(2L);
		assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
		workTypeDTO1.setId(null);
		assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
	}
}
