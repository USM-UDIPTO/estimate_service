package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class OverHeadMapperTest {

    private OverHeadMapper overHeadMapper;

    @BeforeMethod
    public void setUp() {
        overHeadMapper = new OverHeadMapperImpl();
    }
}
