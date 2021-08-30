package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkSubEstimateGroupLumpsum} and its DTO
 * {@link WorkSubEstimateGroupLumpsumDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkSubEstimateGroupLumpsumMapper
		extends EntityMapper<WorkSubEstimateGroupLumpsumDTO, WorkSubEstimateGroupLumpsum> {
}
