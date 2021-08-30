package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateLoadUnloadChargesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLoadUnloadChargesDTO.class);
        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO1 = new WorkEstimateLoadUnloadChargesDTO();
        workEstimateLoadUnloadChargesDTO1.setId(1L);
        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO2 = new WorkEstimateLoadUnloadChargesDTO();
        assertThat(workEstimateLoadUnloadChargesDTO1).isNotEqualTo(workEstimateLoadUnloadChargesDTO2);
        workEstimateLoadUnloadChargesDTO2.setId(workEstimateLoadUnloadChargesDTO1.getId());
        assertThat(workEstimateLoadUnloadChargesDTO1).isEqualTo(workEstimateLoadUnloadChargesDTO2);
        workEstimateLoadUnloadChargesDTO2.setId(2L);
        assertThat(workEstimateLoadUnloadChargesDTO1).isNotEqualTo(workEstimateLoadUnloadChargesDTO2);
        workEstimateLoadUnloadChargesDTO1.setId(null);
        assertThat(workEstimateLoadUnloadChargesDTO1).isNotEqualTo(workEstimateLoadUnloadChargesDTO2);
    }
}
