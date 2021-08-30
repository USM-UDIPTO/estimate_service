package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.model.SubEstimate;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateRepositoryTest.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class SubEstimateRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SubEstimateRepositoryTest.class);

	/** The Constant DEFAULT_AREA_WEIGHTAGE_CIRCLE. */
	private static final Long DEFAULT_AREA_WEIGHTAGE_CIRCLE = 1L;

	/** The Constant UPDATE_AREA_WEIGHTAGE_CIRCLE. */
	private static final Long UPDATE_AREA_WEIGHTAGE_CIRCLE = 2L;

	/** The Constant DEFAULT_COMPLETED_YN. */
	private static final Boolean DEFAULT_COMPLETED_YN = true;

	/** The Constant UPDATE_COMPLETED_YN. */
	private static final Boolean UPDATE_COMPLETED_YN = true;

	/** The Constant DEFAULT_AREA_WEIGHTAGE_DESCRIPTION. */
	private static final String DEFAULT_AREA_WEIGHTAGE_DESCRIPTION = "AREA_WEIGHTAGE_DESCRIPTION_001";

	/** The Constant UPDATE_AREA_WEIGHTAGE_DESCRIPTION. */
	private static final String UPDATE_AREA_WEIGHTAGE_DESCRIPTION = "AREA_WEIGHTAGE_DESCRIPTION_002";

	/** The Constant DEFAULT_AREA_WEIGHTAGE_ID. */
	private static final Long DEFAULT_AREA_WEIGHTAGE_ID = 1L;

	/** The Constant UPDATE_AREA_WEIGHTAGE_ID. */
	private static final Long UPDATE_AREA_WEIGHTAGE_ID = 2L;

	/** The Constant DEFAULT_ESTIMATE_TOTAL. */
	private static final BigDecimal DEFAULT_ESTIMATE_TOTAL = BigDecimal.valueOf(12.0000);

	/** The Constant UPDATE_ESTIMATE_TOTAL. */
	private static final BigDecimal UPDATE_ESTIMATE_TOTAL = BigDecimal.valueOf(14.0000);

	/** The Constant DEFAULT_SOR_WOR_CATEGORY_ID. */
	private static final Long DEFAULT_SOR_WOR_CATEGORY_ID = 1L;

	/** The Constant DEFAULT_WORK_SUBESTIMATE_GROUP_ID. */
	private static final Long DEFAULT_WORK_SUBESTIMATE_GROUP_ID = 1L;

	/** The Constant UPDATE_WORK_SUBESTIMATE_GROUP_ID. */
	private static final Long UPDATE_WORK_SUBESTIMATE_GROUP_ID = 2L;

	/** The Constant UPDATE_SOR_WOR_CATEGORY_ID. */
	private static final Long UPDATE_SOR_WOR_CATEGORY_ID = 2L;

	/** The Constant DEFAULT_SUBESTIMATE_NAME. */
	private static final String DEFAULT_SUBESTIMATE_NAME = "Subestimate Name.";

	/** The Constant UPDATE_SUBESTIMATE_NAME. */
	private static final String UPDATE_SUBESTIMATE_NAME = "This is updated Subestimate Name.";

	/** The Constant DEFAULT_WORKESTIMATE_ID. */
	private static final Long DEFAULT_WORKESTIMATE_ID = 1L;

	/** The Constant UPDATE_WORKESTIMATE_ID. */
	private static final Long UPDATE_WORKESTIMATE_ID = 2L;

	/** The sub estimate. */
	private static SubEstimate subEstimate;

	/** The sub estimate repository. */
	@Autowired
	private SubEstimateRepository subEstimateRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");
	}

	/**
	 * Creates the entity.
	 *
	 * @return the sub estimate
	 */
	public static SubEstimate createEntity() {
		SubEstimate subEstimate = new SubEstimate().areaWeightageCircle(DEFAULT_AREA_WEIGHTAGE_CIRCLE)
				.areaWeightageDescription(DEFAULT_AREA_WEIGHTAGE_DESCRIPTION).areaWeightageId(DEFAULT_AREA_WEIGHTAGE_ID)
				.estimateTotal(DEFAULT_ESTIMATE_TOTAL).sorWorCategoryId(DEFAULT_SOR_WOR_CATEGORY_ID)
				.subEstimateName(DEFAULT_SUBESTIMATE_NAME).workEstimateId(DEFAULT_WORKESTIMATE_ID)
				.completedYn(DEFAULT_COMPLETED_YN);
		subEstimate.setWorkSubEstimateGroupId(DEFAULT_WORK_SUBESTIMATE_GROUP_ID);

		return subEstimate;

	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest - Start");

		subEstimate = createEntity();

		SubEstimate testSubEstimate = subEstimateRepository.save(subEstimate);

		Assert.assertTrue(testSubEstimate.getId() > 0);
		Assert.assertEquals(testSubEstimate.getAreaWeightageCircle(), subEstimate.getAreaWeightageCircle());
		Assert.assertEquals(testSubEstimate.getSubEstimateName(), subEstimate.getSubEstimateName());
		Assert.assertEquals(testSubEstimate.getAreaWeightageDescription(), subEstimate.getAreaWeightageDescription());
		Assert.assertEquals(testSubEstimate.getAreaWeightageId(), subEstimate.getAreaWeightageId());
		Assert.assertEquals(testSubEstimate.getEstimateTotal(), subEstimate.getEstimateTotal());
		Assert.assertEquals(testSubEstimate.getSorWorCategoryId(), subEstimate.getSorWorCategoryId());
		Assert.assertEquals(testSubEstimate.getWorkEstimateId(), subEstimate.getWorkEstimateId());

		log.info("Test - createTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Update test.
	 */
	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - updateTest - Start");

		SubEstimate subEstimateDB = null;

		subEstimate = createEntity();
		SubEstimate testSubEstimate = subEstimateRepository.save(subEstimate);

		Optional<SubEstimate> subEstimateOpt = subEstimateRepository.findById(testSubEstimate.getId());
		if (subEstimateOpt.isPresent()) {
			subEstimateDB = subEstimateOpt.get();
		}
		subEstimateDB.areaWeightageCircle(UPDATE_AREA_WEIGHTAGE_CIRCLE)
				.areaWeightageDescription(UPDATE_AREA_WEIGHTAGE_DESCRIPTION).areaWeightageId(UPDATE_AREA_WEIGHTAGE_ID)
				.estimateTotal(UPDATE_ESTIMATE_TOTAL).sorWorCategoryId(UPDATE_SOR_WOR_CATEGORY_ID)
				.subEstimateName(UPDATE_SUBESTIMATE_NAME).workEstimateId(UPDATE_WORKESTIMATE_ID)
				.completedYn(UPDATE_COMPLETED_YN);
		subEstimateDB.setWorkSubEstimateGroupId(UPDATE_WORK_SUBESTIMATE_GROUP_ID);

		SubEstimate updatedSubEstimate = subEstimateRepository.save(subEstimateDB);

		Assert.assertEquals(updatedSubEstimate.getAreaWeightageDescription(),
				subEstimateDB.getAreaWeightageDescription());
		Assert.assertEquals(updatedSubEstimate.getSubEstimateName(), subEstimateDB.getSubEstimateName());
		Assert.assertEquals(updatedSubEstimate.getAreaWeightageCircle(), subEstimateDB.getAreaWeightageCircle());
		Assert.assertEquals(updatedSubEstimate.getAreaWeightageId(), subEstimateDB.getAreaWeightageId());
		Assert.assertEquals(updatedSubEstimate.getCompletedYn(), subEstimateDB.getCompletedYn());
		Assert.assertEquals(updatedSubEstimate.getEstimateTotal(), subEstimateDB.getEstimateTotal());
		Assert.assertEquals(updatedSubEstimate.getSorWorCategoryId(), subEstimateDB.getSorWorCategoryId());
		Assert.assertEquals(updatedSubEstimate.getWorkEstimateId(), subEstimateDB.getWorkEstimateId());
		Assert.assertEquals(updatedSubEstimate.getWorkSubEstimateGroupId(), subEstimateDB.getWorkSubEstimateGroupId());

		log.info("Test - updateTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Find all test.
	 */
	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("Test - findAllTest - Start");

		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();

		int NUMBER_OF_SUB_ESTIMATES = subEstimateList.size();

		log.info("Number Of Sub Estimate Item: " + NUMBER_OF_SUB_ESTIMATES);

		Assert.assertTrue(NUMBER_OF_SUB_ESTIMATES > 0);

		log.info("Test - findAllTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Find by id test.
	 */
	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByIdTest - Start");

		subEstimate = createEntity();
		SubEstimate testSubEstimate = subEstimateRepository.save(subEstimate);

		Optional<SubEstimate> subEstimateOpt = subEstimateRepository.findById(testSubEstimate.getId());

		log.info("Sub Estimate : " + subEstimateOpt);

		Assert.assertTrue(subEstimateOpt.isPresent());

		log.info("Test - findByIdTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Find by work estimate id and id test.
	 */
	@Test(priority = 5)
	public void findByWorkEstimateIdAndIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest - Start");
		subEstimate = createEntity();
		SubEstimate testSubEstimate = subEstimateRepository.save(subEstimate);

		Optional<SubEstimate> subEstimateOpt = subEstimateRepository.findByWorkEstimateIdAndId(DEFAULT_WORKESTIMATE_ID,
				testSubEstimate.getId());
		log.info("Sub Estimate : " + subEstimateOpt);
		Assert.assertTrue(subEstimateOpt.isPresent());
		log.info("Test - findByWorkEstimateIdAndIdTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Find by work estimate id and work sub estimate group id test.
	 */
	@Test(priority = 6)
	public void findByWorkEstimateIdAndWorkSubEstimateGroupIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest - Start");

		List<SubEstimate> subEstimateList = subEstimateRepository.findByWorkEstimateIdAndWorkSubEstimateGroupId(
				DEFAULT_WORKESTIMATE_ID, DEFAULT_WORK_SUBESTIMATE_GROUP_ID);
		log.info("Sub Estimate : " + subEstimateList);
		log.info("Test - findByWorkEstimateIdAndWorkSubEstimateGroupIdTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Find by work estimate id and id in test.
	 */
	@Test(priority = 7)
	public void findByWorkEstimateIdAndIdInTest() {
		log.info("==================================================================================");
		log.info("Test - findByWorkEstimateIdAndIdInTest - Start");
		subEstimate = createEntity();
		subEstimateRepository.save(subEstimate);

		final Long ID = 1L;
		final Long ID1 = 2L;
		List<Long> ids = new ArrayList<>();
		ids.add(ID);
		ids.add(ID1);
		List<SubEstimate> subEstimateList = subEstimateRepository.findByWorkEstimateIdAndIdIn(DEFAULT_WORKESTIMATE_ID,
				ids);
		int NUMBER_OF_SUB_ESTIMATES = subEstimateList.size();

		log.info("Number Of Sub Estimate Item: " + NUMBER_OF_SUB_ESTIMATES);

		Assert.assertTrue(NUMBER_OF_SUB_ESTIMATES > 0);
		log.info("Test - findByWorkEstimateIdAndIdInTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Delete test.
	 */
	@Test(priority = 8)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("Test - deleteTest - Start");

		Optional<SubEstimate> subEstimateOpt = subEstimateRepository.findById(subEstimate.getId());

		if (subEstimateOpt.isEmpty()) {
			Assert.assertTrue(true);
		}
		log.info("Test - deleteTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - SubEstimateRepositoryTest");
	}

}
