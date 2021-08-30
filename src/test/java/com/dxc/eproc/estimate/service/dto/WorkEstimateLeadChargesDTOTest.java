package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateLeadChargesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLeadChargesDTO.class);
        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO1 = new WorkEstimateLeadChargesDTO();
        workEstimateLeadChargesDTO1.setId(1L);
        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO2 = new WorkEstimateLeadChargesDTO();
        assertThat(workEstimateLeadChargesDTO1).isNotEqualTo(workEstimateLeadChargesDTO2);
        workEstimateLeadChargesDTO2.setId(workEstimateLeadChargesDTO1.getId());
        assertThat(workEstimateLeadChargesDTO1).isEqualTo(workEstimateLeadChargesDTO2);
        workEstimateLeadChargesDTO2.setId(2L);
        assertThat(workEstimateLeadChargesDTO1).isNotEqualTo(workEstimateLeadChargesDTO2);
        workEstimateLeadChargesDTO1.setId(null);
        assertThat(workEstimateLeadChargesDTO1).isNotEqualTo(workEstimateLeadChargesDTO2);
    }
}