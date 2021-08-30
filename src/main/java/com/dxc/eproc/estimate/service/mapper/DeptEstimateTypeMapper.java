package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeptEstimateType} and its DTO
 * {@link DeptEstimateTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeptEstimateTypeMapper extends EntityMapper<DeptEstimateTypeDTO, DeptEstimateType> {
}
