package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class RaParametersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaParametersDTO.class);
        RaParametersDTO raParametersDTO1 = new RaParametersDTO();
        raParametersDTO1.setId(1L);
        RaParametersDTO raParametersDTO2 = new RaParametersDTO();
        assertThat(raParametersDTO1).isNotEqualTo(raParametersDTO2);
        raParametersDTO2.setId(raParametersDTO1.getId());
        assertThat(raParametersDTO1).isEqualTo(raParametersDTO2);
        raParametersDTO2.setId(2L);
        assertThat(raParametersDTO1).isNotEqualTo(raParametersDTO2);
        raParametersDTO1.setId(null);
        assertThat(raParametersDTO1).isNotEqualTo(raParametersDTO2);
    }
}
