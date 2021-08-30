package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class RaConfigurationMapperTest {

    private RaConfigurationMapper raConfigurationMapper;

    @BeforeMethod
    public void setUp() {
        raConfigurationMapper = new RaConfigurationMapperImpl();
    }
}
