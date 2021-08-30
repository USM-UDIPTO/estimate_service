package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkCategoryAttribute} and its DTO
 * {@link WorkCategoryAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkCategoryAttributeMapper extends EntityMapper<WorkCategoryAttributeDTO, WorkCategoryAttribute> {
}
