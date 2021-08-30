package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateRateAnalysisMapperTest {

    private WorkEstimateRateAnalysisMapper workEstimateRateAnalysisMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateRateAnalysisMapper = new WorkEstimateRateAnalysisMapperImpl();
    }
}
