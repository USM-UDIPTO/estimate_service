package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkEstimate} and its DTO
 * {@link WorkEstimateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateMapper extends EntityMapper<WorkEstimateDTO, WorkEstimate> {
}
