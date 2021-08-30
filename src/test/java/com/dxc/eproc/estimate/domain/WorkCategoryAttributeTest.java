package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkCategoryAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkCategoryAttributeTest.
 */
class WorkCategoryAttributeTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkCategoryAttribute.class);
		WorkCategoryAttribute workCategoryAttribute1 = new WorkCategoryAttribute();
		workCategoryAttribute1.setId(1L);
		WorkCategoryAttribute workCategoryAttribute2 = new WorkCategoryAttribute();
		workCategoryAttribute2.setId(workCategoryAttribute1.getId());
		assertThat(workCategoryAttribute1).isEqualTo(workCategoryAttribute2);
		workCategoryAttribute2.setId(2L);
		assertThat(workCategoryAttribute1).isNotEqualTo(workCategoryAttribute2);
		workCategoryAttribute1.setId(null);
		assertThat(workCategoryAttribute1).isNotEqualTo(workCategoryAttribute2);
	}
}
