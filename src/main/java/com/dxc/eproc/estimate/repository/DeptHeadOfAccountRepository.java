package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.DeptHeadOfAccount;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the DeptHeadOfAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeptHeadOfAccountRepository extends JpaRepository<DeptHeadOfAccount, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<DeptHeadOfAccount> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all dept head of account by dept id and active yn.
	 *
	 * @param deptId the dept id
	 * @param b      the b
	 * @return the list
	 */
	List<DeptHeadOfAccount> findAllDeptHeadOfAccountByDeptIdAndActiveYn(Long deptId, boolean b);
}
