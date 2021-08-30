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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;

/**
 * The Class WorkEstimateItemLBDRepositoryTest.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkEstimateItemLBDRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateItemLBDRepositoryTest.class);

	/** The Constant workEstimateItemId. */
	private static final Long WORKESTIMATEITEMID = 101L;

	/** The Constant lbdParticulars. */
	private static final String LBDPARTICULARS = "Particulars--44";

	/** The Constant lbdNos. */
	private static final BigDecimal LBDNOS = new BigDecimal("7");

	/** The Constant lbdLength. */
	private static final BigDecimal LBDLENGTH = new BigDecimal("6");

	/** The Constant lbdLengthFormula. */
	private static final String LBDLENGTHFORMULA = "2+2*3";

	/** The Constant lbdBredth. */
	private static final BigDecimal LBDBREADTH = new BigDecimal("2");

	/** The Constant lbdBredthFormula. */
	private static final String LBDBREADTHFORMULA = "2+2*6";

	/** The Constant lbdDepth. */
	private static final BigDecimal LBDDEPTH = new BigDecimal("5");

	/** The Constant lbdDepthFormula. */
	private static final String LBDDEPTHFORMULA = "2+2*8";

	/** The Constant lbdQuantity. */
	private static final BigDecimal LBDQUANTITY = new BigDecimal("3");

	/** The Constant lbdTotal. */
	private static final BigDecimal LBDTOTAL = new BigDecimal("500");

	/** The Constant additionDeduction. */
	private static final LBDOperation ADDITIONDEDUCTION = LBDOperation.ADDITION;

	/** The Constant calculatedYn. */
	private static final Boolean CALCULATEDYN = true;

	private static final BigDecimal LBDBREDTH = null;

	/** The work estimate item LBD. */
	private static WorkEstimateItemLBD workEstimateItemLBD;

	/** The work estimate item LBD optional. */
	private static Optional<WorkEstimateItemLBD> workEstimateItemLBDOptional;

	/** The work estimate item LBDDTO. */
	private static WorkEstimateItemLBDDTO workEstimateItemLBDDTO;

	/** The work estimate item LBD repository. */
	@Autowired
	private WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		System.err.println("-----init--------");

	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 *
	 * @return the WorkEstimateItemLBD
	 */
	public static WorkEstimateItemLBD createEntity() {

		workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setWorkEstimateItemId(WORKESTIMATEITEMID);
		workEstimateItemLBD.setLbdParticulars(LBDPARTICULARS);
		workEstimateItemLBD.setLbdNos(LBDNOS);
		workEstimateItemLBD.setLbdLength(LBDLENGTH);
		workEstimateItemLBD.setLbdLengthFormula(LBDLENGTHFORMULA);
		workEstimateItemLBD.setLbdBredth(LBDBREDTH);
		workEstimateItemLBD.setLbdBredthFormula(LBDBREADTHFORMULA);
		workEstimateItemLBD.setLbdDepth(LBDDEPTH);
		workEstimateItemLBD.setLbdDepthFormula(LBDDEPTHFORMULA);
		workEstimateItemLBD.setLbdQuantity(LBDQUANTITY);
		workEstimateItemLBD.setLbdTotal(LBDTOTAL);
		workEstimateItemLBD.setAdditionDeduction(ADDITIONDEDUCTION);
		workEstimateItemLBD.setCalculatedYn(CALCULATEDYN);
		return workEstimateItemLBD;

	}

	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test Start- create Start");
		workEstimateItemLBD = createEntity();
		WorkEstimateItemLBD testWorkEstimateItemLBD = workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Assert.assertEquals(testWorkEstimateItemLBD.getWorkEstimateItemId(),
				workEstimateItemLBD.getWorkEstimateItemId());
		log.info("==================================================================================");
		log.info("Test End- create End");
	}

	/**
	 * Partial Update test.
	 */
	@Test(priority = 2)
	public void updateWorkEstimateItemLBDTest() {
		log.info("==================================================================================");
		log.info("Test Start- partial Update");
		workEstimateItemLBDOptional = workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId());
		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();

		workEstimateItemLBD.setId(workEstimateItemLBDOptional.get().getId());
		workEstimateItemLBD.setWorkEstimateItemId(201L);
		workEstimateItemLBD.setLbdParticulars(workEstimateItemLBDOptional.get().getLbdParticulars());
		workEstimateItemLBD.setLbdNos(new BigDecimal("5"));
		workEstimateItemLBD.setLbdLength(new BigDecimal("15"));
		workEstimateItemLBD.setLbdLengthFormula(workEstimateItemLBDOptional.get().getLbdLengthFormula());
		workEstimateItemLBD.setLbdBredth(new BigDecimal("25"));
		workEstimateItemLBD.setLbdBredthFormula(workEstimateItemLBDOptional.get().getLbdBredthFormula());
		workEstimateItemLBD.setLbdDepth(new BigDecimal("8"));
		workEstimateItemLBD.setLbdDepthFormula(workEstimateItemLBDOptional.get().getLbdDepthFormula());
		workEstimateItemLBD.setLbdQuantity(new BigDecimal("300"));
		workEstimateItemLBD.setLbdTotal(new BigDecimal("325"));
		workEstimateItemLBD.setAdditionDeduction(workEstimateItemLBDOptional.get().getAdditionDeduction());
		workEstimateItemLBD.setCalculatedYn(true);

		WorkEstimateItemLBD testWorkEstimateItemLBD = workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Assert.assertEquals(testWorkEstimateItemLBD.getWorkEstimateItemId(),
				workEstimateItemLBD.getWorkEstimateItemId());
		log.info("==================================================================================");
		log.info("Test End- partial Update");
	}

	/**
	 * Get All Work Estimate Item.
	 *
	 * @return the all work estimate item LBDS test
	 */
	@Test(priority = 3)
	public void getAllWorkEstimateItemLBDSTest() {
		log.info("==================================================================================");
		log.info("Test Start- Get All getAllWorkEstimateItem");
		List<WorkEstimateItemLBD> workEstimateItemLBDList = workEstimateItemLBDRepository.findAll();
		Assert.assertNotNull(workEstimateItemLBDList);
		log.info("==================================================================================");
		log.info("Test End- Get All getAllWorkEstimateItem");
	}

	/**
	 * Get Work Estimate Item By Id.
	 *
	 * @return the work estimate item LBD test
	 */
	@Test(priority = 4)
	public void getWorkEstimateItemLBDTest() {
		log.info("==================================================================================");
		log.info("Test Start- Get  getAllWorkEstimateItem By Id");
		workEstimateItemLBDOptional = workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId());
		Optional<WorkEstimateItemLBD> workEstimateItemLBDList = workEstimateItemLBDRepository
				.findById(workEstimateItemLBDOptional.get().getId());
		Assert.assertNotNull(workEstimateItemLBDList);
		log.info("==================================================================================");
		log.info("Test End- Get getAllWorkEstimateItem By Id");
	}

	/**
	 * Delete work estimate item LBD test.
	 */
	@Test(priority = 5)
	public void deleteWorkEstimateItemLBDTest() {
		log.info("==================================================================================");
		log.info("Test Start- deleteWorkEstimateItemLBDTest By Id");
		workEstimateItemLBDOptional = workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId());
		workEstimateItemLBDRepository.deleteById(workEstimateItemLBDOptional.get().getId());
		log.info("==================================================================================");
		log.info("Test End- deleteWorkEstimateItemLBDTest By Id");
	}

}
