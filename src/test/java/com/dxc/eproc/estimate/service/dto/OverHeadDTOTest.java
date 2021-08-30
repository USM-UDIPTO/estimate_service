package com.dxc.eproc.estimate.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.dxc.eproc.estimate.integration.TestUtil;

class OverHeadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverHeadDTO.class);
        OverHeadDTO overHeadDTO1 = new OverHeadDTO();
        overHeadDTO1.setId(1L);
        OverHeadDTO overHeadDTO2 = new OverHeadDTO();
        assertThat(overHeadDTO1).isNotEqualTo(overHeadDTO2);
        overHeadDTO2.setId(overHeadDTO1.getId());
        assertThat(overHeadDTO1).isEqualTo(overHeadDTO2);
        overHeadDTO2.setId(2L);
        assertThat(overHeadDTO1).isNotEqualTo(overHeadDTO2);
        overHeadDTO1.setId(null);
        assertThat(overHeadDTO1).isNotEqualTo(overHeadDTO2);
    }
}
