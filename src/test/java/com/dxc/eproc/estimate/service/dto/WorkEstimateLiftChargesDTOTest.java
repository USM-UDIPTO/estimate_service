package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateLiftChargesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLiftChargesDTO.class);
        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO1 = new WorkEstimateLiftChargesDTO();
        workEstimateLiftChargesDTO1.setId(1L);
        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO2 = new WorkEstimateLiftChargesDTO();
        assertThat(workEstimateLiftChargesDTO1).isNotEqualTo(workEstimateLiftChargesDTO2);
        workEstimateLiftChargesDTO2.setId(workEstimateLiftChargesDTO1.getId());
        assertThat(workEstimateLiftChargesDTO1).isEqualTo(workEstimateLiftChargesDTO2);
        workEstimateLiftChargesDTO2.setId(2L);
        assertThat(workEstimateLiftChargesDTO1).isNotEqualTo(workEstimateLiftChargesDTO2);
        workEstimateLiftChargesDTO1.setId(null);
        assertThat(workEstimateLiftChargesDTO1).isNotEqualTo(workEstimateLiftChargesDTO2);
    }
}
