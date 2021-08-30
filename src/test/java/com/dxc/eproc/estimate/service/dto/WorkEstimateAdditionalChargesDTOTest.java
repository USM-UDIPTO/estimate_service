package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateAdditionalChargesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateAdditionalChargesDTO.class);
        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO1 = new WorkEstimateAdditionalChargesDTO();
        workEstimateAdditionalChargesDTO1.setId(1L);
        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO2 = new WorkEstimateAdditionalChargesDTO();
        assertThat(workEstimateAdditionalChargesDTO1).isNotEqualTo(workEstimateAdditionalChargesDTO2);
        workEstimateAdditionalChargesDTO2.setId(workEstimateAdditionalChargesDTO1.getId());
        assertThat(workEstimateAdditionalChargesDTO1).isEqualTo(workEstimateAdditionalChargesDTO2);
        workEstimateAdditionalChargesDTO2.setId(2L);
        assertThat(workEstimateAdditionalChargesDTO1).isNotEqualTo(workEstimateAdditionalChargesDTO2);
        workEstimateAdditionalChargesDTO1.setId(null);
        assertThat(workEstimateAdditionalChargesDTO1).isNotEqualTo(workEstimateAdditionalChargesDTO2);
    }
}
