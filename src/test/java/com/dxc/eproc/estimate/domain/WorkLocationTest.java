package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkLocation;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkLocationTest.
 */
class WorkLocationTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkLocation.class);
		WorkLocation workLocation1 = new WorkLocation();
		workLocation1.setId(1L);
		WorkLocation workLocation2 = new WorkLocation();
		workLocation2.setId(workLocation1.getId());
		assertThat(workLocation1).isEqualTo(workLocation2);
		workLocation2.setId(2L);
		assertThat(workLocation1).isNotEqualTo(workLocation2);
		workLocation1.setId(null);
		assertThat(workLocation1).isNotEqualTo(workLocation2);
	}
}
