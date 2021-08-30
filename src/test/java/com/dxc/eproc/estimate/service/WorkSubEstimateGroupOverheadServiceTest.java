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

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupOverheadRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.estimate.service.impl.WorkSubEstimateGroupOverheadServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupOverheadMapper;

public class WorkSubEstimateGroupOverheadServiceTest extends PowerMockTestCase {

	private static final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadServiceTest.class);

	private static WorkSubEstimateGroupOverhead workSubEstimateGroupOverhead;

	private static WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO;

	private static List<WorkSubEstimateGroupOverhead> overheadList;

	private static Page<WorkSubEstimateGroupOverhead> overheadPage;

	private static List<WorkSubEstimateGroupOverheadDTO> overheadDTOList;

	@InjectMocks
	private WorkSubEstimateGroupOverheadServiceImpl workSubEstimateGroupOverheadService;

	@Mock
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	@Mock
	private WorkEstimateService workEstimateService;

	@Mock
	private WorkSubEstimateGroupOverheadRepository workSubEstimateGroupOverheadRepository;

	@Mock
	private WorkSubEstimateGroupOverheadMapper workSubEstimateGroupOverheadMapper;

	@BeforeClass
	public void init() {
		workSubEstimateGroupOverhead = new WorkSubEstimateGroupOverhead();
		workSubEstimateGroupOverhead.setId(1L);
		workSubEstimateGroupOverhead.setCode("A");
		workSubEstimateGroupOverhead.setConstruct("(Fixed)");
		workSubEstimateGroupOverhead.setEnteredValue(new BigDecimal(1));
		workSubEstimateGroupOverhead.setFinalYn(true);
		workSubEstimateGroupOverhead.setOverheadValue(new BigDecimal(1));
		workSubEstimateGroupOverhead.setValueFixedYn(true);
		workSubEstimateGroupOverhead.setValueType(ValueType.ADDED);
		workSubEstimateGroupOverheadDTO = new WorkSubEstimateGroupOverheadDTO();
		BeanUtils.copyProperties(workSubEstimateGroupOverhead, workSubEstimateGroupOverheadDTO);
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(1L);
		workSubEstimateGroupOverheadDTO.setSelectedOverheads(selectedOverheads);

		overheadList = new ArrayList<>();
		overheadList.add(workSubEstimateGroupOverhead);
		overheadPage = new PageImpl<>(overheadList);

		overheadDTOList = new ArrayList<>();
		overheadDTOList.add(workSubEstimateGroupOverheadDTO);
	}

	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(workSubEstimateGroupOverheadMapper.toEntity(workSubEstimateGroupOverheadDTO))
				.thenReturn(workSubEstimateGroupOverhead);
		PowerMockito.when(workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverhead))
				.thenReturn(workSubEstimateGroupOverheadDTO);
		PowerMockito.when(workSubEstimateGroupOverheadMapper.toDto(overheadList)).thenReturn(overheadDTOList);
	}

	@Test
	public void saveTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.save(workSubEstimateGroupOverhead))
				.thenReturn(workSubEstimateGroupOverhead);

		WorkSubEstimateGroupOverheadDTO result = workSubEstimateGroupOverheadService
				.save(workSubEstimateGroupOverheadDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.save(workSubEstimateGroupOverhead))
				.thenReturn(workSubEstimateGroupOverhead);

		PowerMockito.when(workSubEstimateGroupOverheadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroupOverhead));

		Optional<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.partialUpdate(workSubEstimateGroupOverheadDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
	}

	@Test
	public void findAllTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.findAll(PageRequest.of(0, 5)))
				.thenReturn(overheadPage);

		Page<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findAll(PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllTest successful!");
	}

	@Test
	public void findOneTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroupOverhead));

		Optional<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	@Test
	public void deleteTest() throws Exception {
		workSubEstimateGroupOverheadService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdInTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupIdAndIdIn(Mockito.anyLong(),
				Mockito.anyList())).thenReturn(overheadList);

		List<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndIdIn(Mockito.anyLong(), Mockito.anyList());
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndIdInTest successful!");
	}

	@Test
	public void sumOverheadValueByGroupIdAndIdInTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndIdIn(Mockito.anyLong(),
				Mockito.anyList())).thenReturn(new BigDecimal(0));

		BigDecimal result = workSubEstimateGroupOverheadService.sumOverheadValueByGroupIdAndIdIn(Mockito.anyLong(),
				Mockito.anyList());
		Assert.assertNotNull(result);
		log.info("sumOverheadValueByGroupIdAndIdInTest successful!");
	}

	@Test
	public void countByWorkSubEstimateGroupIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.countByWorkSubEstimateGroupId(Mockito.anyLong()))
				.thenReturn(1);

		int result = workSubEstimateGroupOverheadService.countByWorkSubEstimateGroupId(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("countByWorkSubEstimateGroupIdTest successful!");
	}

	@Test
	public void sumAddedOverheadValueByGroupIdAndFinalYnTrueTest() throws Exception {
		PowerMockito.when(
				workSubEstimateGroupOverheadRepository.sumAddedOverheadValueByGroupIdAndFinalYnTrue(Mockito.anyLong()))
				.thenReturn(new BigDecimal(0));

		BigDecimal result = workSubEstimateGroupOverheadService
				.sumAddedOverheadValueByGroupIdAndFinalYnTrue(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("sumAddedOverheadValueByGroupIdAndFinalYnTrueTest successful!");
	}

	@Test
	public void sumOtherOverheadValueByGroupIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.sumOtherOverheadValueByGroupId(Mockito.anyLong()))
				.thenReturn(new BigDecimal(0));

		BigDecimal result = workSubEstimateGroupOverheadService.sumOtherOverheadValueByGroupId(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("sumOtherOverheadValueByGroupIdTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdAndValueTypeTest() throws Exception {
		PowerMockito
				.when(workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupIdAndIdAndValueType(
						Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
				.thenReturn(Optional.of(workSubEstimateGroupOverhead));

		Optional<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndIdAndValueType(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdAndValueTypeTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalseTest() throws Exception {
		PowerMockito
				.when(workSubEstimateGroupOverheadRepository
						.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(Mockito.anyLong(), Mockito.any()))
				.thenReturn(overheadList);

		List<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(Mockito.anyLong(), Mockito.any());
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalseTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5)))
				.thenReturn(overheadPage);

		Page<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupIdAndId(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(Optional.of(workSubEstimateGroupOverhead));

		Optional<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndId(Mockito.anyLong(), Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndValueTypeTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueType(Mockito.anyLong(), Mockito.any())).thenReturn(overheadList);

		List<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueType(Mockito.anyLong(), Mockito.any());
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndValueTypeTest successful!");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCodeTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(Mockito.anyLong(),
						Mockito.any()))
				.thenReturn(overheadList);
		List<WorkSubEstimateGroupOverheadDTO> result = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(Mockito.anyLong(),
						Mockito.any());
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCodeTest successful!");
	}

	@Test
	public void sumOverheadValueByGroupIdAndCodeInTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndCodeIn(Mockito.anyLong(),
				Mockito.any())).thenReturn(BigDecimal.TEN);
		BigDecimal result = workSubEstimateGroupOverheadService.sumOverheadValueByGroupIdAndCodeIn(Mockito.anyLong(),
				Mockito.any());
		Assert.assertEquals(result, BigDecimal.TEN);
	}

	@Test
	public void calculatePercentageOverheadTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndIdIn(1L,
				workSubEstimateGroupOverheadDTO.getSelectedOverheads())).thenReturn(BigDecimal.TEN);
		workSubEstimateGroupOverheadService.calculatePercentageOverhead(workSubEstimateGroupOverheadDTO,
				overheadDTOList, 1L);
		log.info("calculatePercentageOverheadTest successful!");
	}

	@Test
	public void recalculateAndUpdateWRTOverheadTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupOverheadRepository.sumAddedOverheadValueByGroupIdAndFinalYnTrue(1L))
				.thenReturn(null);
		PowerMockito.when(workSubEstimateGroupService.partialUpdate(Mockito.any()))
				.thenReturn(Optional.of(new WorkSubEstimateGroupDTO()));
		PowerMockito.when(workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(null);
		PowerMockito.when(workEstimateService.partialUpdate(Mockito.any()))
				.thenReturn(Optional.of(new WorkEstimateDTO()));
		workSubEstimateGroupOverheadService.recalculateAndUpdateWRTOverhead(1L, 1L);
		log.info("recalculateAndUpdateWRTOverheadTest successful!");
	}
}
