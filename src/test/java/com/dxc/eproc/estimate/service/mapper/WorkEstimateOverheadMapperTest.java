package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateOverheadMapperTest {

    private WorkEstimateOverheadMapper workEstimateOverheadMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateOverheadMapper = new WorkEstimateOverheadMapperImpl();
    }
}
