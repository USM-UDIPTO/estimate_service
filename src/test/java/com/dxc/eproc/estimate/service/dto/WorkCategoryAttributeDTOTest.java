package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkCategoryAttributeDTOTest.
 */
class WorkCategoryAttributeDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkCategoryAttributeDTO.class);
		WorkCategoryAttributeDTO workCategoryAttributeDTO1 = new WorkCategoryAttributeDTO();
		workCategoryAttributeDTO1.setId(1L);
		WorkCategoryAttributeDTO workCategoryAttributeDTO2 = new WorkCategoryAttributeDTO();
		assertThat(workCategoryAttributeDTO1).isNotEqualTo(workCategoryAttributeDTO2);
		workCategoryAttributeDTO2.setId(workCategoryAttributeDTO1.getId());
		assertThat(workCategoryAttributeDTO1).isEqualTo(workCategoryAttributeDTO2);
		workCategoryAttributeDTO2.setId(2L);
		assertThat(workCategoryAttributeDTO1).isNotEqualTo(workCategoryAttributeDTO2);
		workCategoryAttributeDTO1.setId(null);
		assertThat(workCategoryAttributeDTO1).isNotEqualTo(workCategoryAttributeDTO2);
	}
}
