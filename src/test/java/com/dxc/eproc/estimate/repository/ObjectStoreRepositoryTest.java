package com.dxc.eproc.estimate.repository;

import java.util.ArrayList;
import java.util.List;
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
import com.dxc.eproc.estimate.document.ReferenceTypes;
import com.dxc.eproc.estimate.model.ObjectStore;

@SpringBootTest(classes = EstimateServiceApplication.class)
@ActiveProfiles("test")
public class ObjectStoreRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ObjectStoreRepositoryTest.class);

	/** The Object Store */
	private static ObjectStore objectStore;

	/** The ObjectStore Repository */
	@Autowired
	ObjectStoreRepository objectStoreRepository;

	/**
	 * inits the class.
	 *
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		objectStore = new ObjectStore();
	}

	/**
	 * createEntity.
	 *
	 * @return objectStore
	 */
	public static ObjectStore createEntity() {
		ObjectStore objectStore = new ObjectStore();
		objectStore.setReferenceId(11L);
		objectStore.setReferenceType(ReferenceTypes.ADMINSANCTION_PROCEEDINGS);
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(11L);
		return objectStore;
	}

	/**
	 * create Test.
	 */
	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("create test starts");
		objectStore = createEntity();
		ObjectStore testObjectStore = objectStoreRepository.save(objectStore);
		Assert.assertEquals(testObjectStore.getReferenceId(), objectStore.getReferenceId());
		Assert.assertEquals(testObjectStore.getReferenceType(), objectStore.getReferenceType());
		Assert.assertEquals(testObjectStore.getWorkEstimateId(), objectStore.getWorkEstimateId());
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
		objectStore = objectStoreRepository.save(createEntity());
		Long beforeReferenceId = objectStore.getReferenceId();
		ReferenceTypes beforeRefType = objectStore.getReferenceType();
		objectStore = objectStoreRepository.findById(objectStore.getId()).get();
		objectStore.setReferenceType(ReferenceTypes.TECHSANCTION_PROCEEDINGS);
		objectStore.setReferenceId(22L);
		ObjectStore testObjectStore = objectStoreRepository.save(objectStore);
		Long afterReferenceId = testObjectStore.getReferenceId();
		ReferenceTypes afterRefType = testObjectStore.getReferenceType();
		Assertions.assertThat(afterReferenceId).isNotEqualTo(beforeReferenceId);
		Assertions.assertThat(afterRefType).isNotEqualTo(beforeRefType);
		log.info("==================================================================================");
		log.info("update test ends");
	}

	/**
	 * findAll Test.
	 */
	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("find all test starts");
		List<ObjectStore> objectStoreList = objectStoreRepository.findAll();
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find all test ends");
	}

	/**
	 * findById Test.
	 */
	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("find by id test starts");
		Optional<ObjectStore> objectStoreOpt = objectStoreRepository.findById(objectStore.getId());
		Assert.assertTrue(objectStoreOpt.isPresent());
		log.info("==================================================================================");
		log.info("find by id test ends");
	}

	/**
	 * findBy WorkEstimateId Test.
	 */
	@Test(priority = 5)
	public void findByWorkEstimateIdTest() {
		log.info("==================================================================================");
		log.info("find by work estimate id test starts");
		List<ObjectStore> objectStoreList = objectStoreRepository.findByWorkEstimateId(objectStore.getWorkEstimateId());
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find by work estimate id test ends");
	}

	/**
	 * findBy ReferenceId And Reference Type Test.
	 */
	@Test(priority = 6)
	public void findByReferenceIdAndReferenceTypeTest() {
		log.info("==================================================================================");
		log.info("find by reference id and reference type test starts");
		List<ObjectStore> objectStoreList = objectStoreRepository
				.findByReferenceIdAndReferenceType(objectStore.getReferenceId(), objectStore.getReferenceType());
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find by reference id and reference type test ends");
	}

	/**
	 * find By ReferenceId And ReferenceType And ActiveYn Test.
	 */
	@Test(priority = 7)
	public void findByReferenceIdAndReferenceTypeAndActiveYnTest() {
		log.info("==================================================================================");
		log.info("find by reference id and reference type and activeyn test starts");
		List<ObjectStore> objectStoreList = objectStoreRepository.findByReferenceIdAndReferenceTypeAndActiveYn(
				objectStore.getReferenceId(), objectStore.getReferenceType(), objectStore.isActiveYn());
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find by reference id and reference type and activeyn test ends");
	}

	/**
	 * findBy Reference Id And ReferenceType In And ActiveYn Test.
	 */
	@Test(priority = 8)
	public void findByReferenceIdAndReferenceTypeInAndActiveYnTest() {
		log.info("==================================================================================");
		log.info("find by reference id and reference type in and activeyn test andstarts");
		List<ReferenceTypes> referenceTypeList = new ArrayList<>();
		referenceTypeList.add(ReferenceTypes.ADMINSANCTION_PROCEEDINGS);
		referenceTypeList.add(ReferenceTypes.TECHSANCTION_PROCEEDINGS);
		List<ObjectStore> objectStoreList = objectStoreRepository.findByReferenceIdAndReferenceTypeInAndActiveYn(
				objectStore.getReferenceId(), referenceTypeList, objectStore.isActiveYn());
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find by reference id aand reference type in and activeyn test ends");
	}

	/**
	 * find All By Work EstimateId And Reference Type.
	 */
	@Test(priority = 9)
	public void findAllByWorkEstimateIdAndReferenceType() {
		log.info("==================================================================================");
		log.info("find by reference id and reference type in and activeyn test andstarts");
		List<ObjectStore> objectStoreList = objectStoreRepository.findAllByWorkEstimateIdAndReferenceType(
				objectStore.getWorkEstimateId(), ReferenceTypes.ADMINSANCTION_PROCEEDINGS);
		Assert.assertTrue(objectStoreList.size() != 0);
		log.info("==================================================================================");
		log.info("find by reference id aand reference type in and activeyn test ends");
	}

	/**
	 * delete Test.
	 */
	@Test(priority = 10)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("delete test starts");
		objectStore = objectStoreRepository.findById(objectStore.getId()).get();
		objectStoreRepository.delete(objectStore);
		Optional<ObjectStore> objectStoreOpt = objectStoreRepository.findById(objectStore.getId());
		if (!objectStoreOpt.isPresent()) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("objectStore is not deleted");
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
		log.info("This is executed after once Per Test Class - ObjectStoreRepositoryTest");
	}

}
