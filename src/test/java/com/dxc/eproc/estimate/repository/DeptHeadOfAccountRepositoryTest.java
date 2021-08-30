package com.dxc.eproc.estimate.repository;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.DeptHeadOfAccount;

@SpringBootTest
@ActiveProfiles("test")
public class DeptHeadOfAccountRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(DeptHeadOfAccountRepositoryTest.class);

	private static DeptHeadOfAccount deptHeadOfAccount;

	private static Optional<DeptHeadOfAccount> deptHeadOfAccountOptional;

	@Autowired
	DeptHeadOfAccountRepository deptHeadOfAccountRepository;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		deptHeadOfAccount = new DeptHeadOfAccount();

	}

	public static DeptHeadOfAccount createEntity() {
		DeptHeadOfAccount deptHeadOfAccount = new DeptHeadOfAccount();
		deptHeadOfAccount.setDeptId(1L);
		deptHeadOfAccount.setHeadOfAccount("test");
		deptHeadOfAccount.setHoaDescription("Test");
		deptHeadOfAccount.setActiveYn(false);
		return deptHeadOfAccount;
	}

	@Test
	public void createTest() {
		log.info("==================================================================================");
		log.info("Test Start- create Start");
		deptHeadOfAccount = createEntity();
		DeptHeadOfAccount testdeptHeadOfAccount = deptHeadOfAccountRepository.save(deptHeadOfAccount);
		Assert.assertEquals(testdeptHeadOfAccount.getId(), deptHeadOfAccount.getId());
		log.info("==================================================================================");
		log.info("Test End- create End");
	}

	@Test
	public void updatedeptHeadOfAccountTest() {
		log.info("==================================================================================");
		log.info("Test Start- partial Update");
		DeptHeadOfAccount deptAccount = createEntity();
		deptHeadOfAccountRepository.save(deptAccount);
		deptHeadOfAccountOptional = deptHeadOfAccountRepository.findById(deptAccount.getId());
		DeptHeadOfAccount deptHeadOfAccount = new DeptHeadOfAccount();
		deptHeadOfAccount.setId(deptHeadOfAccountOptional.get().getId());
		deptHeadOfAccount.setDeptId(deptHeadOfAccountOptional.get().getDeptId());
		deptHeadOfAccount.setHeadOfAccount(deptHeadOfAccountOptional.get().getHeadOfAccount());
		deptHeadOfAccount.setHoaDescription(deptHeadOfAccountOptional.get().getHeadOfAccount());
		deptHeadOfAccount.setActiveYn(deptHeadOfAccountOptional.get().getActiveYn());

		DeptHeadOfAccount testdeptHeadOfAccount = deptHeadOfAccountRepository.save(deptHeadOfAccount);
		Assert.assertEquals(testdeptHeadOfAccount.getId(), deptHeadOfAccount.getId());
		log.info("==================================================================================");
		log.info("Test End- partial Update");
	}

	@Test
	public void getAlldeptHeadOfAccountTest() {
		log.info("==================================================================================");
		log.info("Test Start- Get All getAlldeptHeadOfAccountTest");
		List<DeptHeadOfAccount> deptHeadOfAccountList = deptHeadOfAccountRepository.findAll();
		Assert.assertNotNull(deptHeadOfAccountList);
		log.info("==================================================================================");
		log.info("Test End- Get All getAlldeptHeadOfAccountTest");
	}

	@Test
	public void findAllByActiveYnTest() {
		Page<DeptHeadOfAccount> result = deptHeadOfAccountRepository.findAllByActiveYn(true, PageRequest.of(0, 5));
		Assert.assertTrue(result.isEmpty());
		log.info("findAllByActiveYnTest successful");
	}

	@Test
	public void findAllDeptHeadOfAccountByDeptIdAndActiveYnTest() {
		List<DeptHeadOfAccount> result = deptHeadOfAccountRepository.findAllDeptHeadOfAccountByDeptIdAndActiveYn(1L,
				true);
		Assert.assertTrue(result.isEmpty());
		log.info("findAllDeptHeadOfAccountByDeptIdAndActiveYnTest successful");
	}

}
