package com.dxc.eproc.estimate.repository;

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
import com.dxc.eproc.estimate.model.WorkType;

@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkTypeRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkTypeRepositoryTest.class);

	private static WorkType workType;

	private static Optional<WorkType> workTypeOptional;

	@Autowired
	private WorkTypeRepository workTypeRepository;

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

	public static WorkType createEntity() {

		workType = new WorkType();
		workType.setId(1L);
		workType.setWorkTypeName("srs");
		workType.setWorkTypeValue("dsc001");
		workType.setValueType("frs");
		workType.setActiveYn(true);
		return workType;

	}

	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test Start- create Start");
		workType = createEntity();
		WorkType testworkType = workTypeRepository.save(workType);
		Assert.assertEquals(testworkType.getId(), workType.getId());
		log.info("==================================================================================");
		log.info("Test End- create End");
	}

	@Test(priority = 2)
	public void updateWorkTypeTest() {
		log.info("==================================================================================");
		log.info("Test Start- partial Update");
		WorkType workType = createEntity();
		workTypeRepository.save(workType);
		workTypeOptional = workTypeRepository.findById(workType.getId());
		workType.setId(workTypeOptional.get().getId());
		workType.setWorkTypeName(workTypeOptional.get().getWorkTypeName());
		workType.setWorkTypeValue(workTypeOptional.get().getWorkTypeValue());
		workType.setValueType(workTypeOptional.get().getValueType());
		workType.setActiveYn(workTypeOptional.get().getActiveYn());

		WorkType testworkType = workTypeRepository.save(workType);
		Assert.assertEquals(testworkType.getId(), workType.getId());
		log.info("==================================================================================");
		log.info("Test End- partial Update");
	}

	@Test(priority = 3)
	public void getAllWorkTypesTest() {
		log.info("==================================================================================");
		log.info("Test Start- getAllWorkTypesTest");
		List<WorkType> workTypeList = workTypeRepository.findAll();
		Assert.assertNotNull(workTypeList);
		log.info("==================================================================================");
		log.info("Test End- getAllWorkTypesTest");
	}

	@Test(priority = 4)
	public void getWorkTypeTest() {
		log.info("==================================================================================");
		log.info("Test Start-   getWorkTypeTest By Id");
		workTypeOptional = workTypeRepository.findById(workType.getId());
		Optional<WorkType> workTypeList = workTypeRepository.findById(workTypeOptional.get().getId());
		Assert.assertNotNull(workTypeList);
		log.info("==================================================================================");
		log.info("Test End-  getWorkTypeTest By Id");
	}

	@Test(priority = 5)
	public void deleteWorkTypeTest() {
		log.info("==================================================================================");
		log.info("Test Start- deleteWorkTypeTest By Id");
		workTypeOptional = workTypeRepository.findById(workType.getId());
		workTypeRepository.deleteById(workTypeOptional.get().getId());
		log.info("==================================================================================");
		log.info("Test End- deleteWorkTypeTest By Id");
	}
}
