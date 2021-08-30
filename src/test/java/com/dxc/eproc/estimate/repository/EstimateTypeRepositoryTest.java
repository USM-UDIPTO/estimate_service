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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.EstimateType;

@SpringBootTest
@ActiveProfiles("test")
public class EstimateTypeRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(EstimateTypeRepositoryTest.class);

	private static EstimateType estimateType;

	@Autowired
	EstimateTypeRepository estimateTypeRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");

	}

	private EstimateType createEstimateType() {

		EstimateType estimateType = new EstimateType().estimateTypeValue("estimateType").valueType("valueType")
				.activeYn(true);

		return estimateType;
	}

	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest - Start");

		estimateType = createEstimateType();
		EstimateType testEstimateType = estimateTypeRepository.save(estimateType);

		Assert.assertTrue(testEstimateType.getId() > 0);
		Assert.assertEquals(testEstimateType.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(testEstimateType.getValueType(), estimateType.getValueType());
		Assert.assertEquals(testEstimateType.getActiveYn(), estimateType.getActiveYn());

		log.info("Test - createTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - updateTest - Start");

		EstimateType estimateTypeDB = null;

		estimateType = createEstimateType();
		EstimateType testEstimateType = estimateTypeRepository.save(estimateType);

		Optional<EstimateType> estimateTypeOpt = estimateTypeRepository.findById(testEstimateType.getId());
		if (estimateTypeOpt.isPresent()) {
			estimateTypeDB = estimateTypeOpt.get();
		}
		estimateTypeDB.estimateTypeValue("estimateType1").valueType("valueType1").activeYn(true);

		EstimateType updatedEstimateType = estimateTypeRepository.save(estimateTypeDB);

		Assert.assertEquals(updatedEstimateType.getId(), estimateTypeDB.getId());
		Assert.assertEquals(updatedEstimateType.getEstimateTypeValue(), estimateTypeDB.getEstimateTypeValue());
		Assert.assertEquals(updatedEstimateType.getValueType(), estimateTypeDB.getValueType());
		Assert.assertEquals(updatedEstimateType.getActiveYn(), estimateTypeDB.getActiveYn());

		log.info("Test - updateTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("Test - findAllTest - Start");

		estimateType = createEstimateType();
		estimateTypeRepository.save(estimateType);

		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();

		int NUMBER_OF_ESTIMATE_TYPES = estimateTypeList.size();

		log.info("Number Of Estimate types: " + NUMBER_OF_ESTIMATE_TYPES);

		Assert.assertTrue(NUMBER_OF_ESTIMATE_TYPES > 0);

		log.info("Test - findAllTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByIdTest - Start");

		estimateType = createEstimateType();
		EstimateType testEstimateType = estimateTypeRepository.save(estimateType);

		Optional<EstimateType> estimateTypeOpt = estimateTypeRepository.findById(testEstimateType.getId());

		log.info("EstimateType  : " + estimateTypeOpt);

		Assert.assertTrue(estimateTypeOpt.isPresent());

		log.info("Test - findByIdTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 5)
	public void findAllByActiveYnTest() {
		Page<EstimateType> result = estimateTypeRepository.findAllByActiveYn(false, PageRequest.of(0, 5));
		Assert.assertTrue(result.isEmpty());
		log.info("findAllByActiveYnTest successful");
	}

	@Test(priority = 6)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("Test - deleteTest - Start");

		estimateType = estimateTypeRepository.findById(estimateType.getId()).get();
		estimateTypeRepository.delete(estimateType);

		Optional<EstimateType> estimateTypeOpt = estimateTypeRepository.findById(estimateType.getId());

		if (estimateTypeOpt.isEmpty()) {
			Assert.assertTrue(true);
		}
		log.info("Test - deleteTest - End");
		log.info("==================================================================================");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - EstimateTypeRepositoryTest");
	}

}
