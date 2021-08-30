package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SchemeCodeDTOTest.
 */
class SchemeCodeDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(SchemeCodeDTO.class);
		SchemeCodeDTO schemeCodeDTO1 = new SchemeCodeDTO();
		schemeCodeDTO1.setId(1L);
		SchemeCodeDTO schemeCodeDTO2 = new SchemeCodeDTO();
		assertThat(schemeCodeDTO1).isNotEqualTo(schemeCodeDTO2);
		schemeCodeDTO2.setId(schemeCodeDTO1.getId());
		assertThat(schemeCodeDTO1).isEqualTo(schemeCodeDTO2);
		schemeCodeDTO2.setId(2L);
		assertThat(schemeCodeDTO1).isNotEqualTo(schemeCodeDTO2);
		schemeCodeDTO1.setId(null);
		assertThat(schemeCodeDTO1).isNotEqualTo(schemeCodeDTO2);
	}
}
