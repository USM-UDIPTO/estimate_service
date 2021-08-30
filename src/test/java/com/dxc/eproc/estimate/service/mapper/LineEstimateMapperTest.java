package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class LineEstimateMapperTest {

    private LineEstimateMapper lineEstimateMapper;

    @BeforeMethod
    public void setUp() {
        lineEstimateMapper = new LineEstimateMapperImpl();
    }
}
