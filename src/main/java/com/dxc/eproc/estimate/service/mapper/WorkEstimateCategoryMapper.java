package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.model.WorkEstimateCategory;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateCategoryDTO;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkCategory} and its DTO
 * {@link WorkCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateCategoryMapper extends EntityMapper<WorkEstimateCategoryDTO, WorkEstimateCategory> {
}
