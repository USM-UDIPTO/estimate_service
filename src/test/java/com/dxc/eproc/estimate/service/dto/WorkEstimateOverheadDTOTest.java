package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateOverheadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateOverheadDTO.class);
        WorkEstimateOverheadDTO workEstimateOverheadDTO1 = new WorkEstimateOverheadDTO();
        workEstimateOverheadDTO1.setId(1L);
        WorkEstimateOverheadDTO workEstimateOverheadDTO2 = new WorkEstimateOverheadDTO();
        assertThat(workEstimateOverheadDTO1).isNotEqualTo(workEstimateOverheadDTO2);
        workEstimateOverheadDTO2.setId(workEstimateOverheadDTO1.getId());
        assertThat(workEstimateOverheadDTO1).isEqualTo(workEstimateOverheadDTO2);
        workEstimateOverheadDTO2.setId(2L);
        assertThat(workEstimateOverheadDTO1).isNotEqualTo(workEstimateOverheadDTO2);
        workEstimateOverheadDTO1.setId(null);
        assertThat(workEstimateOverheadDTO1).isNotEqualTo(workEstimateOverheadDTO2);
    }
}
