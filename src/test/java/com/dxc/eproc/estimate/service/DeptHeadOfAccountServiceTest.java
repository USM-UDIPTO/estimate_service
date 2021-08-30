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

import com.dxc.eproc.estimate.model.DeptHeadOfAccount;
import com.dxc.eproc.estimate.repository.DeptHeadOfAccountRepository;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;
import com.dxc.eproc.estimate.service.impl.DeptHeadOfAccountServiceImpl;
import com.dxc.eproc.estimate.service.mapper.DeptHeadOfAccountMapperImpl;

public class DeptHeadOfAccountServiceTest extends PowerMockTestCase {

	private static final Logger log = LoggerFactory.getLogger(DeptHeadOfAccountServiceTest.class);
	private DeptHeadOfAccountService deptHeadOfAccountService;

	/** The workEstimateItem LBDMapper. */
	@InjectMocks
	private DeptHeadOfAccountMapperImpl deptHeadOfAccountMapper;

	/** The supplier info repository. */
	@Mock
	private DeptHeadOfAccountRepository deptHeadOfAccountRepository;

	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class -DeptHeadOfAccountServiceTest: init");
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - DeptHeadOfAccountTest: setUp");

		deptHeadOfAccountService = new DeptHeadOfAccountServiceImpl(deptHeadOfAccountRepository,
				deptHeadOfAccountMapper);
	}

	private DeptHeadOfAccount createDeptHeadOfAccount() {

		DeptHeadOfAccount deptHeadOfAccount = new DeptHeadOfAccount().activeYn(false);

		return deptHeadOfAccount;
	}

	public List<DeptHeadOfAccount> createdeptHeadOfAccountList() {
		List<DeptHeadOfAccount> deptHeadOfAccountList = new ArrayList<>();

		DeptHeadOfAccount firstdeptHeadOfAccount = new DeptHeadOfAccount().id(1L).activeYn(false);
		DeptHeadOfAccount seconddeptHeadOfAccount = new DeptHeadOfAccount().id(2L).activeYn(false);

		deptHeadOfAccountList.add(firstdeptHeadOfAccount);
		deptHeadOfAccountList.add(seconddeptHeadOfAccount);

		return deptHeadOfAccountList;
	}

	@Test
	public void saveDeptHeadOfAccount() throws Exception {
		log.info("==========================================================================");
		log.info("Test - savedeptHeadOfAccount - Start");
		DeptHeadOfAccount deptHeadOfAccount = createDeptHeadOfAccount();
		deptHeadOfAccount.setId(null);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);

		Mockito.when(deptHeadOfAccountRepository.save(deptHeadOfAccount)).thenReturn(deptHeadOfAccount);

		deptHeadOfAccountDTO = deptHeadOfAccountService.save(deptHeadOfAccountDTO);
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
	public void saveDeptHeadOfAccount_NotNullTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - savedeptHeadOfAccount - Start");
		DeptHeadOfAccount deptHeadOfAccount = createDeptHeadOfAccount();
		deptHeadOfAccount.setId(1001L);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
		PowerMockito.when(deptHeadOfAccountRepository.findById(deptHeadOfAccountDTO.getId()))
				.thenReturn(Optional.of(deptHeadOfAccount));
		Mockito.when(deptHeadOfAccountRepository.save(deptHeadOfAccount)).thenReturn(deptHeadOfAccount);
		deptHeadOfAccountDTO = deptHeadOfAccountService.save(deptHeadOfAccountDTO);
		Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		Assert.assertEquals(deptHeadOfAccount.getDeptId(), deptHeadOfAccountDTO.getDeptId());
		Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(), deptHeadOfAccountDTO.getHeadOfAccount());
		Assert.assertEquals(deptHeadOfAccount.getHoaDescription(), deptHeadOfAccountDTO.getHoaDescription());
		Assert.assertEquals(deptHeadOfAccount.getActiveYn(), deptHeadOfAccountDTO.getActiveYn());
		log.info("==========================================================================");
		log.info("Test - saveDeptHeadOfAccount - End");
	}

	@Test
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 1L;

		DeptHeadOfAccount deptHeadOfAccount = createDeptHeadOfAccount();
		deptHeadOfAccount.setId(ID);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
		deptHeadOfAccountDTO.setActiveYn(false);
		deptHeadOfAccountDTO.setDeptId(ID);

		PowerMockito.when(deptHeadOfAccountRepository.findById(deptHeadOfAccountDTO.getId()))
				.thenReturn(Optional.of(deptHeadOfAccount));
		PowerMockito.when(deptHeadOfAccountRepository.save(Mockito.any())).thenReturn(deptHeadOfAccount);

		Optional<DeptHeadOfAccountDTO> optionaldeptHeadOfAccountDTO = deptHeadOfAccountService
				.partialUpdate(deptHeadOfAccountDTO);
		log.info("Response data:partialUpdateTest: " + optionaldeptHeadOfAccountDTO);

		if (optionaldeptHeadOfAccountDTO.isPresent()) {
			deptHeadOfAccountDTO = optionaldeptHeadOfAccountDTO.get();
		}

		Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		Assert.assertEquals(deptHeadOfAccount.getDeptId(), deptHeadOfAccountDTO.getDeptId());
		Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(), deptHeadOfAccountDTO.getHeadOfAccount());
		Assert.assertEquals(deptHeadOfAccount.getHoaDescription(), deptHeadOfAccountDTO.getHoaDescription());
		Assert.assertEquals(deptHeadOfAccount.getActiveYn(), deptHeadOfAccountDTO.getActiveYn());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	@Test
	public void delete() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createDeptHeadOfAccount();
		PowerMockito.when(deptHeadOfAccountRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(deptHeadOfAccount));
		deptHeadOfAccountService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@Test
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 1L;
		// Optional<DeptHeadOfAccount> deptHeadOfAccountOpt = null;

		DeptHeadOfAccount deptHeadOfAccount = createDeptHeadOfAccount();
		deptHeadOfAccount.setId(ID);
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);

		PowerMockito.when(deptHeadOfAccountRepository.findById(deptHeadOfAccountDTO.getId()))
				.thenReturn(Optional.of(deptHeadOfAccount));

		Optional<DeptHeadOfAccountDTO> optionaldeptHeadOfAccountDTO = deptHeadOfAccountService.findOne(ID);
		log.info("Response data:partialUpdateTest: " + optionaldeptHeadOfAccountDTO);

		if (optionaldeptHeadOfAccountDTO.isPresent()) {
			deptHeadOfAccountDTO = optionaldeptHeadOfAccountDTO.get();
		}

		Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		Assert.assertEquals(deptHeadOfAccount.getDeptId(), deptHeadOfAccountDTO.getDeptId());
		Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(), deptHeadOfAccountDTO.getHeadOfAccount());
		Assert.assertEquals(deptHeadOfAccount.getHoaDescription(), deptHeadOfAccountDTO.getHoaDescription());
		Assert.assertEquals(deptHeadOfAccount.getActiveYn(), deptHeadOfAccountDTO.getActiveYn());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<DeptHeadOfAccountDTO> responseDTO = null;
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<DeptHeadOfAccount> deptHeadOfAccountList = createdeptHeadOfAccountList();
		Page<DeptHeadOfAccount> deptHeadOfAccountPage = new PageImpl<>(deptHeadOfAccountList);

		PowerMockito.when(deptHeadOfAccountRepository.findAllByActiveYn(true, pageable))
				.thenReturn(deptHeadOfAccountPage);

		responseDTO = deptHeadOfAccountService.findAllActive(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		deptHeadOfAccountDTO = responseDTO.get().findFirst().get();
		DeptHeadOfAccount deptHeadOfAccount = deptHeadOfAccountList.get(0);

		Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		Assert.assertEquals(deptHeadOfAccount.getDeptId(), deptHeadOfAccountDTO.getDeptId());
		Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(), deptHeadOfAccountDTO.getHeadOfAccount());
		Assert.assertEquals(deptHeadOfAccount.getHoaDescription(), deptHeadOfAccountDTO.getHoaDescription());
		Assert.assertEquals(deptHeadOfAccount.getActiveYn(), deptHeadOfAccountDTO.getActiveYn());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	@Test
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<DeptHeadOfAccountDTO> responseDTO = null;
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<DeptHeadOfAccount> deptHeadOfAccountList = createdeptHeadOfAccountList();
		Page<DeptHeadOfAccount> deptHeadOfAccountPage = new PageImpl<>(deptHeadOfAccountList);

		PowerMockito.when(deptHeadOfAccountRepository.findAll(pageable)).thenReturn(deptHeadOfAccountPage);

		responseDTO = deptHeadOfAccountService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		deptHeadOfAccountDTO = responseDTO.get().findFirst().get();
		DeptHeadOfAccount deptHeadOfAccount = deptHeadOfAccountList.get(0);

		Assert.assertEquals(deptHeadOfAccount.getId(), deptHeadOfAccountDTO.getId());
		Assert.assertEquals(deptHeadOfAccount.getDeptId(), deptHeadOfAccountDTO.getDeptId());
		Assert.assertEquals(deptHeadOfAccount.getHeadOfAccount(), deptHeadOfAccountDTO.getHeadOfAccount());
		Assert.assertEquals(deptHeadOfAccount.getHoaDescription(), deptHeadOfAccountDTO.getHoaDescription());
		Assert.assertEquals(deptHeadOfAccount.getActiveYn(), deptHeadOfAccountDTO.getActiveYn());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	@Test
	public void findAllDeptHeadOfAccountByDeptIdAndActiveYn() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		final Long ID = 1L;
		// Optional<DeptHeadOfAccount> deptHeadOfAccountOpt = null;
		List<DeptHeadOfAccount> deptHeadOfAccountList = createdeptHeadOfAccountList();

		PowerMockito.when(deptHeadOfAccountRepository.findAllDeptHeadOfAccountByDeptIdAndActiveYn(ID, false))
				.thenReturn(deptHeadOfAccountList);

		deptHeadOfAccountService.findAllDeptHeadOfAccountByDeptIdAndActiveYn(ID);

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}
}
