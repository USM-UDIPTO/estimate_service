package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateLiftCharges;

class WorkEstimateLiftChargesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLiftCharges.class);
        WorkEstimateLiftCharges workEstimateLiftCharges1 = new WorkEstimateLiftCharges();
        workEstimateLiftCharges1.setId(1L);
        WorkEstimateLiftCharges workEstimateLiftCharges2 = new WorkEstimateLiftCharges();
        workEstimateLiftCharges2.setId(workEstimateLiftCharges1.getId());
        assertThat(workEstimateLiftCharges1).isEqualTo(workEstimateLiftCharges2);
        workEstimateLiftCharges2.setId(2L);
        assertThat(workEstimateLiftCharges1).isNotEqualTo(workEstimateLiftCharges2);
        workEstimateLiftCharges1.setId(null);
        assertThat(workEstimateLiftCharges1).isNotEqualTo(workEstimateLiftCharges2);
    }
}
