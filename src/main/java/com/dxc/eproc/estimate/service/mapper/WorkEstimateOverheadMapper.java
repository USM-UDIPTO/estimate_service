package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.WorkEstimateOverhead;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;

/**
 * Mapper for the entity {@link WorkEstimateOverhead} and its DTO {@link WorkEstimateOverheadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateOverheadMapper extends EntityMapper<WorkEstimateOverheadDTO, WorkEstimateOverhead> {}
