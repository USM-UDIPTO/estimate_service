package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class WorkEstimateMarketRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateMarketRateDTO.class);
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO1 = new WorkEstimateMarketRateDTO();
        workEstimateMarketRateDTO1.setId(1L);
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO2 = new WorkEstimateMarketRateDTO();
        assertThat(workEstimateMarketRateDTO1).isNotEqualTo(workEstimateMarketRateDTO2);
        workEstimateMarketRateDTO2.setId(workEstimateMarketRateDTO1.getId());
        assertThat(workEstimateMarketRateDTO1).isEqualTo(workEstimateMarketRateDTO2);
        workEstimateMarketRateDTO2.setId(2L);
        assertThat(workEstimateMarketRateDTO1).isNotEqualTo(workEstimateMarketRateDTO2);
        workEstimateMarketRateDTO1.setId(null);
        assertThat(workEstimateMarketRateDTO1).isNotEqualTo(workEstimateMarketRateDTO2);
    }
}
