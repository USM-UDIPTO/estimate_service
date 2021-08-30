package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkSubEstimateGroup} and its DTO
 * {@link WorkSubEstimateGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkSubEstimateGroupMapper extends EntityMapper<WorkSubEstimateGroupDTO, WorkSubEstimateGroup> {
}
