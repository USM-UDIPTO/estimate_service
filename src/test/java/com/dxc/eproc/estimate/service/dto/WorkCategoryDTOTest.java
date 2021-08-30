package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkCategoryDTOTest.
 */
class WorkCategoryDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkCategoryDTO.class);
		WorkCategoryDTO workCategoryDTO1 = new WorkCategoryDTO();
		workCategoryDTO1.setId(1L);
		WorkCategoryDTO workCategoryDTO2 = new WorkCategoryDTO();
		assertThat(workCategoryDTO1).isNotEqualTo(workCategoryDTO2);
		workCategoryDTO2.setId(workCategoryDTO1.getId());
		assertThat(workCategoryDTO1).isEqualTo(workCategoryDTO2);
		workCategoryDTO2.setId(2L);
		assertThat(workCategoryDTO1).isNotEqualTo(workCategoryDTO2);
		workCategoryDTO1.setId(null);
		assertThat(workCategoryDTO1).isNotEqualTo(workCategoryDTO2);
	}
}
