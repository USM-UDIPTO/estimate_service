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
import com.dxc.eproc.estimate.model.WorkCategoryAttribute;

@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class WorkCategoryAttributeRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkCategoryAttributeRepositoryTest.class);

	private static WorkCategoryAttribute workCategoryAttribute;

	@Autowired
	WorkCategoryAttributeRepository workCategoryAttributeRepository;

	/**
	 * inits the class.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workCategoryAttribute = new WorkCategoryAttribute();
	}

	/**
	 * create the Entity.
	 * @return workCategoryAttribute
	 */
	private WorkCategoryAttribute createEntity() {
		workCategoryAttribute = new WorkCategoryAttribute();
		workCategoryAttribute.setActiveYn(true);
		workCategoryAttribute.setAttributeName("WorkEstimate Attribute");
		workCategoryAttribute.setWorkCategoryId(001L);
		workCategoryAttribute.setWorkTypeId(002L);
		return workCategoryAttribute;
	}

	/**
	 * create the test.
	 */
	@Test
	public void createTest() {
		log.info("==================================================================================");
		log.info("create test starts");
		workCategoryAttribute = createEntity();
		WorkCategoryAttribute testWorkCategoryAttribute = workCategoryAttributeRepository.save(workCategoryAttribute);
		Assert.assertEquals(testWorkCategoryAttribute.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(testWorkCategoryAttribute.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(testWorkCategoryAttribute.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(testWorkCategoryAttribute.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());
		log.info("==================================================================================");
		log.info("create test ends");
	}

	/**
	 * update the Test.
	 */
	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("update test starts");
		workCategoryAttribute = workCategoryAttributeRepository.save(createEntity());
		String attributeName = workCategoryAttribute.getAttributeName();
		Long workTypeId = workCategoryAttribute.getWorkTypeId();
		workCategoryAttribute = workCategoryAttributeRepository.findById(workCategoryAttribute.getId()).get();
		workCategoryAttribute.setAttributeName("AttributeNameTest");
		workCategoryAttribute.setWorkTypeId(22L);
		WorkCategoryAttribute testWorkCategory = workCategoryAttributeRepository.save(workCategoryAttribute);
		Assertions.assertThat(testWorkCategory.getAttributeName()).isNotEqualTo(attributeName);
		Assertions.assertThat(testWorkCategory.getWorkTypeId()).isNotEqualTo(workTypeId);
		log.info("==================================================================================");
		log.info("update test ends");
	}

	/**
	 * find All Test.
	 */
	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("findAll test starts");
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		Assert.assertTrue(workCategoryAttributeList.size() != 0);
		log.info("==================================================================================");
		log.info("findAll test ends");
	}

	/**
	 * find By Id Test.
	 */
	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("findBy Id test starts");
		Optional<WorkCategoryAttribute> workCategoryAttributeOpt = workCategoryAttributeRepository
				.findById(workCategoryAttribute.getId());
		Assert.assertTrue(workCategoryAttributeOpt.isPresent());
		log.info("==================================================================================");
		log.info("findBy Id test ends");
	}

	/**
	 * find All By Work Type Id And WorkCategory Id And ActiveYn Test.
	 */
	@Test(priority = 5)
	public void findAllByWorkTypeIdAndWorkCategoryIdAndActiveYnTest() {
		log.info("==================================================================================");
		log.info("find All By WorkTypeId And WorkCategory Id And ActiveYn test starts");
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository
				.findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(workCategoryAttribute.getWorkTypeId(),
						workCategoryAttribute.getWorkCategoryId(), true);
		Assert.assertTrue(workCategoryAttributeList.size() != 0);
		log.info("==================================================================================");
		log.info("find All By WorkType Id And WorkCategory Id And ActiveYn test ends");
	}

	/**
	 * find All By ActiveYn Pageable Test.
	 */
	@Test(priority = 6)
	public void findAllByActiveYnPageableTest() {
		log.info("==================================================================================");
		log.info("find All By ActiveYn Pageable test starts");
		Page<WorkCategoryAttribute> workCategoryOpt = workCategoryAttributeRepository.findAllByActiveYn(true,
				PageRequest.of(0, 5));
		Assert.assertTrue(workCategoryOpt.hasContent());
		log.info("==================================================================================");
		log.info("find All By ActiveYn Pageable test ends");
	}

	/**
	 * delete Test.
	 */
	@Test(priority = 7)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("delete test starts");
		workCategoryAttribute = workCategoryAttributeRepository.findById(workCategoryAttribute.getId()).get();
		workCategoryAttributeRepository.delete(workCategoryAttribute);
		Optional<WorkCategoryAttribute> workCategoryAttributeOpt = workCategoryAttributeRepository
				.findById(workCategoryAttribute.getId());
		if (!workCategoryAttributeOpt.isPresent()) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("workCategoryAttributeOpt is not deleted");
		}
		log.info("==================================================================================");
		log.info("delete test ends");
	}

	/**
	 * tear Down.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkCategoryAttributeRepositoryTest");
	}

}
