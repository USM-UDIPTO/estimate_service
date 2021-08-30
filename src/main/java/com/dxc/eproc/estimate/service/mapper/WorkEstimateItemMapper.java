package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkEstimateItem} and its DTO
 * {@link WorkEstimateItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateItemMapper extends EntityMapper<WorkEstimateItemDTO, WorkEstimateItem> {
}
