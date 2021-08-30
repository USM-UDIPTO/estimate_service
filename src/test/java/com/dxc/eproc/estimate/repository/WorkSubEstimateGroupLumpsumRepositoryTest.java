package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum;

@SpringBootTest
@ActiveProfiles("test")
public class WorkSubEstimateGroupLumpsumRepositoryTest extends AbstractTestNGSpringContextTests {
	private final static Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupLumpsumRepositoryTest.class);

	private static WorkSubEstimateGroupLumpsum workSubEstimateGroupLumpsum;

	@Autowired
	private WorkSubEstimateGroupLumpsumRepository workSubEstimateGroupLumpsumRepository;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workSubEstimateGroupLumpsum = new WorkSubEstimateGroupLumpsum();
		workSubEstimateGroupLumpsum.setId(1L);
		workSubEstimateGroupLumpsum.setName("Test");
		workSubEstimateGroupLumpsum.setApproxRate(new BigDecimal(1.54));
		workSubEstimateGroupLumpsum.setWorkSubEstimateGroupId(1L);
		workSubEstimateGroupLumpsum = workSubEstimateGroupLumpsumRepository.save(workSubEstimateGroupLumpsum);
	}

	@Test
	public void findByWorkSubEstimateGroupIdTest() {
		Page<WorkSubEstimateGroupLumpsum> result = workSubEstimateGroupLumpsumRepository
				.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		log.info("findByWorkSubEstimateGroupIdTest successful");

	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdTest() {

		Optional<WorkSubEstimateGroupLumpsum> result = workSubEstimateGroupLumpsumRepository
				.findByWorkSubEstimateGroupIdAndId(1L, workSubEstimateGroupLumpsum.getId());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdTest successful");
	}

	@Test
	public void sumApproxRateByGroupIdTest() {
		BigDecimal result = workSubEstimateGroupLumpsumRepository.sumApproxRateByGroupId(1L);
		Assert.assertNotEquals(result, null);
		log.info("sumApproxRateByGroupIdTest successful");
	}
}