package com.dxc.eproc.estimate.repository;

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
import com.dxc.eproc.estimate.model.OverHead;

// TODO: Auto-generated Javadoc
/**
 * The Class OverHeadRepositoryTest.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class OverHeadRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(OverHeadRepositoryTest.class);
	
	/** The Constant OVER_HEAD_NAME. */
	private static final String OVER_HEAD_NAME = "Environment Impacts";
	
    /** The Constant OVER_HEAD_TYPE. */
    private static final OverHeadType OVER_HEAD_TYPE = OverHeadType.NORMAL;
		
	/** The Constant ACTIVE_YN. */
	private static final Boolean ACTIVE_YN =true;
	
	/** The over head. */
	private static OverHead overHead;
	
	/** The over head repository. */
	@Autowired
	private  OverHeadRepository overHeadRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public  void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
			
		overHead = new OverHead();
		overHead = createEntity();
		overHead= overHeadRepository.save(overHead);

	}
	
	/**
	 * Creates the entity.
	 *
	 * @return the over head
	 */
	public  OverHead createEntity() {
		OverHead overHead = new OverHead();
		overHead.setOverHeadName(OVER_HEAD_NAME);
		overHead.setOverHeadType(OVER_HEAD_TYPE);
		overHead.activeYn(ACTIVE_YN);
		return overHead;
	}
	
	/**
	 * Creates the test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - create Start");
	
		Assert.assertEquals(overHead.getOverHeadName(), overHead.getOverHeadName());
		Assert.assertEquals(overHead.getOverHeadType(), overHead.getOverHeadType());
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
		
		String name = overHead.getOverHeadName();		
		overHead = overHeadRepository.findById(overHead.getId()).get();
		
		overHead.setOverHeadName("Environment-Updated");
		overHead.setOverHeadType(OVER_HEAD_TYPE);
		
		OverHead testOverHead = overHeadRepository.save(overHead);
		String afterOverHeadName = testOverHead.getOverHeadName();
		Assertions.assertThat(name).isNotEqualTo(afterOverHeadName);
		log.info("==================================================================================");
		log.info("Test - Update End");
	}

	/**
	 * Find all by active yn test.
	 */
	//findAllByActiveYn
	@Test(priority = 3)
	public void findAllByActiveYnTest() {
		Page<OverHead> result = overHeadRepository.findAllByActiveYn(true,PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		log.info("findAllByActiveYnTest successful");
	}
	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - OverHeadRepositoryTest");

	}
}
