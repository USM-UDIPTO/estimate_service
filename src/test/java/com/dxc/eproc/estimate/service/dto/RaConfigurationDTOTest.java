package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class RaConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaConfigurationDTO.class);
        RaConfigurationDTO raConfigurationDTO1 = new RaConfigurationDTO();
        raConfigurationDTO1.setId(1L);
        RaConfigurationDTO raConfigurationDTO2 = new RaConfigurationDTO();
        assertThat(raConfigurationDTO1).isNotEqualTo(raConfigurationDTO2);
        raConfigurationDTO2.setId(raConfigurationDTO1.getId());
        assertThat(raConfigurationDTO1).isEqualTo(raConfigurationDTO2);
        raConfigurationDTO2.setId(2L);
        assertThat(raConfigurationDTO1).isNotEqualTo(raConfigurationDTO2);
        raConfigurationDTO1.setId(null);
        assertThat(raConfigurationDTO1).isNotEqualTo(raConfigurationDTO2);
    }
}
