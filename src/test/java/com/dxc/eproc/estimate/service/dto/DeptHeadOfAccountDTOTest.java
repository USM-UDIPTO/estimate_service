package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class DeptHeadOfAccountDTOTest.
 */
class DeptHeadOfAccountDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(DeptHeadOfAccountDTO.class);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO1 = new DeptHeadOfAccountDTO();
		deptHeadOfAccountDTO1.setId(1L);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO2 = new DeptHeadOfAccountDTO();
		assertThat(deptHeadOfAccountDTO1).isNotEqualTo(deptHeadOfAccountDTO2);
		deptHeadOfAccountDTO2.setId(deptHeadOfAccountDTO1.getId());
		assertThat(deptHeadOfAccountDTO1).isEqualTo(deptHeadOfAccountDTO2);
		deptHeadOfAccountDTO2.setId(2L);
		assertThat(deptHeadOfAccountDTO1).isNotEqualTo(deptHeadOfAccountDTO2);
		deptHeadOfAccountDTO1.setId(null);
		assertThat(deptHeadOfAccountDTO1).isNotEqualTo(deptHeadOfAccountDTO2);
	}
}
