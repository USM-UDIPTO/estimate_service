package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateMarketRateMapperTest {

    private WorkEstimateMarketRateMapper workEstimateMarketRateMapper;

    @BeforeMethod
    public void setUp() {
        workEstimateMarketRateMapper = new WorkEstimateMarketRateMapperImpl();
    }
}
