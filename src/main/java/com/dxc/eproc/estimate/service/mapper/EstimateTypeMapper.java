package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link EstimateType} and its DTO
 * {@link EstimateTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstimateTypeMapper extends EntityMapper<EstimateTypeDTO, EstimateType> {
}
