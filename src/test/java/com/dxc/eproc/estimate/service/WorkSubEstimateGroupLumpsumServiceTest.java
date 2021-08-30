package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupLumpsumRepository;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.dxc.eproc.estimate.service.impl.WorkSubEstimateGroupLumpsumServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupLumpsumMapper;

public class WorkSubEstimateGroupLumpsumServiceTest extends PowerMockTestCase {
	private static final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupLumpsumServiceTest.class);
	private static WorkSubEstimateGroupLumpsum workSubEstimateGroupLumpsum;
	private static WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO;
	private static Page<WorkSubEstimateGroupLumpsum> lumpsumPage;

	@InjectMocks
	private WorkSubEstimateGroupLumpsumServiceImpl workSubEstimateGroupLumpsumService;

	@Mock
	private WorkSubEstimateGroupLumpsumRepository workSubEstimateGroupLumpsumRepository;

	@Mock
	private WorkSubEstimateGroupLumpsumMapper workSubEstimateGroupLumpsumMapper;

	@BeforeClass
	public void init() {
		workSubEstimateGroupLumpsum = new WorkSubEstimateGroupLumpsum();
		workSubEstimateGroupLumpsum.setId(1L);
		workSubEstimateGroupLumpsum.setName("Test");
		workSubEstimateGroupLumpsum.setApproxRate(new BigDecimal(1));
		workSubEstimateGroupLumpsum.setWorkSubEstimateGroupId(1L);
		workSubEstimateGroupLumpsumDTO = new WorkSubEstimateGroupLumpsumDTO();
		BeanUtils.copyProperties(workSubEstimateGroupLumpsum, workSubEstimateGroupLumpsumDTO);
		List<WorkSubEstimateGroupLumpsum> lumpsumList = new ArrayList<>();
		lumpsumList.add(workSubEstimateGroupLumpsum);
		lumpsumPage = new PageImpl<>(lumpsumList);

	}

	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(workSubEstimateGroupLumpsumMapper.toEntity(workSubEstimateGroupLumpsumDTO))
				.thenReturn(workSubEstimateGroupLumpsum);
		PowerMockito.when(workSubEstimateGroupLumpsumMapper.toDto(workSubEstimateGroupLumpsum))
				.thenReturn(workSubEstimateGroupLumpsumDTO);

	}

	@Test
	public void saveTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.save(workSubEstimateGroupLumpsum))
				.thenReturn(workSubEstimateGroupLumpsum);

		WorkSubEstimateGroupLumpsumDTO result = workSubEstimateGroupLumpsumService.save(workSubEstimateGroupLumpsumDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.save(workSubEstimateGroupLumpsum))
				.thenReturn(workSubEstimateGroupLumpsum);

		PowerMockito.when(workSubEstimateGroupLumpsumRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroupLumpsum));

		Optional<WorkSubEstimateGroupLumpsumDTO> result = workSubEstimateGroupLumpsumService
				.partialUpdate(workSubEstimateGroupLumpsumDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
	}

	@Test
	public void findAllTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.findAll(PageRequest.of(0, 5))).thenReturn(lumpsumPage);

		Page<WorkSubEstimateGroupLumpsumDTO> result = workSubEstimateGroupLumpsumService.findAll(PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllTest successful!");
	}

	@Test
	public void findOneTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroupLumpsum));

		Optional<WorkSubEstimateGroupLumpsumDTO> result = workSubEstimateGroupLumpsumService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	@Test
	public void deleteTest() throws Exception {
		workSubEstimateGroupLumpsumService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5)))
				.thenReturn(lumpsumPage);

		Page<WorkSubEstimateGroupLumpsumDTO> result = workSubEstimateGroupLumpsumService
				.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.findByWorkSubEstimateGroupIdAndId(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(Optional.of(workSubEstimateGroupLumpsum));

		Optional<WorkSubEstimateGroupLumpsumDTO> result = workSubEstimateGroupLumpsumService
				.findByWorkSubEstimateGroupIdAndId(Mockito.anyLong(), Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdTest successful!");
	}

	@Test
	public void sumApproxRateByGroupIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumRepository.sumApproxRateByGroupId(Mockito.anyLong()))
				.thenReturn(new BigDecimal(0));
		BigDecimal result = workSubEstimateGroupLumpsumService.sumApproxRateByGroupId(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("sumApproxRateByGroupIdTest successful!");
	}
}
