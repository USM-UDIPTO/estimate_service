package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateLeadChargesMapperTest {

	private WorkEstimateLeadChargesMapper workEstimateLeadChargesMapper;

	@BeforeMethod
	public void setUp() {
		workEstimateLeadChargesMapper = new WorkEstimateLeadChargesMapperImpl();
	}
}
