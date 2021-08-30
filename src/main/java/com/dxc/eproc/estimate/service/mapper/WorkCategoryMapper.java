package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkCategory} and its DTO
 * {@link WorkCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkCategoryMapper extends EntityMapper<WorkCategoryDTO, WorkCategory> {
}
