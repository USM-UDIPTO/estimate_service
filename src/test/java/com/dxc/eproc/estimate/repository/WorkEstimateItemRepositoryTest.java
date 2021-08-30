package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
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
import com.dxc.eproc.estimate.model.WorkEstimateItem;

/**
 * The Class WorkEstimateItemRepositoryTest
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkEstimateItemRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateItemRepositoryTest.class);

	/** The Constant DEFAULT_SUBESTIMATE_ID. */
	private static final Long DEFAULT_SUBESTIMATE_ID = 1L;

	/** The Constant UPDATE_SUBESTIMATE_ID. */
	private static final Long UPDATE_SUBESTIMATE_ID = 2L;

	/** The Constant DEFAULT_WORK_SORITEM_ID. */
	private static final Long DEFAULT_WORK_SORITEM_ID = 1L;

	/** The Constant UPDATE_WORK_SORITEM_ID. */
	private static final Long UPDATE_WORK_SORITEM_ID = 2L;

	/** The Constant DEFAULT_WORK_CATEGORY_ID. */
	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;

	/** The Constant UPDATE_WORK_CATEGORY_ID. */
	private static final Long UPDATE_WORK_CATEGORY_ID = 2L;

	/** The Constant DEFAULT_ENTRY_ORDER. */
	private static final Integer DEFAULT_ENTRY_ORDER = 0;

	/** The Constant UPDATE_ENTRY_ORDER. */
	private static final Integer UPDATE_ENTRY_ORDER = 1;

	/** The Constant DEFAULT_UOM_ID. */
	private static final Long DEFAULT_UOM_ID = 1L;

	/** The Constant UPDATE_UOM_ID. */
	private static final Long UPDATE_UOM_ID = 2L;

	/** The Constant DEFAULT_ITEM_CODE. */
	private static final String DEFAULT_ITEM_CODE = "ITEM_CODE_001";

	/** The Constant UPDATE_ITEM_CODE. */
	private static final String UPDATE_ITEM_CODE = "ITEM_CODE_002";

	/** The Constant DEFAULT_DESCRIPTION. */
	private static final String DEFAULT_DESCRIPTION = "This is item description.";

	/** The Constant UPDATE_DESCRIPTION. */
	private static final String UPDATE_DESCRIPTION = "This is updated item description.";

	/** The Constant DEFAULT_BASE_RATE. */
	private static final BigDecimal DEFAULT_BASE_RATE = BigDecimal.valueOf(12.0000);

	/** The Constant UPDATE_DESCRIPTION. */
	private static final BigDecimal UPDATE_BASE_RATE = BigDecimal.valueOf(14.0000);

	/** The Constant DEFAULT_FINAL_RATE. */
	private static final BigDecimal DEFAULT_FINAL_RATE = BigDecimal.valueOf(12.0000);

	/** The Constant UPDATE_FINAL_RATE. */
	private static final BigDecimal UPDATE_FINAL_RATE = BigDecimal.valueOf(14.0000);

	/** The Constant DEFAULT_QUANTITY. */
	private static final BigDecimal DEFAULT_QUANTITY = BigDecimal.valueOf(1.0000);

	/** The Constant UPDATE_QUANTITY. */
	private static final BigDecimal UPDATE_QUANTITY = BigDecimal.valueOf(5.0000);

	/** The Constant DEFAULT_FLOOR_NUMBER. */
	private static final Integer DEFAULT_FLOOR_NUMBER = 12;

	/** The Constant UPDATE_FLOOR_NUMBER. */
	private static final Integer UPDATE_FLOOR_NUMBER = 10;

	/** The work estimate item. */
	private static WorkEstimateItem workEstimateItem;

	/** The work estimate item repository. */
	@Autowired
	private WorkEstimateItemRepository workEstimateItemRepository;

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
	 * Create an entity for this test. This is a static method, as tests for other
	 * entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @return the work estimate item
	 */
	public static WorkEstimateItem createEntity() {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem().subEstimateId(DEFAULT_SUBESTIMATE_ID)
				.catWorkSorItemId(DEFAULT_WORK_SORITEM_ID).categoryId(DEFAULT_WORK_CATEGORY_ID)
				.entryOrder(DEFAULT_ENTRY_ORDER).uomId(DEFAULT_UOM_ID).itemCode(DEFAULT_ITEM_CODE)
				.description(DEFAULT_DESCRIPTION).baseRate(DEFAULT_BASE_RATE).finalRate(DEFAULT_FINAL_RATE)
				.quantity(DEFAULT_QUANTITY).floorNumber(DEFAULT_FLOOR_NUMBER);

		return workEstimateItem;
	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest - Start");

		workEstimateItem = createEntity();

		WorkEstimateItem testWorkEstimateItem = workEstimateItemRepository.save(workEstimateItem);

		Assert.assertTrue(testWorkEstimateItem.getId() > 0);
		Assert.assertEquals(testWorkEstimateItem.getSubEstimateId(), workEstimateItem.getSubEstimateId());
		Assert.assertEquals(testWorkEstimateItem.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(testWorkEstimateItem.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(testWorkEstimateItem.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(testWorkEstimateItem.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(testWorkEstimateItem.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(testWorkEstimateItem.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(testWorkEstimateItem.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(testWorkEstimateItem.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(testWorkEstimateItem.getQuantity(), workEstimateItem.getQuantity());
		Assert.assertEquals(testWorkEstimateItem.getFloorNumber(), workEstimateItem.getFloorNumber());

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

		WorkEstimateItem workEstimateItemDB = null;

		Optional<WorkEstimateItem> workEstimateItemOpt = workEstimateItemRepository.findById(workEstimateItem.getId());
		if (workEstimateItemOpt.isPresent()) {
			workEstimateItemDB = workEstimateItemOpt.get();
		}
		workEstimateItemDB.subEstimateId(UPDATE_SUBESTIMATE_ID).catWorkSorItemId(UPDATE_WORK_SORITEM_ID)
				.categoryId(UPDATE_WORK_CATEGORY_ID).entryOrder(UPDATE_ENTRY_ORDER).uomId(UPDATE_UOM_ID)
				.itemCode(UPDATE_ITEM_CODE).description(UPDATE_DESCRIPTION).baseRate(UPDATE_BASE_RATE)
				.finalRate(UPDATE_FINAL_RATE).quantity(UPDATE_QUANTITY).floorNumber(UPDATE_FLOOR_NUMBER);

		WorkEstimateItem updatedWorkEstimateItem = workEstimateItemRepository.save(workEstimateItemDB);

		Assert.assertEquals(updatedWorkEstimateItem.getId(), workEstimateItemDB.getId());
		Assert.assertEquals(updatedWorkEstimateItem.getSubEstimateId(), workEstimateItemDB.getSubEstimateId());
		Assert.assertEquals(updatedWorkEstimateItem.getCatWorkSorItemId(), workEstimateItemDB.getCatWorkSorItemId());
		Assert.assertEquals(updatedWorkEstimateItem.getCategoryId(), workEstimateItemDB.getCategoryId());
		Assert.assertEquals(updatedWorkEstimateItem.getEntryOrder(), workEstimateItemDB.getEntryOrder());
		Assert.assertEquals(updatedWorkEstimateItem.getUomId(), workEstimateItemDB.getUomId());
		Assert.assertEquals(updatedWorkEstimateItem.getItemCode(), workEstimateItemDB.getItemCode());
		Assert.assertEquals(updatedWorkEstimateItem.getDescription(), workEstimateItemDB.getDescription());
		Assert.assertEquals(updatedWorkEstimateItem.getBaseRate(), workEstimateItemDB.getBaseRate());
		Assert.assertEquals(updatedWorkEstimateItem.getFinalRate(), workEstimateItemDB.getFinalRate());
		Assert.assertEquals(updatedWorkEstimateItem.getQuantity(), workEstimateItemDB.getQuantity());
		Assert.assertEquals(updatedWorkEstimateItem.getFloorNumber(), workEstimateItemDB.getFloorNumber());

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

		List<WorkEstimateItem> WorkEstimateItemList = workEstimateItemRepository.findAll();

		int NUMBER_OF_WORK_ESTIMATE_ITEM = WorkEstimateItemList.size();

		log.info("Number Of Work Estimate Item: " + NUMBER_OF_WORK_ESTIMATE_ITEM);

		Assert.assertTrue(NUMBER_OF_WORK_ESTIMATE_ITEM > 0);

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

		Optional<WorkEstimateItem> workEstimateItemOpt = workEstimateItemRepository.findById(workEstimateItem.getId());

		log.info("Work Estimate Item : " + workEstimateItemOpt);

		Assert.assertTrue(workEstimateItemOpt.isPresent());

		log.info("Test - findByIdTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 5)
	public void findBySubEstimateIdAndIdTest() {
		log.info("==================================================================================");
		log.info("Test - findBySubEstimateIdAndIdTest - Start");

		WorkEstimateItem tempWorkEstimateItem = workEstimateItemRepository.findById(workEstimateItem.getId()).get();
		tempWorkEstimateItem.setSubEstimateId(1L);
		workEstimateItemRepository.save(tempWorkEstimateItem);

		Optional<WorkEstimateItem> WorkEstimateItemOpt = workEstimateItemRepository
				.findBySubEstimateIdAndId(tempWorkEstimateItem.getSubEstimateId(), tempWorkEstimateItem.getId());

		Assert.assertTrue(WorkEstimateItemOpt.isPresent());

		log.info("Test - findBySubEstimateIdAndIdTest - End");
		log.info("==================================================================================");
	}

	/**
	 * Delete test.
	 */
	@Test(priority = 6)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("Test - deleteTest - Start");

		workEstimateItem = workEstimateItemRepository.findById(workEstimateItem.getId()).get();
		workEstimateItemRepository.delete(workEstimateItem);

		Optional<WorkEstimateItem> workEstimateItemOpt = workEstimateItemRepository.findById(workEstimateItem.getId());

		if (workEstimateItemOpt.isEmpty()) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("Work Estimate Item is not Deleted.");
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
		log.info("This is executed after once Per Test Class - WorkEstimateItemRepositoryTest");
	}
}
