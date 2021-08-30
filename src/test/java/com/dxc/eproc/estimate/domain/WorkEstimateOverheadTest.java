package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;

class WorkEstimateOverheadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateOverhead.class);
        WorkEstimateOverhead workEstimateOverhead1 = new WorkEstimateOverhead();
        workEstimateOverhead1.setId(1L);
        WorkEstimateOverhead workEstimateOverhead2 = new WorkEstimateOverhead();
        workEstimateOverhead2.setId(workEstimateOverhead1.getId());
        assertThat(workEstimateOverhead1).isEqualTo(workEstimateOverhead2);
        workEstimateOverhead2.setId(2L);
        assertThat(workEstimateOverhead1).isNotEqualTo(workEstimateOverhead2);
        workEstimateOverhead1.setId(null);
        assertThat(workEstimateOverhead1).isNotEqualTo(workEstimateOverhead2);
    }
}
