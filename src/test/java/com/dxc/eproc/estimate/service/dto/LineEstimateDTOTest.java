package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class LineEstimateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineEstimateDTO.class);
        LineEstimateDTO lineEstimateDTO1 = new LineEstimateDTO();
        lineEstimateDTO1.setId(1L);
        LineEstimateDTO lineEstimateDTO2 = new LineEstimateDTO();
        assertThat(lineEstimateDTO1).isNotEqualTo(lineEstimateDTO2);
        lineEstimateDTO2.setId(lineEstimateDTO1.getId());
        assertThat(lineEstimateDTO1).isEqualTo(lineEstimateDTO2);
        lineEstimateDTO2.setId(2L);
        assertThat(lineEstimateDTO1).isNotEqualTo(lineEstimateDTO2);
        lineEstimateDTO1.setId(null);
        assertThat(lineEstimateDTO1).isNotEqualTo(lineEstimateDTO2);
    }
}
