package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges;

class WorkEstimateOtherAddnLiftChargesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateOtherAddnLiftCharges.class);
        WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges1 = new WorkEstimateOtherAddnLiftCharges();
        workEstimateOtherAddnLiftCharges1.setId(1L);
        WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges2 = new WorkEstimateOtherAddnLiftCharges();
        workEstimateOtherAddnLiftCharges2.setId(workEstimateOtherAddnLiftCharges1.getId());
        assertThat(workEstimateOtherAddnLiftCharges1).isEqualTo(workEstimateOtherAddnLiftCharges2);
        workEstimateOtherAddnLiftCharges2.setId(2L);
        assertThat(workEstimateOtherAddnLiftCharges1).isNotEqualTo(workEstimateOtherAddnLiftCharges2);
        workEstimateOtherAddnLiftCharges1.setId(null);
        assertThat(workEstimateOtherAddnLiftCharges1).isNotEqualTo(workEstimateOtherAddnLiftCharges2);
    }
}
