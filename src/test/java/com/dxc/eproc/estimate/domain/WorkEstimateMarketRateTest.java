package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateMarketRate;

class WorkEstimateMarketRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateMarketRate.class);
        WorkEstimateMarketRate workEstimateMarketRate1 = new WorkEstimateMarketRate();
        workEstimateMarketRate1.setId(1L);
        WorkEstimateMarketRate workEstimateMarketRate2 = new WorkEstimateMarketRate();
        workEstimateMarketRate2.setId(workEstimateMarketRate1.getId());
        assertThat(workEstimateMarketRate1).isEqualTo(workEstimateMarketRate2);
        workEstimateMarketRate2.setId(2L);
        assertThat(workEstimateMarketRate1).isNotEqualTo(workEstimateMarketRate2);
        workEstimateMarketRate1.setId(null);
        assertThat(workEstimateMarketRate1).isNotEqualTo(workEstimateMarketRate2);
    }
}
