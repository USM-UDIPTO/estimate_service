package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateRoyaltyChargesDTOTest {

	@Test
	void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(WorkEstimateRoyaltyChargesDTO.class);
		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO1 = new WorkEstimateRoyaltyChargesDTO();
		workEstimateRoyaltyChargesDTO1.setId(1L);
		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO2 = new WorkEstimateRoyaltyChargesDTO();
		assertThat(workEstimateRoyaltyChargesDTO1).isNotEqualTo(workEstimateRoyaltyChargesDTO2);
		workEstimateRoyaltyChargesDTO2.setId(workEstimateRoyaltyChargesDTO1.getId());
		assertThat(workEstimateRoyaltyChargesDTO1).isEqualTo(workEstimateRoyaltyChargesDTO2);
		workEstimateRoyaltyChargesDTO2.setId(2L);
		assertThat(workEstimateRoyaltyChargesDTO1).isNotEqualTo(workEstimateRoyaltyChargesDTO2);
		workEstimateRoyaltyChargesDTO1.setId(null);
		assertThat(workEstimateRoyaltyChargesDTO1).isNotEqualTo(workEstimateRoyaltyChargesDTO2);
	}
}
