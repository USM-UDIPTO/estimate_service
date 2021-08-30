package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkType} and its DTO {@link WorkTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkTypeMapper extends EntityMapper<WorkTypeDTO, WorkType> {
}
