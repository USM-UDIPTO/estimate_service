package com.dxc.eproc.estimate.service.mapper;

import org.testng.annotations.BeforeMethod;

class WorkEstimateRoyaltyChargesMapperTest {

	private WorkEstimateRoyaltyChargesMapper workEstimateRoyaltyChargesMapper;

	@BeforeMethod
	public void setUp() {
		workEstimateRoyaltyChargesMapper = new WorkEstimateRoyaltyChargesMapperImpl();
	}
}
