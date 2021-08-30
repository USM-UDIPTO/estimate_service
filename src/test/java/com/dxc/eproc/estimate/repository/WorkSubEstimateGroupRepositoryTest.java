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

import com.dxc.eproc.estimate.model.WorkSubEstimateGroup;


@SpringBootTest
@ActiveProfiles("test")
public class WorkSubEstimateGroupRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupRepositoryTest.class);

	private static WorkSubEstimateGroup workSubEstimateGroup;

	@Autowired
	private WorkSubEstimateGroupRepository workSubEstimateGroupRepository;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");

		workSubEstimateGroup = new WorkSubEstimateGroup();
		workSubEstimateGroup.setId(1L);
		workSubEstimateGroup.setOverheadTotal(null);
		workSubEstimateGroup.setLumpsumTotal(null);
		workSubEstimateGroup.setWorkEstimateId(1L);
		workSubEstimateGroup.setDescription("testD_o");
		workSubEstimateGroup = workSubEstimateGroupRepository.save(workSubEstimateGroup);
	}

	@Test
	public void sumOverheadTotalByWorkEstimateId() {
		BigDecimal result = workSubEstimateGroupRepository.sumOverheadTotalByWorkEstimateId(1L);
		Assert.assertEquals(result, null);
		log.info("sumOverheadTotalByWorkEstimateId successful");
	}

	@Test
	public void findByWorkEstimateIdAndIdTest() {
		Optional<WorkSubEstimateGroup> result = workSubEstimateGroupRepository.findByWorkEstimateIdAndId(1L,
				workSubEstimateGroup.getId());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdTest successful");
	}

	@Test
	public void findByWorkEstimateIdTest() {
		Page<WorkSubEstimateGroup> result = workSubEstimateGroupRepository.findByWorkEstimateId(1L,
				PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		log.info("findByWorkSubEstimateGroupIdTest successful");
	}

	@Test
	public void sumLumpsumTotalByWorkEstimateIdTest() {
		BigDecimal result = workSubEstimateGroupRepository.sumLumpsumTotalByWorkEstimateId(1L);
		Assert.assertEquals(result, null);
		log.info("sumLumpsumTotalByWorkEstimateId successful");
	}
}
