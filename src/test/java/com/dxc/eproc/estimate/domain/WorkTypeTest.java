package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkType;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkTypeTest.
 */
class WorkTypeTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkType.class);
		WorkType workType1 = new WorkType();
		workType1.setId(1L);
		WorkType workType2 = new WorkType();
		workType2.setId(workType1.getId());
		assertThat(workType1).isEqualTo(workType2);
		workType2.setId(2L);
		assertThat(workType1).isNotEqualTo(workType2);
		workType1.setId(null);
		assertThat(workType1).isNotEqualTo(workType2);
	}
}
