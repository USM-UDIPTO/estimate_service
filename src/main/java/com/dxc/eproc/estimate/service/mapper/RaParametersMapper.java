package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.RaParameters;
import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RaParameters} and its DTO {@link RaParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaParametersMapper extends EntityMapper<RaParametersDTO, RaParameters> {}
