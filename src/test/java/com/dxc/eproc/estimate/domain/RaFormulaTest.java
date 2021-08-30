package com.dxc.eproc.estimate.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;
import com.dxc.eproc.estimate.model.RaFormula;

class RaFormulaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaFormula.class);
        RaFormula raFormula1 = new RaFormula();
        raFormula1.setId(1L);
        RaFormula raFormula2 = new RaFormula();
        raFormula2.setId(raFormula1.getId());
        assertThat(raFormula1).isEqualTo(raFormula2);
        raFormula2.setId(2L);
        assertThat(raFormula1).isNotEqualTo(raFormula2);
        raFormula1.setId(null);
        assertThat(raFormula1).isNotEqualTo(raFormula2);
    }
}
