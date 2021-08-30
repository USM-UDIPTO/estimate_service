package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.OverHead;

class OverHeadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverHead.class);
        OverHead overHead1 = new OverHead();
        overHead1.setId(1L);
        OverHead overHead2 = new OverHead();
        overHead2.setId(overHead1.getId());
        assertThat(overHead1).isEqualTo(overHead2);
        overHead2.setId(2L);
        assertThat(overHead1).isNotEqualTo(overHead2);
        overHead1.setId(null);
        assertThat(overHead1).isNotEqualTo(overHead2);
    }
}
