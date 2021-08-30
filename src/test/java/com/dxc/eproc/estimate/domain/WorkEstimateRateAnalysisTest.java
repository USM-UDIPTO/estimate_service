package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis;

class WorkEstimateRateAnalysisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateRateAnalysis.class);
        WorkEstimateRateAnalysis workEstimateRateAnalysis1 = new WorkEstimateRateAnalysis();
        workEstimateRateAnalysis1.setId(1L);
        WorkEstimateRateAnalysis workEstimateRateAnalysis2 = new WorkEstimateRateAnalysis();
        workEstimateRateAnalysis2.setId(workEstimateRateAnalysis1.getId());
        assertThat(workEstimateRateAnalysis1).isEqualTo(workEstimateRateAnalysis2);
        workEstimateRateAnalysis2.setId(2L);
        assertThat(workEstimateRateAnalysis1).isNotEqualTo(workEstimateRateAnalysis2);
        workEstimateRateAnalysis1.setId(null);
        assertThat(workEstimateRateAnalysis1).isNotEqualTo(workEstimateRateAnalysis2);
    }
}
