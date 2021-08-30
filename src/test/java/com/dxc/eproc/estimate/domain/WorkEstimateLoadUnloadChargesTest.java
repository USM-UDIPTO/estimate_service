package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges;

class WorkEstimateLoadUnloadChargesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEstimateLoadUnloadCharges.class);
        WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges1 = new WorkEstimateLoadUnloadCharges();
        workEstimateLoadUnloadCharges1.setId(1L);
        WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges2 = new WorkEstimateLoadUnloadCharges();
        workEstimateLoadUnloadCharges2.setId(workEstimateLoadUnloadCharges1.getId());
        assertThat(workEstimateLoadUnloadCharges1).isEqualTo(workEstimateLoadUnloadCharges2);
        workEstimateLoadUnloadCharges2.setId(2L);
        assertThat(workEstimateLoadUnloadCharges1).isNotEqualTo(workEstimateLoadUnloadCharges2);
        workEstimateLoadUnloadCharges1.setId(null);
        assertThat(workEstimateLoadUnloadCharges1).isNotEqualTo(workEstimateLoadUnloadCharges2);
    }
}
