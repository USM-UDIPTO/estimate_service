package com.dxc.eproc.estimate.repository;

import com.dxc.eproc.estimate.model.RaParameters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RaParameters entity.
 */
@Repository
public interface RaParametersRepository extends JpaRepository<RaParameters, Long> {}
