package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateRateAnalysisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateRateAnalysisDTO.class);
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO1 = new WorkEstimateRateAnalysisDTO();
        workEstimateRateAnalysisDTO1.setId(1L);
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO2 = new WorkEstimateRateAnalysisDTO();
        assertThat(workEstimateRateAnalysisDTO1).isNotEqualTo(workEstimateRateAnalysisDTO2);
        workEstimateRateAnalysisDTO2.setId(workEstimateRateAnalysisDTO1.getId());
        assertThat(workEstimateRateAnalysisDTO1).isEqualTo(workEstimateRateAnalysisDTO2);
        workEstimateRateAnalysisDTO2.setId(2L);
        assertThat(workEstimateRateAnalysisDTO1).isNotEqualTo(workEstimateRateAnalysisDTO2);
        workEstimateRateAnalysisDTO1.setId(null);
        assertThat(workEstimateRateAnalysisDTO1).isNotEqualTo(workEstimateRateAnalysisDTO2);
    }
}
