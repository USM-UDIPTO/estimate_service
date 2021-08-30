package com.dxc.eproc.estimate.repository;

import java.util.List;
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

import com.dxc.eproc.estimate.model.DeptEstimateType;

@SpringBootTest
@ActiveProfiles("test")
public class DeptEstimateTypeRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(DeptEstimateTypeRepositoryTest.class);

	private static DeptEstimateType deptEstimateType;

	@Autowired
	DeptEstimateTypeRepository deptEstimateTypeRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");

	}

	private DeptEstimateType createDeptEstimateType() {

		DeptEstimateType deptEstimateType = new DeptEstimateType().flowType("flowType").deptId(1L).estimateTypeId(1L)
				.activeYn(true);

		return deptEstimateType;
	}

	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest - Start");

		deptEstimateType = createDeptEstimateType();
		DeptEstimateType testDeptEstimateType = deptEstimateTypeRepository.save(deptEstimateType);

		Assert.assertTrue(testDeptEstimateType.getId() > 0);
		Assert.assertEquals(testDeptEstimateType.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(testDeptEstimateType.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(testDeptEstimateType.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(testDeptEstimateType.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - createTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 1)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - updateTest - Start");

		DeptEstimateType deptEstimateTypeDB = null;

		deptEstimateType = createDeptEstimateType();
		DeptEstimateType testDeptEstimateType = deptEstimateTypeRepository.save(deptEstimateType);

		Optional<DeptEstimateType> deptEstimateTypeOpt = deptEstimateTypeRepository
				.findById(testDeptEstimateType.getId());
		if (deptEstimateTypeOpt.isPresent()) {
			deptEstimateTypeDB = deptEstimateTypeOpt.get();
		}
		deptEstimateTypeDB.flowType("flowType1").deptId(1L).estimateTypeId(1L).activeYn(true);

		DeptEstimateType updatedDeptEstimateType = deptEstimateTypeRepository.save(deptEstimateTypeDB);

		Assert.assertEquals(updatedDeptEstimateType.getId(), deptEstimateTypeDB.getId());
		Assert.assertEquals(updatedDeptEstimateType.getFlowType(), deptEstimateTypeDB.getFlowType());
		Assert.assertEquals(updatedDeptEstimateType.getDeptId(), deptEstimateTypeDB.getDeptId());
		Assert.assertEquals(updatedDeptEstimateType.getEstimateTypeId(), deptEstimateTypeDB.getEstimateTypeId());
		Assert.assertEquals(updatedDeptEstimateType.getActiveYn(), deptEstimateTypeDB.getActiveYn());

		log.info("Test - updateTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("Test - findAllTest - Start");

		deptEstimateType = createDeptEstimateType();
		deptEstimateTypeRepository.save(deptEstimateType);

		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();

		int NUMBER_OF_DEPT_ESTIMATE_TYPES = deptEstimateTypeList.size();

		log.info("Number Of DeptEstimate types: " + NUMBER_OF_DEPT_ESTIMATE_TYPES);

		Assert.assertTrue(NUMBER_OF_DEPT_ESTIMATE_TYPES > 0);

		log.info("Test - findAllTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByIdTest - Start");

		deptEstimateType = createDeptEstimateType();
		DeptEstimateType testDeptEstimateType = deptEstimateTypeRepository.save(deptEstimateType);

		Optional<DeptEstimateType> deptEstimateTypeOpt = deptEstimateTypeRepository
				.findById(testDeptEstimateType.getId());

		log.info("DeptEstimateType  : " + deptEstimateTypeOpt);

		Assert.assertTrue(deptEstimateTypeOpt.isPresent());

		log.info("Test - findByIdTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 5)
	public void findAllByActiveYnTest() {
		Page<DeptEstimateType> result = deptEstimateTypeRepository.findAllByActiveYn(false, PageRequest.of(0, 5));
		Assert.assertTrue(result.isEmpty());
		log.info("findAllByActiveYnTest successful");
	}

	@Test(priority = 6)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("Test - deleteTest - Start");

		deptEstimateType = deptEstimateTypeRepository.findById(deptEstimateType.getId()).get();
		deptEstimateTypeRepository.delete(deptEstimateType);

		Optional<DeptEstimateType> deptEstimateTypeOpt = deptEstimateTypeRepository.findById(deptEstimateType.getId());

		if (deptEstimateTypeOpt.isEmpty()) {
			Assert.assertTrue(true);
		}
		log.info("Test - deleteTest - End");
		log.info("==================================================================================");
	}

}
