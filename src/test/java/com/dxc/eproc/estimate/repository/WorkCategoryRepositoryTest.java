package com.dxc.eproc.estimate.repository;

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
import com.dxc.eproc.estimate.model.WorkCategory;

@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkCategoryRepositoryTest extends AbstractTestNGSpringContextTests {
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkCategoryRepositoryTest.class);

	private static WorkCategory workCategory;

	@Autowired
	WorkCategoryRepository workCategoryRepository;

	/**
	 * inits the class.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workCategory = new WorkCategory();
	}

	/**
	 * create Entity.
	 *
	 * @return workCategory
	 */
	private WorkCategory createEntity() {
		workCategory = new WorkCategory();
		workCategory.setActiveYn(true);
		workCategory.setCategoryCode("001");
		workCategory.setCategoryName("WorkEstimate");
		return workCategory;
	}

	/**
	 * create Test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("create test starts");
		workCategory = createEntity();
		WorkCategory testWorkCategory = workCategoryRepository.save(workCategory);
		Assert.assertEquals(testWorkCategory.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(testWorkCategory.getCategoryName(), workCategory.getCategoryName());
		Assert.assertEquals(testWorkCategory.getActiveYn(), workCategory.getActiveYn());
		log.info("==================================================================================");
		log.info("create test ends");
	}

	/**
	 * update Test.
	 */
	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("update test starts");
		workCategory = workCategoryRepository.save(createEntity());
		String categoryCode = workCategory.getCategoryCode();
		String categoryName = workCategory.getCategoryName();
		workCategory = workCategoryRepository.findById(workCategory.getId()).get();
		workCategory.setCategoryCode("003");
		workCategory.setCategoryName("TestWorkEstimate");
		WorkCategory testWorkCategory = workCategoryRepository.save(workCategory);
		Assertions.assertThat(testWorkCategory.getCategoryCode()).isNotEqualTo(categoryCode);
		Assertions.assertThat(testWorkCategory.getCategoryName()).isNotEqualTo(categoryName);
		log.info("==================================================================================");
		log.info("update test ends");
	}

	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("findAll test starts");
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		Assert.assertTrue(workCategoryList.size() != 0);
		log.info("==================================================================================");
		log.info("findAll test ends");
	}

	/**
	 * findByIdTest
	 */
	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("findBy Id test starts");
		Optional<WorkCategory> workCategoryOpt = workCategoryRepository.findById(workCategory.getId());
		Assert.assertTrue(workCategoryOpt.isPresent());
		log.info("==================================================================================");
		log.info("findBy Id test ends");
	}

	@Test(priority = 5)
	public void findAllByActiveYnTest() {
		log.info("==================================================================================");
		log.info("find All By ActiveYn test starts");
		List<WorkCategory> workCategoryList = workCategoryRepository.findAllByActiveYn(true);
		Assert.assertTrue(workCategoryList.size() != 0);
		log.info("==================================================================================");
		log.info("find All By ActiveYn test ends");
	}

	@Test(priority = 6)
	public void findAllByActiveYnPageableTest() {
		log.info("==================================================================================");
		log.info("find All By ActiveYn Pageable test starts");
		Page<WorkCategory> workCategoryOpt = workCategoryRepository.findAllByActiveYn(true, PageRequest.of(0, 5));
		Assert.assertTrue(workCategoryOpt.hasContent());
		log.info("==================================================================================");
		log.info("find All By ActiveYn Pageable test ends");
	}

	@Test(priority = 7)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("delete test starts");
		workCategory = workCategoryRepository.findById(workCategory.getId()).get();
		workCategoryRepository.delete(workCategory);
		Optional<WorkCategory> workCategoryOpt = workCategoryRepository.findById(workCategory.getId());
		if (!workCategoryOpt.isPresent()) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("workCategory is not deleted");
		}
		log.info("==================================================================================");
		log.info("delete test ends");
	}

	@AfterClass
	public void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkCategoryRepositoryTest");
	}

}
