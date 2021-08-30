package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class RaParametersMapperTest {

    private RaParametersMapper raParametersMapper;

    @BeforeMethod
    public void setUp() {
        raParametersMapper = new RaParametersMapperImpl();
    }
}
