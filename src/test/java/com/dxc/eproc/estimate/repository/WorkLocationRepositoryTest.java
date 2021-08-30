package com.dxc.eproc.estimate.repository;

import java.util.ArrayList;
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

import com.dxc.eproc.estimate.model.WorkLocation;

@SpringBootTest
@ActiveProfiles("test")
public class WorkLocationRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(WorkLocationRepositoryTest.class);

	private static final Long DEFAULT_SUBESTIMATE_ID = 1L;

	private static final Long UPDATE_SUBESTIMATE_ID = 2L;

	private static final Long DEFAULT_ASSEMBLY_ID = 1L;

	private static final Long UPDATE_ASSEMBLY_ID = 2L;

	private static final Long DEFAULT_DISTRICT_ID = 1L;

	private static final Long UPDATE_DISTRICT_ID = 2L;

	private static final Long DEFAULT_LOKSABHA_ID = 1L;

	private static final Long UPDATE_LOKSABHA_ID = 2L;

	private static final Long DEFAULT_TALUQ_ID = 1L;

	private static final Long UPDATE_TALUQ_ID = 2L;

	private static final Integer DEFAULT_LATITUDE_DEGREES = 1;

	private static final Integer UPDATE_LATITUDE_DEGREES = 2;

	private static final Integer DEFAULT_LATITUDE_MINUTES = 1;

	private static final Integer UPDATE_LATITUDE_MINUTES = 2;

	private static final Integer DEFAULT_LATITUDE_SECONDS = 1;

	private static final Integer UPDATE_LATITUDE_SECONDS = 2;

	private static final Integer DEFAULT_LONGITUDE_DEGREES = 1;

	private static final Integer UPDATE_LONGITUDE_DEGREES = 2;

	private static final Integer DEFAULT_LONGITUDE_MINUTES = 1;

	private static final Integer UPDATE_LONGITUDE_MINUTES = 2;

	private static final Integer DEFAULT_LONGITUDE_SECONDS = 1;

	private static final Integer UPDATE_LONGITUDE_SECONDS = 2;

	private static final String DEFAULT_DESCRIPTION = " defalut description";

	private static final String UPDATE_DESCRIPTION = " defalut description";

	private static WorkLocation workLocation;

	@Autowired
	private WorkLocationRepository workLocationRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");

	}

	private WorkLocation createEntity() {

		WorkLocation workLocation = new WorkLocation().subEstimateId(DEFAULT_SUBESTIMATE_ID)
				.assemblyContId(DEFAULT_ASSEMBLY_ID).districtId(DEFAULT_DISTRICT_ID).loksabhaContId(DEFAULT_LOKSABHA_ID)
				.taluqId(DEFAULT_TALUQ_ID).latitudeDegrees(DEFAULT_LATITUDE_DEGREES)
				.latitudeMinutes(DEFAULT_LATITUDE_MINUTES).latitudeSeconds(DEFAULT_LATITUDE_SECONDS)
				.longitudeDegrees(DEFAULT_LONGITUDE_DEGREES).longitudeMinutes(DEFAULT_LONGITUDE_MINUTES)
				.longitudeSeconds(DEFAULT_LONGITUDE_SECONDS).locationDescription(DEFAULT_DESCRIPTION);

		return workLocation;

	}

	@Test(priority = 1)
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test - createTest - Start");

		workLocation = createEntity();

		WorkLocation testWorkLocation = workLocationRepository.save(workLocation);

		Assert.assertTrue(testWorkLocation.getId() > 0);
		Assert.assertEquals(testWorkLocation.getSubEstimateId(), workLocation.getSubEstimateId());
		Assert.assertEquals(testWorkLocation.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(testWorkLocation.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(testWorkLocation.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(testWorkLocation.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(testWorkLocation.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(testWorkLocation.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(testWorkLocation.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(testWorkLocation.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(testWorkLocation.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(testWorkLocation.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(testWorkLocation.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - createTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 2)
	public void updateTest() {
		log.info("==================================================================================");
		log.info("Test - updateTest - Start");

		WorkLocation workLocationDB = null;

		workLocation = createEntity();

		WorkLocation testWorkLocation = workLocationRepository.save(workLocation);

		Optional<WorkLocation> workLocationOpt = workLocationRepository.findById(testWorkLocation.getId());
		if (workLocationOpt.isPresent()) {
			workLocationDB = workLocationOpt.get();
		}
		workLocationDB.subEstimateId(UPDATE_SUBESTIMATE_ID).assemblyContId(UPDATE_ASSEMBLY_ID)
				.districtId(UPDATE_DISTRICT_ID).loksabhaContId(UPDATE_LOKSABHA_ID).taluqId(UPDATE_TALUQ_ID)
				.latitudeDegrees(UPDATE_LATITUDE_DEGREES).latitudeMinutes(UPDATE_LATITUDE_MINUTES)
				.latitudeSeconds(UPDATE_LATITUDE_SECONDS).longitudeDegrees(UPDATE_LONGITUDE_DEGREES)
				.longitudeMinutes(UPDATE_LONGITUDE_MINUTES).longitudeSeconds(UPDATE_LONGITUDE_SECONDS)
				.locationDescription(UPDATE_DESCRIPTION);

		WorkLocation updatedWorkLocation = workLocationRepository.save(workLocationDB);

		Assert.assertEquals(updatedWorkLocation.getId(), workLocationDB.getId());
		Assert.assertEquals(updatedWorkLocation.getSubEstimateId(), workLocationDB.getSubEstimateId());
		Assert.assertEquals(updatedWorkLocation.getLocationDescription(), workLocationDB.getLocationDescription());
		Assert.assertEquals(updatedWorkLocation.getAssemblyContId(), workLocationDB.getAssemblyContId());
		Assert.assertEquals(updatedWorkLocation.getDistrictId(), workLocationDB.getDistrictId());
		Assert.assertEquals(updatedWorkLocation.getLoksabhaContId(), workLocationDB.getLoksabhaContId());
		Assert.assertEquals(updatedWorkLocation.getTaluqId(), workLocationDB.getTaluqId());
		Assert.assertEquals(updatedWorkLocation.getLatitudeDegrees(), workLocationDB.getLatitudeDegrees());
		Assert.assertEquals(updatedWorkLocation.getLatitudeMinutes(), workLocationDB.getLatitudeMinutes());
		Assert.assertEquals(updatedWorkLocation.getLatitudeSeconds(), workLocationDB.getLatitudeSeconds());
		Assert.assertEquals(updatedWorkLocation.getLongitudeDegrees(), workLocationDB.getLongitudeDegrees());
		Assert.assertEquals(updatedWorkLocation.getLongitudeMinutes(), workLocationDB.getLongitudeMinutes());
		Assert.assertEquals(updatedWorkLocation.getLongitudeSeconds(), workLocationDB.getLongitudeSeconds());

		log.info("Test - updateTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 3)
	public void findAllTest() {
		log.info("==================================================================================");
		log.info("Test - findAllTest - Start");

		workLocation = createEntity();
		workLocationRepository.save(workLocation);

		List<WorkLocation> workLocationList = workLocationRepository.findAll();

		int NUMBER_OF_WORK_LOCATIONS = workLocationList.size();

		log.info("Number Of Work Estimate Item: " + NUMBER_OF_WORK_LOCATIONS);

		Assert.assertTrue(NUMBER_OF_WORK_LOCATIONS > 0);

		log.info("Test - findAllTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 4)
	public void findByIdTest() {
		log.info("==================================================================================");
		log.info("Test - findByIdTest - Start");

		workLocation = createEntity();
		WorkLocation testWorkLocation = workLocationRepository.save(workLocation);

		Optional<WorkLocation> workLocationOpt = workLocationRepository.findById(testWorkLocation.getId());

		log.info("Work Location  : " + workLocationOpt);

		Assert.assertTrue(workLocationOpt.isPresent());

		log.info("Test - findByIdTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 5)
	public void findBySubEstimateIdAndIdTest() {
		log.info("==================================================================================");
		log.info("Test - findBySubEstimateIdAndIdTest - Start");

		workLocation = createEntity();
		WorkLocation testWorkLocation = workLocationRepository.save(workLocation);

		Optional<WorkLocation> workLocationOpt = workLocationRepository.findBySubEstimateIdAndId(DEFAULT_SUBESTIMATE_ID,
				testWorkLocation.getId());

		log.info("Work Location  : " + workLocationOpt);

		Assert.assertTrue(workLocationOpt.isPresent());

		log.info("Test - findBySubEstimateIdAndIdTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 6)
	public void findBySubEstimateIdOrderByLastModifiedTsDescTest() {
		log.info("==================================================================================");
		log.info("Test - findBySubEstimateIdOrderByLastModifiedTsDescTest - Start");

		workLocation = createEntity();
		workLocationRepository.save(workLocation);

		Page<WorkLocation> workLocationOpt = workLocationRepository
				.findBySubEstimateIdOrderByLastModifiedTsDesc(DEFAULT_SUBESTIMATE_ID, PageRequest.of(0, 5));
		Assert.assertTrue(workLocationOpt.hasContent());

		log.info("Test - findBySubEstimateIdOrderByLastModifiedTsDescTest - End");
		log.info("==================================================================================");
	}

	@Test(priority = 7)
	public void findBySubEstimateIdAndIdInTest() {
		log.info("==================================================================================");
		log.info("Test - findBySubEstimateIdAndIdInTest - Start");

		workLocation = createEntity();
		workLocationRepository.save(workLocation);

		final Long ID = 1L;
		final Long ID1 = 2L;
		List<Long> ids = new ArrayList<>();
		ids.add(ID);
		ids.add(ID1);

		List<WorkLocation> workLocationList = workLocationRepository.findBySubEstimateIdAndIdIn(DEFAULT_SUBESTIMATE_ID,
				ids);
		int NUMBER_OF_WORK_LOCATIONS = workLocationList.size();

		log.info("Number Of Work Locations : " + NUMBER_OF_WORK_LOCATIONS);

		Assert.assertTrue(NUMBER_OF_WORK_LOCATIONS > 0);

		log.info("Test - findBySubEstimateIdAndIdInTest - End");
		log.info("==================================================================================");

	}

	@Test(priority = 8)
	public void deleteTest() {
		log.info("==================================================================================");
		log.info("Test - deleteTest - Start");

		workLocation = workLocationRepository.findById(workLocation.getId()).get();
		workLocationRepository.delete(workLocation);

		Optional<WorkLocation> workLocationOpt = workLocationRepository.findById(workLocation.getId());

		if (workLocationOpt.isEmpty()) {
			Assert.assertTrue(true);
		}
		log.info("Test - deleteTest - End");
		log.info("==================================================================================");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkLocationRepositoryTest");
	}
}
