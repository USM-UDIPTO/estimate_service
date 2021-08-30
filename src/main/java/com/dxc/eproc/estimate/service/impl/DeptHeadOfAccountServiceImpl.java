package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.DeptHeadOfAccount;
import com.dxc.eproc.estimate.repository.DeptHeadOfAccountRepository;
import com.dxc.eproc.estimate.service.DeptHeadOfAccountService;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;
import com.dxc.eproc.estimate.service.mapper.DeptHeadOfAccountMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link DeptHeadOfAccount}.
 */
@Service
@Transactional
public class DeptHeadOfAccountServiceImpl implements DeptHeadOfAccountService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DeptHeadOfAccountServiceImpl.class);

	/** The dept head of account repository. */
	private final DeptHeadOfAccountRepository deptHeadOfAccountRepository;

	/** The dept head of account mapper. */
	private final DeptHeadOfAccountMapper deptHeadOfAccountMapper;

	/**
	 * Instantiates a new dept head of account service impl.
	 *
	 * @param deptHeadOfAccountRepository the dept head of account repository
	 * @param deptHeadOfAccountMapper     the dept head of account mapper
	 */
	public DeptHeadOfAccountServiceImpl(DeptHeadOfAccountRepository deptHeadOfAccountRepository,
			DeptHeadOfAccountMapper deptHeadOfAccountMapper) {
		this.deptHeadOfAccountRepository = deptHeadOfAccountRepository;
		this.deptHeadOfAccountMapper = deptHeadOfAccountMapper;
	}

	/**
	 * Save.
	 *
	 * @param deptHeadOfAccountDTO the dept head of account DTO
	 * @return the dept head of account DTO
	 */
	@Override
	public DeptHeadOfAccountDTO save(DeptHeadOfAccountDTO deptHeadOfAccountDTO) {
		DeptHeadOfAccount deptHeadOfAccount = null;
		if (deptHeadOfAccountDTO.getId() == null) {
			log.debug("Request to save DeptHeadOfAccount");
			deptHeadOfAccount = deptHeadOfAccountMapper.toEntity(deptHeadOfAccountDTO);
		} else {
			log.debug("Request to update DeptHeadOfAccount - Id : {}", deptHeadOfAccountDTO.getId());
			Optional<DeptHeadOfAccount> deptHeadOfAccountOptional = deptHeadOfAccountRepository
					.findById(deptHeadOfAccountDTO.getId());
			if (deptHeadOfAccountOptional.isPresent()) {
				deptHeadOfAccount = deptHeadOfAccountOptional.get();
				deptHeadOfAccount.activeYn(deptHeadOfAccountDTO.getActiveYn()).deptId(deptHeadOfAccountDTO.getDeptId())
						.headOfAccount(deptHeadOfAccountDTO.getHeadOfAccount())
						.hoaDescription(deptHeadOfAccountDTO.getHoaDescription());
			}
		}
		deptHeadOfAccount = deptHeadOfAccountRepository.save(deptHeadOfAccount);
		return deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
	}

	/**
	 * Partial update.
	 *
	 * @param deptHeadOfAccountDTO the dept head of account DTO
	 * @return the optional
	 */
	@Override
	public Optional<DeptHeadOfAccountDTO> partialUpdate(DeptHeadOfAccountDTO deptHeadOfAccountDTO) {
		log.debug("Request to partially update DeptHeadOfAccount : {}", deptHeadOfAccountDTO);

		return deptHeadOfAccountRepository.findById(deptHeadOfAccountDTO.getId()).map(existingDeptHeadOfAccount -> {
			deptHeadOfAccountMapper.partialUpdate(existingDeptHeadOfAccount, deptHeadOfAccountDTO);
			return existingDeptHeadOfAccount;
		}).map(deptHeadOfAccountRepository::save).map(deptHeadOfAccountMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DeptHeadOfAccountDTO> findAll(Pageable pageable) {
		log.debug("Request to get all DeptHeadOfAccounts");
		return deptHeadOfAccountRepository.findAll(pageable).map(deptHeadOfAccountMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DeptHeadOfAccountDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all active DeptHeadOfAccounts");
		return deptHeadOfAccountRepository.findAllByActiveYn(true, pageable).map(deptHeadOfAccountMapper::toDto);
	}

	@Override
	public List<DeptHeadOfAccountDTO> findAllDeptHeadOfAccountByDeptIdAndActiveYn(Long deptId) {
		// TODO Auto-generated method stub
		return deptHeadOfAccountMapper.toDto(deptHeadOfAccountRepository.findAllDeptHeadOfAccountByDeptIdAndActiveYn(deptId, true));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DeptHeadOfAccountDTO> findOne(Long id) {
		log.debug("Request to get DeptHeadOfAccount : {}", id);
		return deptHeadOfAccountRepository.findById(id).map(deptHeadOfAccountMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete DeptHeadOfAccount : {}", id);
		Optional<DeptHeadOfAccount> deptHeadOfAccountOptional = deptHeadOfAccountRepository.findById(id);
		if (deptHeadOfAccountOptional.isPresent()) {
			DeptHeadOfAccount deptHeadOfAccount = deptHeadOfAccountOptional.get();
			deptHeadOfAccount.activeYn(false);
			deptHeadOfAccountRepository.save(deptHeadOfAccount);
		}
	}
}
