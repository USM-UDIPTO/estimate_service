package com.dxc.eproc.estimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.SeriesTable;

/**
 * The Interface SeriesTableRepository.
 */
@Repository
public interface SeriesTableRepository extends JpaRepository<SeriesTable, String> {

}
