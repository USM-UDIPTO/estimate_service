package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.RaParameters;

class RaParametersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaParameters.class);
        RaParameters raParameters1 = new RaParameters();
        raParameters1.setId(1L);
        RaParameters raParameters2 = new RaParameters();
        raParameters2.setId(raParameters1.getId());
        assertThat(raParameters1).isEqualTo(raParameters2);
        raParameters2.setId(2L);
        assertThat(raParameters1).isNotEqualTo(raParameters2);
        raParameters1.setId(null);
        assertThat(raParameters1).isNotEqualTo(raParameters2);
    }
}
