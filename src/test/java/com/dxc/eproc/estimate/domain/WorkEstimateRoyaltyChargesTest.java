package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges;

class WorkEstimateRoyaltyChargesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateRoyaltyCharges.class);
        WorkEstimateRoyaltyCharges workEstimateRoyalty1 = new WorkEstimateRoyaltyCharges();
        workEstimateRoyalty1.setId(1L);
        WorkEstimateRoyaltyCharges workEstimateRoyalty2 = new WorkEstimateRoyaltyCharges();
        workEstimateRoyalty2.setId(workEstimateRoyalty1.getId());
        assertThat(workEstimateRoyalty1).isEqualTo(workEstimateRoyalty2);
        workEstimateRoyalty2.setId(2L);
        assertThat(workEstimateRoyalty1).isNotEqualTo(workEstimateRoyalty2);
        workEstimateRoyalty1.setId(null);
        assertThat(workEstimateRoyalty1).isNotEqualTo(workEstimateRoyalty2);
    }
}
