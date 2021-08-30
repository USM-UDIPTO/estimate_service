package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateLoadUnloadChargesMapperTest {

    private WorkEstimateLoadUnloadChargesMapper workEstimateLoadUnloadChargesMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateLoadUnloadChargesMapper = new WorkEstimateLoadUnloadChargesMapperImpl();
    }
}
