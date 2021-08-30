package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.LineEstimate;

class LineEstimateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineEstimate.class);
        LineEstimate lineEstimate1 = new LineEstimate();
        lineEstimate1.setId(1L);
        LineEstimate lineEstimate2 = new LineEstimate();
        lineEstimate2.setId(lineEstimate1.getId());
        assertThat(lineEstimate1).isEqualTo(lineEstimate2);
        lineEstimate2.setId(2L);
        assertThat(lineEstimate1).isNotEqualTo(lineEstimate2);
        lineEstimate1.setId(null);
        assertThat(lineEstimate1).isNotEqualTo(lineEstimate2);
    }
}
