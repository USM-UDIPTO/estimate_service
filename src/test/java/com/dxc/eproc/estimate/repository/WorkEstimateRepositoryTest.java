package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.WorkEstimate;

@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkEstimateRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateRepositoryTest.class);

	/** The Constant DEPT_ID. */
	private static final Long DEPT_ID = 1L;

	/** The Constant DEPT_ID. */
	private static final Long UPDATED_DEPT_ID = 2L;

	/** The Constant LOCATION_ID. */
	private static final Long LOCATION_ID = 1L;

	/** The Constant LOCATION_ID. */
	private static final Long UPDATED_LOCATION_ID = 2L;

	/** The Constant FILE_NUMBER. */
	private static final String FILE_NUMBER = "KPWD/AEE/MNG/CRF/2009-10/1";

	/** The Constant DEFAULT_STATUS. */
	private static final WorkEstimateStatus DEFAULT_STATUS = WorkEstimateStatus.DRAFT;

	/** The Constant WORKESTIMATE_NUMBER. */
	private static final String WORKESTIMATE_NUMBER = "TPWD/2021-22/BB/WORK_ESTIMATE_1";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "This is Road Contruction-phase-1";

	/** The Constant DESCRIPTION. */
	private static final String UPDATED_DESCRIPTION = "This is Road Contruction-phase-1-updated";

	/** The Constant NAME. */
	private static final String NAME = "Road Construction";

	/** The Constant NAME. */
	private static final String UPDATED_NAME = "Road Construction-Updated";

	/** The Constant ESTIMATE_TYPE_ID. */
	private static final Long ESTIMATE_TYPE_ID = 1L;

	/** The Constant UPDATED_ESTIMATE_TYPE_ID. */
	private static final Long UPDATED_ESTIMATE_TYPE_ID = 2L;

	/** The Constant WORK_TYPE_ID. */
	private static final Long WORK_TYPE_ID = 1L;

	/** The Constant UPDATED_WORK_TYPE_ID. */
	private static final Long UPDATED_WORK_TYPE_ID = 2L;

	/** The Constant WORK_TYPE_ID. */
	private static final Long WORK_CATEGORY_ID = 1L;

	/** The Constant WORK_TYPE_ID. */
	private static final Long UPDATED_WORK_CATEGORY_ID = 2L;

	/** The Constant UPDATED_WORK_CATEGORY_ID. */
	private static final Long WORK_CATEGORY_ATTRIBUTE = 1L;

	/** The Constant UPDATED_WORK_CATEGORY_ATTRIBUTE. */
	private static final Long UPDATED_WORK_CATEGORY_ATTRIBUTE = 2L;

	/** The Constant WORK_CATEGORY_ATTRIBUTE_VALUE. */
	private static final BigDecimal WORK_CATEGORY_ATTRIBUTE_VALUE = BigDecimal.valueOf(100000.00);

	/** The Constant UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE. */
	private static final BigDecimal UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE = BigDecimal.valueOf(200000.00);

	/** The Constant APPROVED_BUDGET_YN. */
	private static final Boolean APPROVED_BUDGET_YN = true;

	/** The Constant HKRDB_FUNDED_YN. */
	private static final Boolean HKRDB_FUNDED_YN = true;

	/** The Constant PROVISIONAL_AMOUNT. */
	private static final BigDecimal PROVISIONAL_AMOUNT = BigDecimal.valueOf(100000.00);

	/** The Constant UPDATED_PROVISIONAL_AMOUNT. */
	private static final BigDecimal UPDATED_PROVISIONAL_AMOUNT = BigDecimal.valueOf(200000.00);

	/** The Constant GRANT_ALLOCATED_AMOUNT. */
	private static final BigDecimal GRANT_ALLOCATED_AMOUNT = BigDecimal.valueOf(100000.00);

	/** The Constant UPDATED_GRANT_ALLOCATED_AMOUNT. */
	private static final BigDecimal UPDATED_GRANT_ALLOCATED_AMOUNT = BigDecimal.valueOf(200000.00);

	/** The Constant DOCUMENT_REFERENCE. */
	private static final String DOCUMENT_REFERENCE = "2021-22";

	/** The Constant UPDATED_DOCUMENT_REFERENCE. */
	private static final String UPDATED_DOCUMENT_REFERENCE = "2022-23";

	/** The Constant HOA. */
	private static final String HOA = "SSSAEE";

	/** The Constant UPDATED_HOA. */
	private static final String UPDATED_HOA = "SSAEE";

	/** The Constant SCHEMEID. */
	private static final Long SCHEMEID = 1L;

	/** The Constant UPDATED_SCHEMEID. */
	private static final Long UPDATED_SCHEMEID = 2L;

	private static WorkEstimate workEstimate;

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
		workEstimate = createEntity();
		workEstimate = workEstimateRepository.save(workEstimate);

	}

	public WorkEstimate createEntity() {
		WorkEstimate workEstimate = new WorkEstimate();
		workEstimate.setDeptId(DEPT_ID);
		workEstimate.setLocationId(LOCATION_ID);
		workEstimate.setFileNumber(FILE_NUMBER);
		workEstimate.setStatus(DEFAULT_STATUS);
		workEstimate.setWorkEstimateNumber(WORKESTIMATE_NUMBER);
		workEstimate.setName(NAME);
		workEstimate.setDescription(DESCRIPTION);
		workEstimate.setEstimateTypeId(ESTIMATE_TYPE_ID);
		workEstimate.setWorkTypeId(WORK_TYPE_ID);
		workEstimate.workCategoryId(WORK_CATEGORY_ID);
		workEstimate.workCategoryAttribute(WORK_CATEGORY_ATTRIBUTE);
		workEstimate.setWorkCategoryAttributeValue(WORK_CATEGORY_ATTRIBUTE_VALUE);
		workEstimate.setApprovedBudgetYn(APPROVED_BUDGET_YN);
		workEstimate.setProvisionalAmount(PROVISIONAL_AMOUNT);
		workEstimate.setGrantAllocatedAmount(GRANT_ALLOCATED_AMOUNT);
		workEstimate.setHeadOfAccount(HOA);
		workEstimate.setDocumentReference(DOCUMENT_REFERENCE);
		workEstimate.setHkrdbFundedYn(HKRDB_FUNDED_YN);
		workEstimate.setSchemeId(SCHEMEID);

		return workEstimate;
	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - create Start");

		Assert.assertEquals(workEstimate.getDeptId(), workEstimate.getDeptId());
		Assert.assertEquals(workEstimate.getFileNumber(), workEstimate.getFileNumber());
		Assert.assertEquals(workEstimate.getStatus(), workEstimate.getStatus());
		Assert.assertEquals(workEstimate.getWorkEstimateNumber(), workEstimate.getWorkEstimateNumber());
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
		String workEstimateNumber = workEstimate.getWorkEstimateNumber();
		WorkEstimateStatus status = workEstimate.getStatus();
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		workEstimate.setDeptId(UPDATED_DEPT_ID);
		workEstimate.setLocationId(UPDATED_LOCATION_ID);
		workEstimate.setName(UPDATED_NAME);
		workEstimate.setDescription(UPDATED_DESCRIPTION);
		workEstimate.setEstimateTypeId(UPDATED_ESTIMATE_TYPE_ID);
		workEstimate.setWorkTypeId(UPDATED_WORK_TYPE_ID);
		workEstimate.workCategoryId(UPDATED_WORK_CATEGORY_ID);
		workEstimate.workCategoryAttribute(UPDATED_WORK_CATEGORY_ATTRIBUTE);
		workEstimate.setWorkCategoryAttributeValue(UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE);
		workEstimate.setApprovedBudgetYn(APPROVED_BUDGET_YN);
		workEstimate.setProvisionalAmount(UPDATED_PROVISIONAL_AMOUNT);
		workEstimate.setGrantAllocatedAmount(UPDATED_GRANT_ALLOCATED_AMOUNT);
		workEstimate.setHeadOfAccount(UPDATED_HOA);
		workEstimate.setDocumentReference(UPDATED_DOCUMENT_REFERENCE);
		workEstimate.setHkrdbFundedYn(HKRDB_FUNDED_YN);
		workEstimate.setSchemeId(UPDATED_SCHEMEID);

		WorkEstimate testWorkEstimate = workEstimateRepository.save(workEstimate);
		String afterworkEstimateNumber = testWorkEstimate.getWorkEstimateNumber();
		WorkEstimateStatus afterstatus = testWorkEstimate.getStatus();
		Assertions.assertThat(workEstimateNumber).isEqualTo(afterworkEstimateNumber);
		Assertions.assertThat(status).isEqualTo(afterstatus);
		log.info("==================================================================================");
		log.info("Test - Update End");
	}

	/**
	 * Find by id test.
	 */
	@Test(priority = 3)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByIdTest Start");
		Optional<WorkEstimate> workEstimateOpt = workEstimateRepository.findById(workEstimate.getId());
		Assert.assertTrue(workEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findByIdTest End");
	}

	/**
	 * Find by DEPT_ID and FILE NUMBER test.
	 */
	@Test(priority = 4)
	public void findByDeptIdAndFileNumberTest() {

		log.info("==================================================================================");
		log.info("Test - findByDeptIdAndFileNumberTest Start");
		Optional<WorkEstimate> workEstimateOpt = workEstimateRepository
				.findByDeptIdAndFileNumber(workEstimate.getDeptId(), workEstimate.getFileNumber());
		Assert.assertTrue(workEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findByDeptIdAndFileNumberTest End");

	}

	/**
	 * Find by SCHEMEID test.
	 */
	@Test(priority = 5)
	public void findBySchemeIdTest() {

		log.info("==================================================================================");
		log.info("Test - findBySchemeIdTest Start");
		Optional<WorkEstimate> workEstimateOpt = workEstimateRepository.findBySchemeId(workEstimate.getSchemeId());
		Assert.assertTrue(workEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findBySchemeIdTest End");

	}

	/**
	 * Find by DEPT_ID and FILE NUMBER and IDNOT test.
	 */
	@Test(priority = 6)
	public void findByDeptIdAndFileNumberAndIdNotTest() {

		log.info("==================================================================================");
		log.info("Test - findByDeptIdAndFileNumberAndIdNotTest Start");

		Optional<WorkEstimate> workEstimateOpt = workEstimateRepository.findByDeptIdAndFileNumberAndIdNot(
				workEstimate.getDeptId(), workEstimate.getFileNumber(), workEstimate.getId());
		Assert.assertFalse(workEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findByDeptIdAndFileNumberAndIdNotTest End");

	}

	/**
	 * Find by SCHEMEID test.
	 */
	@Test(priority = 7)
	public void findBySchemeIdAndIdNotTest() {

		log.info("==================================================================================");
		log.info("Test - findBySchemeIdAndIdNotTest Start");
		Optional<WorkEstimate> workEstimateOpt = workEstimateRepository
				.findBySchemeIdAndIdNot(workEstimate.getSchemeId(), workEstimate.getId());
		Assert.assertFalse(workEstimateOpt.isPresent());
		log.info("==================================================================================");
		log.info("Test - findBySchemeIdAndIdNotTest End");

	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkEstimateRepositoryTest");

	}

}
