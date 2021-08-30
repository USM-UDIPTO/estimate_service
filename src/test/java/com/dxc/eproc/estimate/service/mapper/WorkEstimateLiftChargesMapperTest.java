package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateLiftChargesMapperTest {

    private WorkEstimateLiftChargesMapper workEstimateLiftChargesMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateLiftChargesMapper = new WorkEstimateLiftChargesMapperImpl();
    }
}
