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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.SchemeCode;
import com.dxc.eproc.estimate.repository.SchemeCodeRepository;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;
import com.dxc.eproc.estimate.service.impl.SchemeCodeServiceImpl;
import com.dxc.eproc.estimate.service.mapper.SchemeCodeMapperImpl;

public class SchemeCodeServiceTest extends PowerMockTestCase {
	private static final Logger log = LoggerFactory.getLogger(SchemeCodeServiceTest.class);

	private static SchemeCodeDTO schemeCodeDTO;
	private SchemeCodeService schemeCodeService;


	@InjectMocks
	private SchemeCodeMapperImpl schemeCodeMapper;

	@Mock
	private SchemeCodeRepository schemeCodeRepository;

	@BeforeClass
	public void init() {
		log.info("============================");
		log.info("this is excuted once per test class-SchemeCodeServiceTest:init");
	}

	@BeforeMethod
	public void setUp() {
		log.info("============================");
		log.info("this is excuted before each test- SchemeCodeServiceTest:setup");
		schemeCodeService = new SchemeCodeServiceImpl(schemeCodeRepository, schemeCodeMapper);
	}

	private SchemeCode createSchemeCode() {
		SchemeCode schemeCode = new SchemeCode().activeYn(false);

		return schemeCode;
	}

	public List<SchemeCode> createschemeCodeList() {
		List<SchemeCode> schemeCodeList = new ArrayList<>();
		SchemeCode firstschemeCode = new SchemeCode().id(1L).activeYn(false);
		SchemeCode secondschemeCode = new SchemeCode().id(2L).activeYn(false);
		schemeCodeList.add(firstschemeCode);
		schemeCodeList.add(secondschemeCode);
		return schemeCodeList;
	}

	@Test
	public void saveSchemeCode() throws Exception {
		log.info("==========================================================================");
		log.info("Test - saveSchemeCode - Start");
		SchemeCode schemeCode = createSchemeCode();
		schemeCode.setId(null);
		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);

		Mockito.when(schemeCodeRepository.save(schemeCode)).thenReturn(schemeCode);

		schemeCodeDTO = schemeCodeService.save(schemeCodeDTO);
		/*
		 * Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		 * Assert.assertEquals(deptHeadOfAccount.getDeptId(),
		 * deptHeadOfAccountDTO.getDeptId());
		 * Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(),
		 * deptHeadOfAccountDTO.getHeadOfAccount());
		 * Assert.assertEquals(deptHeadOfAccount.getHoaDescription(),
		 * deptHeadOfAccountDTO.getHoaDescription());
		 * Assert.assertEquals(deptHeadOfAccount.getActiveYn(),
		 * deptHeadOfAccountDTO.getActiveYn());
		 */
		log.info("==========================================================================");
		log.info("Test - saveDeptHeadOfAccount - End");

	}

	@Test
	public void saveSchemeCode_NotNullTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - savedeptHeadOfAccount - Start");
		SchemeCode schemeCode = createSchemeCode();
		schemeCode.setId(1001L);
		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);
		PowerMockito.when(schemeCodeRepository.findById(schemeCodeDTO.getId())).thenReturn(Optional.of(schemeCode));
		Mockito.when(schemeCodeRepository.save(schemeCode)).thenReturn(schemeCode);
		schemeCodeDTO = schemeCodeService.save(schemeCodeDTO);
		Assert.assertEquals(schemeCode.getLocationId(), schemeCodeDTO.getLocationId());
		Assert.assertEquals(schemeCode.getSchemeCode(), schemeCodeDTO.getSchemeCode());
		Assert.assertEquals(schemeCode.getSchemeType(), schemeCodeDTO.getSchemeType());
		Assert.assertEquals(schemeCode.getSchemeStatus(), schemeCodeDTO.getSchemeStatus());
		Assert.assertEquals(schemeCode.getActiveYn(), schemeCodeDTO.getActiveYn());
		log.info("==========================================================================");
		log.info("Test - saveDeptHeadOfAccount - End");
	}

	@Test
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 1L;

		SchemeCode schemeCode = createSchemeCode();
		schemeCode.setId(ID);
		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);
		schemeCodeDTO.setActiveYn(false);
		schemeCodeDTO.setLocationId(ID);

		PowerMockito.when(schemeCodeRepository.findById(schemeCodeDTO.getId())).thenReturn(Optional.of(schemeCode));
		PowerMockito.when(schemeCodeRepository.save(Mockito.any())).thenReturn(schemeCode);

		Optional<SchemeCodeDTO> optionalschemeCodeDTO = schemeCodeService.partialUpdate(schemeCodeDTO);
		log.info("Response data:partialUpdateTest: " + optionalschemeCodeDTO);

		if (optionalschemeCodeDTO.isPresent()) {
			schemeCodeDTO = optionalschemeCodeDTO.get();
		}

		Assert.assertEquals(schemeCode.getLocationId(), schemeCodeDTO.getLocationId());
		Assert.assertEquals(schemeCode.getSchemeCode(), schemeCodeDTO.getSchemeCode());
		Assert.assertEquals(schemeCode.getSchemeType(), schemeCodeDTO.getSchemeType());
		Assert.assertEquals(schemeCode.getSchemeStatus(), schemeCodeDTO.getSchemeStatus());
		Assert.assertEquals(schemeCode.getActiveYn(), schemeCodeDTO.getActiveYn());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	@Test
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 1L;
		// Optional<SchemeCode> schemeCodeOpt = null;

		SchemeCode schemeCode = createSchemeCode();
		schemeCode.setId(ID);

		PowerMockito.when(schemeCodeRepository.findById(schemeCodeDTO.getId())).thenReturn(Optional.of(schemeCode));

		Optional<SchemeCodeDTO> optionalschemeCodeDTO = schemeCodeService.findOne(ID);
		log.info("Response data:partialUpdateTest: " + optionalschemeCodeDTO);

		if (optionalschemeCodeDTO.isPresent()) {
			schemeCodeDTO = optionalschemeCodeDTO.get();
		}

		Assert.assertEquals(schemeCode.getLocationId(), schemeCodeDTO.getLocationId());
		Assert.assertEquals(schemeCode.getSchemeCode(), schemeCodeDTO.getSchemeCode());
		Assert.assertEquals(schemeCode.getSchemeType(), schemeCodeDTO.getSchemeType());
		Assert.assertEquals(schemeCode.getSchemeStatus(), schemeCodeDTO.getSchemeStatus());
		Assert.assertEquals(schemeCode.getActiveYn(), schemeCodeDTO.getActiveYn());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<SchemeCodeDTO> responseDTO = null;
		SchemeCodeDTO schemeCodeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<SchemeCode> schemeCodeList = createschemeCodeList();
		Page<SchemeCode> schemeCodePage = new PageImpl<>(schemeCodeList);

		PowerMockito.when(schemeCodeRepository.findAllByActiveYn(true, pageable)).thenReturn(schemeCodePage);

		responseDTO = schemeCodeService.findAllActive(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		schemeCodeDTO = responseDTO.get().findFirst().get();
		SchemeCode schemeCode = schemeCodeList.get(0);

		Assert.assertEquals(schemeCode.getLocationId(), schemeCodeDTO.getLocationId());
		Assert.assertEquals(schemeCode.getSchemeCode(), schemeCodeDTO.getSchemeCode());
		Assert.assertEquals(schemeCode.getSchemeType(), schemeCodeDTO.getSchemeType());
		Assert.assertEquals(schemeCode.getSchemeStatus(), schemeCodeDTO.getSchemeStatus());
		Assert.assertEquals(schemeCode.getActiveYn(), schemeCodeDTO.getActiveYn());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	@Test
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<SchemeCodeDTO> responseDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<SchemeCode> schemeCodeList = createschemeCodeList();
		Page<SchemeCode> schemeCodePage = new PageImpl<>(schemeCodeList);

		PowerMockito.when(schemeCodeRepository.findAll(pageable)).thenReturn(schemeCodePage);

		responseDTO = schemeCodeService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		schemeCodeDTO = responseDTO.get().findFirst().get();
		SchemeCode schemeCode = schemeCodeList.get(0);

		Assert.assertEquals(schemeCode.getLocationId(), schemeCodeDTO.getLocationId());
		Assert.assertEquals(schemeCode.getSchemeCode(), schemeCodeDTO.getSchemeCode());
		Assert.assertEquals(schemeCode.getSchemeType(), schemeCodeDTO.getSchemeType());
		Assert.assertEquals(schemeCode.getSchemeStatus(), schemeCodeDTO.getSchemeStatus());
		Assert.assertEquals(schemeCode.getActiveYn(), schemeCodeDTO.getActiveYn());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	@Test
	public void findAllByLocationIdAndSchemeTypeAndSchemeCode() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		final Long ID = 1L;

		List<SchemeCode> schemeCodeList = createschemeCodeList();

		PowerMockito.when(schemeCodeRepository.findAllByLocationIdAndSchemeTypeAndSchemeCode(1L, null, "Test"))
		.thenReturn(schemeCodeList);

		schemeCodeService.findAllByLocationIdAndSchemeTypeAndSchemeCode(ID, null, null);

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test
	public void delete() throws Exception {
		SchemeCode schemeCode = createSchemeCode();
		PowerMockito.when(schemeCodeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(schemeCode));
		schemeCodeService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

}