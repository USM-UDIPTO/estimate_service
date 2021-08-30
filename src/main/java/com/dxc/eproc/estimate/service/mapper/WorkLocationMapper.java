package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link WorkLocation} and its DTO
 * {@link WorkLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkLocationMapper extends EntityMapper<WorkLocationDTO, WorkLocation> {
}
