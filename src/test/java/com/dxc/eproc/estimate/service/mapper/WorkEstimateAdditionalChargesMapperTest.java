package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateAdditionalChargesMapperTest {

    private WorkEstimateAdditionalChargesMapper workEstimateAdditionalChargesMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateAdditionalChargesMapper = new WorkEstimateAdditionalChargesMapperImpl();
    }
}
