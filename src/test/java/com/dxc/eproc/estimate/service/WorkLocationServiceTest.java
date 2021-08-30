package com.dxc.eproc.estimate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.WorkLocation;
import com.dxc.eproc.estimate.repository.WorkLocationRepository;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import com.dxc.eproc.estimate.service.impl.WorkLocationServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkLocationMapperImpl;

public class WorkLocationServiceTest extends PowerMockTestCase {

	private final static Logger log = LoggerFactory.getLogger(WorkLocationServiceTest.class);

	private WorkLocationService workLocationService;

	@InjectMocks
	private WorkLocationMapperImpl workLocationMapper;

	@Mock
	private WorkLocationRepository workLocationRepository;

	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - WorkLocaxtionServiceTest: init");

	}

	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - WorkEstimateItemServiceTest: setUp");

		workLocationService = new WorkLocationServiceImpl(workLocationRepository, workLocationMapper);

	}

	private WorkLocation createWorkLocation() {

		WorkLocation workLocation = new WorkLocation().assemblyContId(1L).districtId(1L).loksabhaContId(1L).taluqId(1L)
				.latitudeDegrees(1).latitudeMinutes(1).latitudeSeconds(1).longitudeDegrees(2).longitudeMinutes(2)
				.longitudeSeconds(2).locationDescription("testing location description");

		return workLocation;
	}

	public List<WorkLocation> createWorkLocationList() {
		List<WorkLocation> workLocationList = new ArrayList<>();

		WorkLocation firstWorkLocation = new WorkLocation().id(1L).assemblyContId(1L).districtId(1L).loksabhaContId(1L)
				.taluqId(1L).latitudeDegrees(1).latitudeMinutes(1).latitudeSeconds(1).longitudeDegrees(2)
				.longitudeMinutes(2).longitudeSeconds(2).locationDescription("testing location description");

		WorkLocation secondWorkLocation = new WorkLocation().id(2L).assemblyContId(1L).districtId(1L).loksabhaContId(1L)
				.taluqId(1L).latitudeDegrees(1).latitudeMinutes(1).latitudeSeconds(1).longitudeDegrees(2)
				.longitudeMinutes(2).longitudeSeconds(2).locationDescription("testing location description");

		workLocationList.add(firstWorkLocation);
		workLocationList.add(secondWorkLocation);

		return workLocationList;
	}

	@Test(priority = 1)
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		final Long WORKESTIMATEID = 101L;

		WorkLocation workLocation = createWorkLocation();
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);
		workLocation.setId(WORKESTIMATEID);

		PowerMockito.when(workLocationRepository.save(Mockito.any())).thenReturn(workLocation);

		workLocationDTO = workLocationService.save(workLocationDTO);
		log.info("Response data:saveTest: " + workLocationDTO);

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 2)
	public void saveExistingWorkLocationTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingWorkLocationTest - Start");

		final Long WORKESTIMATEID = 101L;

		WorkLocation workLocation = createWorkLocation();
		workLocation.setId(WORKESTIMATEID);
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		PowerMockito.when(workLocationRepository.findById(workLocationDTO.getId()))
		.thenReturn(Optional.of(workLocation));
		PowerMockito.when(workLocationRepository.save(Mockito.any())).thenReturn(workLocation);

		workLocationDTO = workLocationService.save(workLocationDTO);
		log.info("Response data:saveExistingWorkLocation : " + workLocationDTO);

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - saveExistingWorkLocationTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 3)
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long WORKESTIMATEID = 101L;
		final Long UPDATEDTALUQID = 100L;

		WorkLocation workLocation = createWorkLocation();
		workLocation.setId(WORKESTIMATEID);
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);
		workLocationDTO.setAssemblyContId(null);
		workLocationDTO.setLocationDescription(null);
		workLocationDTO.setTaluqId(UPDATEDTALUQID);

		Optional<WorkLocationDTO> workLocationDTOOpt = Optional.of(workLocationDTO);

		PowerMockito.when(workLocationRepository.findById(workLocationDTO.getId()))
		.thenReturn(Optional.of(workLocation));
		PowerMockito.when(workLocationRepository.save(Mockito.any())).thenReturn(workLocation);

		workLocationDTOOpt = workLocationService.partialUpdate(workLocationDTO);
		log.info("Response data:partialUpdateTest: " + workLocationDTOOpt);

		if (workLocationDTOOpt.isPresent()) {
			workLocationDTO = workLocationDTOOpt.get();
		}

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<WorkLocationDTO> responseDTO = null;
		WorkLocationDTO workLocationDTO = null;

		final Long WORKESTIMATEID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkLocation> workLocationList = createWorkLocationList();
		Page<WorkLocation> workLocationPage = new PageImpl<>(workLocationList);

		PowerMockito.when(workLocationRepository.findAll(pageable)).thenReturn(workLocationPage);

		responseDTO = workLocationService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		workLocationDTO = responseDTO.get().findFirst().get();
		WorkLocation workLocation = workLocationList.get(0);

		Assert.assertEquals(workLocationDTO.getId(), WORKESTIMATEID);
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 5)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long WORKESTIMATEID = 101L;

		Optional<WorkLocationDTO> workLocationOpt = null;

		WorkLocation workLocation = createWorkLocation();
		workLocation.setId(WORKESTIMATEID);
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		PowerMockito.when(workLocationRepository.findById(workLocationDTO.getId()))
		.thenReturn(Optional.of(workLocation));

		workLocationOpt = workLocationService.findOne(WORKESTIMATEID);
		log.info("Response data:findOneTest: " + workLocationOpt);

		if (workLocationOpt.isPresent()) {
			workLocationDTO = workLocationOpt.get();
		}

		Assert.assertEquals(workLocationDTO.getId(), WORKESTIMATEID);
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 6)
	public void findBySubEstimateIdAndIdTest() {
		log.info("==========================================================================");
		log.info("Test - findBySubEstimateIdAndIdTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long ID = 1L;

		Optional<WorkLocationDTO> workLocationOpt = null;
		WorkLocationDTO workLocationDTO = null;

		WorkLocation workLocation = createWorkLocation();
		workLocation.setId(ID);

		PowerMockito.when(workLocationRepository.findBySubEstimateIdAndId(SUBESTIMATEID, ID))
		.thenReturn(Optional.of(workLocation));

		workLocationOpt = workLocationService.findBySubEstimateIdAndId(SUBESTIMATEID, ID);
		log.info("Response data:findBySubEstimateIdAndIdTest: " + workLocationOpt);

		if (workLocationOpt.isPresent()) {
			workLocationDTO = workLocationOpt.get();
		}

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - findBySubEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 7)
	public void getAllWorkLocationsBySubEstimateIdWithPageTest() {
		log.info("==========================================================================");
		log.info("Test - getAllWorkLocationsBySubEstimateIdWithPageTest - Start");

		Page<WorkLocationDTO> responseDTO = null;
		WorkLocationDTO workLocationDTO = null;

		// final Long WORK_ESTIMATE_ID = 1L;
		final Long SUBESTIMATEID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkLocation> workLocationList = createWorkLocationList();
		Page<WorkLocation> workLocationPage = new PageImpl<>(workLocationList);

		PowerMockito
		.when(workLocationRepository.findBySubEstimateIdOrderByLastModifiedTsDesc(SUBESTIMATEID, pageable))
		.thenReturn(workLocationPage);

		responseDTO = workLocationService.getAllWorkLocationsBySubEstimateId(SUBESTIMATEID, pageable);
		log.info("Response data: getAllWorkLocationsBySubEstimateIdWithPageTest: " + responseDTO);

		workLocationDTO = responseDTO.get().findFirst().get();
		WorkLocation workLocation = workLocationList.get(0);

		Assert.assertEquals(workLocationDTO.getId(), SUBESTIMATEID);
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - getAllWorkLocationsBySubEstimateIdWithPageTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 8)
	public void getAllWorkLocationsBySubEstimateIdTest() {
		log.info("==========================================================================");
		log.info("Test - getAllWorkLocationsBySubEstimateIdTest - Start");

		final Long SUBESTIMATEID = 1L;

		WorkLocationDTO workLocationDTO = null;
		List<WorkLocationDTO> workLocationDTOList = null;

		List<WorkLocation> workLocationList = createWorkLocationList();

		PowerMockito.when(workLocationRepository.findBySubEstimateIdOrderByLastModifiedTsAsc(SUBESTIMATEID))
		.thenReturn(workLocationList);

		workLocationDTOList = workLocationService.getAllWorkLocationsBySubEstimateId(SUBESTIMATEID);
		log.info("Response data:getAllWorkLocationsBySubEstimateIdTest: " + workLocationList);

		workLocationDTO = workLocationDTOList.get(0);
		WorkLocation workLocation = workLocationList.get(0);

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - getAllWorkLocationsBySubEstimateIdTestIn - End");
		log.info("==========================================================================");
	}

	@Test(priority = 8)
	public void findBySubEstimateIdAndIdInTest() {
		log.info("==========================================================================");
		log.info("Test - findBySubEstimateIdAndIdInTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long ID = 1L;
		final Long ID2 = 2L;

		WorkLocationDTO workLocationDTO = null;
		List<WorkLocationDTO> workLocationDTOList = null;

		WorkLocation workLocation1 = createWorkLocation();
		workLocation1.setId(ID);

		WorkLocation workLocation2 = createWorkLocation();
		workLocation2.setId(ID2);

		List<WorkLocation> workLocationList = createWorkLocationList();
		workLocationList.add(workLocation1);
		workLocationList.add(workLocation2);

		List<Long> ids = new ArrayList<>();
		ids.add(ID);
		ids.add(ID2);

		PowerMockito.when(workLocationRepository.findBySubEstimateIdAndIdIn(SUBESTIMATEID, ids))
		.thenReturn(workLocationList);

		workLocationDTOList = workLocationService.findBySubEstimateIdAndIdIn(SUBESTIMATEID, ids);
		log.info("Response data:findBySubEstimateIdAndIdTest: " + workLocationList);

		workLocationDTO = workLocationDTOList.get(0);
		WorkLocation workLocation = workLocationList.get(0);

		Assert.assertEquals(workLocationDTO.getId(), workLocation.getId());
		Assert.assertEquals(workLocationDTO.getLocationDescription(), workLocation.getLocationDescription());
		Assert.assertEquals(workLocationDTO.getAssemblyContId(), workLocation.getAssemblyContId());
		Assert.assertEquals(workLocationDTO.getDistrictId(), workLocation.getDistrictId());
		Assert.assertEquals(workLocationDTO.getLoksabhaContId(), workLocation.getLoksabhaContId());
		Assert.assertEquals(workLocationDTO.getTaluqId(), workLocation.getTaluqId());
		Assert.assertEquals(workLocationDTO.getLatitudeDegrees(), workLocation.getLatitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLatitudeMinutes(), workLocation.getLatitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLatitudeSeconds(), workLocation.getLatitudeSeconds());
		Assert.assertEquals(workLocationDTO.getLongitudeDegrees(), workLocation.getLongitudeDegrees());
		Assert.assertEquals(workLocationDTO.getLongitudeMinutes(), workLocation.getLongitudeMinutes());
		Assert.assertEquals(workLocationDTO.getLongitudeSeconds(), workLocation.getLongitudeSeconds());

		log.info("Test - findBySubEstimateIdAndIdTestIn - End");
		log.info("==========================================================================");
	}

	@Test(priority = 9)
	public void deleteTest() {
		log.info("==========================================================================");
		log.info("Test - deleteTest - Start");

		final Long ID = 1L;

		PowerMockito.doNothing().when(workLocationRepository).deleteById(ID);

		workLocationService.delete(ID);
		log.info("Response: deleteTest has been passed!");

		log.info("Test - deleteTest - End");
		log.info("==========================================================================");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkLocationServiceTest");
	}

}
