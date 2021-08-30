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
import com.dxc.eproc.estimate.model.WorkSubEstimateGroup;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupRepository;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.estimate.service.impl.WorkSubEstimateGroupServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupMapper;

public class WorkSubEstimateGroupServiceTest extends PowerMockTestCase {

	private static final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadServiceTest.class);

	private static WorkSubEstimateGroup workSubEstimateGroup;

	private static WorkSubEstimateGroupDTO workSubEstimateGroupDTO;

	private static List<WorkSubEstimateGroupDTO> groupDTOList;

	private static Page<WorkSubEstimateGroup> page;

	private static WorkEstimateDTO workEstimateDTO;

	private static SubEstimateDTO subEstimateDTO;

	private static List<SubEstimateDTO> subEstimateDTOList;

	private static WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO;

	private static List<WorkSubEstimateGroupOverheadDTO> overheadDTOList;

	private static Page<WorkSubEstimateGroupOverheadDTO> overheadDTOPage;

	private static WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO;

	private static List<WorkSubEstimateGroupLumpsumDTO> lumpsumDTOList;

	private static Page<WorkSubEstimateGroupLumpsumDTO> lumpsumDTOPage;

	@InjectMocks
	private WorkSubEstimateGroupServiceImpl workSubEstimateGroupService;

	@Mock
	private WorkSubEstimateGroupRepository workSubEstimateGroupRepository;

	@Mock
	private WorkSubEstimateGroupMapper workSubEstimateGroupMapper;

	@Mock
	private WorkSubEstimateGroupLumpsumService workSubEstimateGroupLumpsumService;

	@Mock
	private WorkEstimateService workEstimateService;

	@Mock
	private SubEstimateService subEstimateService;

	@Mock
	private WorkSubEstimateGroupOverheadService workSubEstimateGroupOverheadService;

	@BeforeClass
	public void init() {
		workSubEstimateGroup = new WorkSubEstimateGroup();
		workSubEstimateGroup.setId(1L);
		workSubEstimateGroup.setOverheadTotal(BigDecimal.ONE);
		workSubEstimateGroup.setLumpsumTotal(BigDecimal.ONE);
		workSubEstimateGroup.setWorkEstimateId(1L);
		workSubEstimateGroup.setDescription(null);
		workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		BeanUtils.copyProperties(workSubEstimateGroup, workSubEstimateGroupDTO);

		groupDTOList = new ArrayList<>();
		groupDTOList.add(workSubEstimateGroupDTO);

		List<WorkSubEstimateGroup> groupList = new ArrayList<>();
		groupList.add(workSubEstimateGroup);
		page = new PageImpl<>(groupList);

		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setId(1L);
		subEstimateDTOList = new ArrayList<>();
		subEstimateDTOList.add(subEstimateDTO);

		workSubEstimateGroupOverheadDTO = getOverheadDTO();
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(1L);
		workSubEstimateGroupOverheadDTO.setSelectedOverheads(selectedOverheads);

		overheadDTOList = new ArrayList<>();
		overheadDTOList.add(workSubEstimateGroupOverheadDTO);
		overheadDTOPage = new PageImpl<>(overheadDTOList);

		workSubEstimateGroupLumpsumDTO = getLumpsumDTO();
		lumpsumDTOList = new ArrayList<>();
		lumpsumDTOList.add(workSubEstimateGroupLumpsumDTO);
		lumpsumDTOPage = new PageImpl<>(lumpsumDTOList);

		workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setGroupOverheadTotal(BigDecimal.ONE);
		workEstimateDTO.setGroupLumpsumTotal(BigDecimal.ONE);
	}

	private WorkSubEstimateGroupOverheadDTO getOverheadDTO() {
		WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO = new WorkSubEstimateGroupOverheadDTO();
		workSubEstimateGroupOverheadDTO.setId(1L);
		workSubEstimateGroupOverheadDTO.setCode("B");
		workSubEstimateGroupOverheadDTO.setConstruct("% of (A)");
		workSubEstimateGroupOverheadDTO.setEnteredValue(BigDecimal.ONE);
		workSubEstimateGroupOverheadDTO.setValueType(ValueType.ADDED);
		return workSubEstimateGroupOverheadDTO;
	}

	private WorkSubEstimateGroupLumpsumDTO getLumpsumDTO() {
		WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO = new WorkSubEstimateGroupLumpsumDTO();
		workSubEstimateGroupLumpsumDTO.setId(1L);
		workSubEstimateGroupLumpsumDTO.setApproxRate(BigDecimal.ONE);
		return workSubEstimateGroupLumpsumDTO;
	}

	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(workSubEstimateGroupMapper.toEntity(workSubEstimateGroupDTO))
				.thenReturn(workSubEstimateGroup);
		PowerMockito.when(workSubEstimateGroupMapper.toDto(workSubEstimateGroup)).thenReturn(workSubEstimateGroupDTO);
	}

	@Test
	public void saveTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.save(workSubEstimateGroup)).thenReturn(workSubEstimateGroup);

		WorkSubEstimateGroupDTO result = workSubEstimateGroupService.save(workSubEstimateGroupDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.save(workSubEstimateGroup)).thenReturn(workSubEstimateGroup);

		PowerMockito.when(workSubEstimateGroupRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroup));

		Optional<WorkSubEstimateGroupDTO> result = workSubEstimateGroupService.partialUpdate(workSubEstimateGroupDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
	}

	@Test
	public void findAllTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.findAll(PageRequest.of(0, 5))).thenReturn(page);

		Page<WorkSubEstimateGroupDTO> result = workSubEstimateGroupService.findAll(PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllTest successful!");
	}

	@Test
	public void findOneTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroup));

		Optional<WorkSubEstimateGroupDTO> result = workSubEstimateGroupService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	@Test
	public void deleteTest() throws Exception {
		workSubEstimateGroupService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@Test
	public void sumOverheadTotalByWorkEstimateIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.sumOverheadTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(new BigDecimal(0));

		BigDecimal result = workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("sumOverheadTotalByWorkEstimateId successful!");
	}

	@Test
	public void findByWorkEstimateIdAndIdTest() throws Exception {
		PowerMockito
				.when(workSubEstimateGroupRepository.findByWorkEstimateIdAndId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroup));

		Optional<WorkSubEstimateGroupDTO> result = workSubEstimateGroupService
				.findByWorkEstimateIdAndId(Mockito.anyLong(), Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkEstimateIdAndIdTest successful!");

	}

	@Test
	public void findByWorkEstimateIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.findByWorkEstimateId(1L, PageRequest.of(0, 5)))
				.thenReturn(page);

		Page<WorkSubEstimateGroupDTO> result = workSubEstimateGroupService.findByWorkEstimateId(1L,
				PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkEstimateIdTest successful!");
	}

	@Test
	public void sumLumpsumTotalByWorkEstimateIdTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.sumLumpsumTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(new BigDecimal(0));

		BigDecimal result = workSubEstimateGroupService.sumLumpsumTotalByWorkEstimateId(Mockito.anyLong());
		Assert.assertNotNull(result);
		log.info("sumLumpsumTotalByWorkEstimateIdTest successful!");
	}

	@Test
	public void recalculateAndUpdateWRTLumpsumTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupLumpsumService.sumApproxRateByGroupId(Mockito.anyLong()))
				.thenReturn(null);
		PowerMockito.when(workSubEstimateGroupRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workSubEstimateGroup));
		PowerMockito.when(workSubEstimateGroupRepository.save(Mockito.any())).thenReturn(workSubEstimateGroup);
		PowerMockito.when(workSubEstimateGroupMapper.toDto(Mockito.anyList())).thenReturn(groupDTOList);
		PowerMockito.when(workEstimateService.partialUpdate(Mockito.any()))
				.thenReturn(Optional.of(new WorkEstimateDTO()));

		workSubEstimateGroupService.recalculateAndUpdateWRTLumpsum(1L, 1L);
		log.info("recalculateAndUpdateWRTLumpsumTest successful!");
	}

	@Test
	public void setSubEstimateDetailsInGroupTest() throws Exception {
		PowerMockito.when(
				subEstimateService.findByWorkEstimateIdAndWorkSubEstimateGroupId(Mockito.anyLong(), Mockito.any()))
				.thenReturn(subEstimateDTOList);
		workSubEstimateGroupService.setSubEstimateDetailsInGroup(1L, workSubEstimateGroupDTO);
		log.info("setSubEstimateDetailsInGroupTest successful!");
	}

	@Test
	public void recalculateGroupWRTSubEstimateTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.findByWorkEstimateId(1L, null)).thenReturn(page);
		PowerMockito.when(workSubEstimateGroupMapper.toDto(Mockito.anyList())).thenReturn(groupDTOList);
		PowerMockito.when(
				subEstimateService.findByWorkEstimateIdAndWorkSubEstimateGroupId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(subEstimateDTOList);
		PowerMockito
				.when(subEstimateService.sumEstimateTotalByWorkEstimateIdAndIds(Mockito.anyLong(), Mockito.anyList()))
				.thenReturn(BigDecimal.ZERO);
		PowerMockito.when(workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueType(Mockito.anyLong(), Mockito.any()))
				.thenReturn(overheadDTOList);
		PowerMockito.when(workSubEstimateGroupOverheadService.partialUpdate(Mockito.any()))
				.thenReturn(Optional.of(workSubEstimateGroupOverheadDTO));
		PowerMockito.when(workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(Mockito.anyLong(),
						Mockito.any()))
				.thenReturn(overheadDTOList);
		PowerMockito.when(workSubEstimateGroupOverheadService.sumOverheadValueByGroupIdAndCodeIn(Mockito.anyLong(),
				Mockito.anyList())).thenReturn(BigDecimal.ZERO);

		workSubEstimateGroupService.recalculateGroupWRTSubEstimate(1L);
		log.info("recalculateGroupWRTSubEstimateTest successful!");
	}

	@Test
	public void recalculateGroupWRTSubEstimate_noSubEstimatesTest() throws Exception {
		PowerMockito.when(workSubEstimateGroupRepository.findByWorkEstimateId(1L, null)).thenReturn(page);
		PowerMockito.when(workSubEstimateGroupMapper.toDto(Mockito.anyList())).thenReturn(groupDTOList);
		PowerMockito.when(
				subEstimateService.findByWorkEstimateIdAndWorkSubEstimateGroupId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(null);
		PowerMockito.when(workSubEstimateGroupOverheadService.findByWorkSubEstimateGroupId(1L, null))
				.thenReturn(overheadDTOPage);
		PowerMockito.when(workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupId(1L, null))
				.thenReturn(lumpsumDTOPage);
		PowerMockito.when(workEstimateService.findOne(Mockito.anyLong())).thenReturn(Optional.of(workEstimateDTO));
		PowerMockito.when(workEstimateService.partialUpdate(Mockito.any())).thenReturn(Optional.of(workEstimateDTO));

		workSubEstimateGroupService.recalculateGroupWRTSubEstimate(1L);
		log.info("recalculateGroupWRTSubEstimate_noSubEstimatesTest successful!");
	}

	@Test
	public void deleteGroupTest() throws Exception {
		PowerMockito.when(
				subEstimateService.findByWorkEstimateIdAndWorkSubEstimateGroupId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(subEstimateDTOList);
		PowerMockito.when(workSubEstimateGroupOverheadService.findByWorkSubEstimateGroupId(1L, null))
				.thenReturn(overheadDTOPage);
		PowerMockito.when(workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupId(1L, null))
				.thenReturn(lumpsumDTOPage);
		PowerMockito.when(workEstimateService.findOne(Mockito.anyLong())).thenReturn(Optional.of(workEstimateDTO));
		PowerMockito.when(workEstimateService.partialUpdate(Mockito.any())).thenReturn(Optional.of(workEstimateDTO));

		workSubEstimateGroupService.deleteGroup(workSubEstimateGroupDTO);
		log.info("deleteGroupTest successful!");
	}
}