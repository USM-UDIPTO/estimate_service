package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateLeadCharges;

class WorkEstimateLeadChargesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLeadCharges.class);
        WorkEstimateLeadCharges workEstimateLead1 = new WorkEstimateLeadCharges();
        workEstimateLead1.setId(1L);
        WorkEstimateLeadCharges workEstimateLead2 = new WorkEstimateLeadCharges();
        workEstimateLead2.setId(workEstimateLead1.getId());
        assertThat(workEstimateLead1).isEqualTo(workEstimateLead2);
        workEstimateLead2.setId(2L);
        assertThat(workEstimateLead1).isNotEqualTo(workEstimateLead2);
        workEstimateLead1.setId(null);
        assertThat(workEstimateLead1).isNotEqualTo(workEstimateLead2);
    }
}
