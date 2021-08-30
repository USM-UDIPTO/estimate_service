package com.dxc.eproc.estimate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.RaConfiguration;

/**
 * Spring Data SQL repository for the RaConfiguration entity.
 */
@Repository
public interface RaConfigurationRepository extends JpaRepository<RaConfiguration, Long> {

	List<RaConfiguration> findAllByDeptId(Long deptId);}
