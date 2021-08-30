package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkCategory;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkCategoryTest.
 */
class WorkCategoryTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkCategory.class);
		WorkCategory workCategory1 = new WorkCategory();
		workCategory1.setId(1L);
		WorkCategory workCategory2 = new WorkCategory();
		workCategory2.setId(workCategory1.getId());
		assertThat(workCategory1).isEqualTo(workCategory2);
		workCategory2.setId(2L);
		assertThat(workCategory1).isNotEqualTo(workCategory2);
		workCategory1.setId(null);
		assertThat(workCategory1).isNotEqualTo(workCategory2);
	}
}
