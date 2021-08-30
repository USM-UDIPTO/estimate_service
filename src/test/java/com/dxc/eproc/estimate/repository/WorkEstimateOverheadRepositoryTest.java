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
import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateOverheadRepositoryTest.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkEstimateOverheadRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateOverheadRepositoryTest.class);

	/** The Constant NAME. */
	private static final String NAME = "Flyover Maintainence";

	/** The Constant OVER_HEAD_TYPE. */
	private static final OverHeadType OVER_HEAD_TYPE = OverHeadType.NORMAL;

	/** The Constant OVER_HEAD_VALUE. */
	private static final BigDecimal OVER_HEAD_VALUE = BigDecimal.TEN;

	/** The ixed yn. */
	private static final Boolean fIXED_YN = true;

	/** The work estimate overhead. */
	private static WorkEstimateOverhead workEstimateOverhead;

	/** The work estimate overhead repository. */
	@Autowired
	private WorkEstimateOverheadRepository workEstimateOverheadRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	/**
	 * Creates the entity.
	 *
	 * @return the work estimate overhead
	 */
	public WorkEstimateOverhead createEntity() {
		WorkEstimateOverhead workEstimateOverhead = new WorkEstimateOverhead();
		workEstimateOverhead.setName(NAME);
		workEstimateOverhead.setWorkEstimateId(1L);
		workEstimateOverhead.setOverHeadType(OVER_HEAD_TYPE);
		workEstimateOverhead.setOverHeadValue(OVER_HEAD_VALUE);
		workEstimateOverhead.setFixedYn(fIXED_YN);
		return workEstimateOverhead;
	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest Start");
				
		workEstimateOverhead = createEntity();
		workEstimateOverheadRepository.save(workEstimateOverhead);

		Assert.assertEquals(workEstimateOverhead.getName(), NAME);
		Assert.assertEquals(workEstimateOverhead.getOverHeadType(), OVER_HEAD_TYPE);
		
		log.info("Test - createTest End");
		log.info("==================================================================================");
	}

	/**
	 * Update test.
	 */
	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - updateTest Start");

		String name = workEstimateOverhead.getName();
		workEstimateOverhead = workEstimateOverheadRepository.findById(workEstimateOverhead.getId()).get();

		workEstimateOverhead.setName("Flyover Maintainece-Updated");
		workEstimateOverhead.setOverHeadValue(BigDecimal.valueOf(20.0000));

		WorkEstimateOverhead testWorkEstimateOverhead = workEstimateOverheadRepository.save(workEstimateOverhead);
		String afterWorkEstimateOverheadName = testWorkEstimateOverhead.getName();
		Assertions.assertThat(name).isNotEqualTo(afterWorkEstimateOverheadName);
		
		log.info("Test - updateTest End");
		log.info("==================================================================================");
	}

	/**
	 * Find by work estimate id and id test.
	 */
	@Test(priority = 3)
	public void findByWorkEstimateIdAndIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest Start");
		
		Optional<WorkEstimateOverhead> lineEstimateOpt = workEstimateOverheadRepository
				.findByWorkEstimateIdAndId(workEstimateOverhead.getWorkEstimateId(), workEstimateOverhead.getId());
		Assert.assertTrue(lineEstimateOpt.isPresent());
		
		log.info("Test - findByWorkEstimateIdAndIdTest End");
		log.info("==================================================================================");
	}

	/**
	 * Find by work estimate id order by last modified ts desc test.
	 */
	@Test(priority = 5)
	public void findByWorkEstimateIdOrderByLastModifiedTsDescTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdOrderByLastModifiedTsDescTest Start");

		Page<WorkEstimateOverhead> result = workEstimateOverheadRepository
				.findByWorkEstimateIdOrderByLastModifiedTsDesc(1L, PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		
		log.info("Test - findByWorkEstimateIdOrderByLastModifiedTsDescTest End");
		log.info("==================================================================================");
	}

	/**
	 * Find all by work estimate id test.
	 */
	@Test(priority = 6)
	public void findAllByWorkEstimateIdTest() {
		log.info("==================================================================================");
		log.info("Test - findAllByWorkEstimateIdTest Start");

		List<WorkEstimateOverhead> result = workEstimateOverheadRepository.findAllByWorkEstimateId(1L);
		Assert.assertFalse(result.isEmpty());
		
		log.info("Test - findAllByWorkEstimateIdTest End");
		log.info("==================================================================================");
	}

	/**
	 * Sum normal over head by work estimate id test.
	 */
	@Test(priority = 6)
	public void sumNormalOverHeadByWorkEstimateIdTest() {
		log.info("==================================================================================");
		log.info("Test - sumNormalOverHeadByWorkEstimateIdTest Start");

		BigDecimal result = workEstimateOverheadRepository.sumNormalOverHeadByWorkEstimateId(1L);
		Assert.assertEquals(result.intValue(), workEstimateOverhead.getOverHeadValue().intValue());
		
		log.info("Test - sumNormalOverHeadByWorkEstimateIdTest End");
		log.info("==================================================================================");
	}

	/**
	 * Sum additional over head by work estimate id test.
	 */
	@Test(priority = 7)
	public void sumAdditionalOverHeadByWorkEstimateIdTest() {
		log.info("==================================================================================");
		log.info("Test - sumAdditionalOverHeadByWorkEstimateIdTest Start");

		BigDecimal result = workEstimateOverheadRepository.sumAdditionalOverHeadByWorkEstimateId(1L);
		Assert.assertEquals(result, null);
		
		log.info("Test - sumAdditionalOverHeadByWorkEstimateIdTest End");
		log.info("==================================================================================");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkEstimateOverheadRepositoryTest");
	}
}
