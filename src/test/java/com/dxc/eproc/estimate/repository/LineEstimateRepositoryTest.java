package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.LineEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;

// TODO: Auto-generated Javadoc
/**
 * The Class LineEstimateRepositoryTest.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class LineEstimateRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LineEstimateRepositoryTest.class);

	/** The Constant NAME. */
	private static final String NAME = "Flyover Maintainence";

	/** The Constant APROXIMATE_RATE. */
	private static final BigDecimal APROXIMATE_RATE = BigDecimal.TEN;

	/** The line estimate. */
	private static LineEstimate lineEstimate;

	/** The work estimate. */
	private static WorkEstimate workEstimate;

	/** The line estimate repository. */
	@Autowired
	private LineEstimateRepository lineEstimateRepository;

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workEstimate = new WorkEstimate();
		workEstimate.setId(1L);
		workEstimate.setName("AA");
		workEstimate.setDeptId(1L);
		workEstimate.setEstimateTypeId(1L);
		workEstimate.setWorkTypeId(1L);
		workEstimate.setWorkCategoryId(1L);
		workEstimate.setWorkCategoryAttribute(1L);
		workEstimate.setWorkEstimateNumber("KPWD");
		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate.setFileNumber("12");
		workEstimate.setLocationId(1L);
		workEstimate.setApprovedBudgetYn(false);
		workEstimate.setHkrdbFundedYn(false);
		;
		workEstimate = workEstimateRepository.save(workEstimate);

		lineEstimate = new LineEstimate();
		lineEstimate = createEntity();
		lineEstimate = lineEstimateRepository.save(lineEstimate);

	}

	/**
	 * Creates the entity.
	 *
	 * @return the line estimate
	 */
	public LineEstimate createEntity() {
		LineEstimate lineEstimate = new LineEstimate();
		lineEstimate.setName(NAME);
		lineEstimate.setApproxRate(APROXIMATE_RATE);
		lineEstimate.setWorkEstimateId(1L);
		return lineEstimate;
	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - create Start");

		Assert.assertEquals(lineEstimate.getName(), lineEstimate.getName());
		Assert.assertEquals(lineEstimate.getApproxRate(), lineEstimate.getApproxRate());
		log.info("==================================================================================");
		log.info("Test - create End");
	}

	/**
	 * Update test.
	 */
	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - Update Start");

		String name = lineEstimate.getName();
		lineEstimate = lineEstimateRepository.findById(lineEstimate.getId()).get();

		lineEstimate.setName("Flyover Maintainece-Updated");
		lineEstimate.setApproxRate(BigDecimal.valueOf(20.0000));

		LineEstimate testLineEstimate = lineEstimateRepository.save(lineEstimate);
		String afterlineEstimateName = testLineEstimate.getName();
		Assertions.assertThat(name).isNotEqualTo(afterlineEstimateName);
		log.info("==================================================================================");
		log.info("Test - Update End");
	}

	/**
	 * Find by work estimate id and id test.
	 */
	@Test(priority = 3)
	public void findByWorkEstimateIdAndIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest Start");
		Optional<LineEstimate> lineEstimateOpt = lineEstimateRepository
				.findByWorkEstimateIdAndId(lineEstimate.getWorkEstimateId(), lineEstimate.getId());
		Assert.assertTrue(lineEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest End");
	}

	/**
	 * Find by work estimate id order by last modified ts desc test.
	 */
	@Test(priority = 4)
	public void findByWorkEstimateIdOrderByLastModifiedTsDescTest() {

		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdOrderByLastModifiedTsDescTest Start");
		List<LineEstimate> result = lineEstimateRepository
				.findByWorkEstimateIdOrderByLastModifiedTsDesc(lineEstimate.getWorkEstimateId());
		Assert.assertFalse(result.isEmpty());
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdOrderByLastModifiedTsDescTest End");

	}

	/**
	 * Find by work estimate id order by last modified ts desc test 2.
	 */
	@Test(priority = 5)
	public void findByWorkEstimateIdOrderByLastModifiedTsDescTest2() {
		Page<LineEstimate> result = lineEstimateRepository.findByWorkEstimateIdOrderByLastModifiedTsDesc(1L,
				PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		log.info("findByWorkEstimateIdOrderByLastModifiedTsDescTest2 successful");
	}

	/**
	 * Find all by work estimate id test.
	 */
	@Test(priority = 6)
	public void findAllByWorkEstimateIdTest() {
		List<LineEstimate> result = lineEstimateRepository.findAllByWorkEstimateId(1L);
		Assert.assertFalse(result.isEmpty());
		log.info("findAllByWorkEstimateIdTest successful");
	}

	/**
	 * Sum approximate value by line estimate id test.
	 */
	@Test(priority = 6)
	public void sumApproximateValueByLineEstimateIdTest() {

		BigDecimal result = lineEstimateRepository.sumApproximateValueByLineEstimateId(lineEstimate.getId());
		Assert.assertEquals(result.intValue(), lineEstimate.getApproxRate().intValue());
		log.info("sumApproximateValueByLineEstimateIdTest successful");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - LineEstimateRepositoryTest");

	}
}
