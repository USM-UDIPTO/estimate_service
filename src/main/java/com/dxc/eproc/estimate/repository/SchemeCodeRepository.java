package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.SchemeType;
import com.dxc.eproc.estimate.model.SchemeCode;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the SchemeCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemeCodeRepository extends JpaRepository<SchemeCode, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<SchemeCode> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all by location id and scheme type and scheme code.
	 *
	 * @param locationId the location id
	 * @param schemeType the scheme type
	 * @param schemeCode the scheme code
	 * @return the list
	 */
	List<SchemeCode> findAllByLocationIdAndSchemeTypeAndSchemeCode(Long locationId, SchemeType schemeType,
			String schemeCode);
}
