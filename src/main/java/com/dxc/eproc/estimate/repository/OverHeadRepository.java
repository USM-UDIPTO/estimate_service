package com.dxc.eproc.estimate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.OverHead;

/**
 * Spring Data SQL repository for the OverHead entity.
 */
@Repository
public interface OverHeadRepository extends JpaRepository<OverHead, Long> {

	Page<OverHead> findAllByActiveYn(boolean b, Pageable pageable);
	
}
