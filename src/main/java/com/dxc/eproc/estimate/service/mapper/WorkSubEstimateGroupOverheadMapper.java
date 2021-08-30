package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkSubEstimateGroupOverhead} and its DTO
 * {@link WorkSubEstimateGroupOverheadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkSubEstimateGroupOverheadMapper
		extends EntityMapper<WorkSubEstimateGroupOverheadDTO, WorkSubEstimateGroupOverhead> {
}
