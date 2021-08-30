package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.DeptHeadOfAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class DeptHeadOfAccountTest.
 */
class DeptHeadOfAccountTest {

	/**
	 * Equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(DeptHeadOfAccount.class);
		DeptHeadOfAccount deptHeadOfAccount1 = new DeptHeadOfAccount();
		deptHeadOfAccount1.setId(1L);
		DeptHeadOfAccount deptHeadOfAccount2 = new DeptHeadOfAccount();
		deptHeadOfAccount2.setId(deptHeadOfAccount1.getId());
		assertThat(deptHeadOfAccount1).isEqualTo(deptHeadOfAccount2);
		deptHeadOfAccount2.setId(2L);
		assertThat(deptHeadOfAccount1).isNotEqualTo(deptHeadOfAccount2);
		deptHeadOfAccount1.setId(null);
		assertThat(deptHeadOfAccount1).isNotEqualTo(deptHeadOfAccount2);
	}
}
