package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.RaConfiguration;
import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RaConfiguration} and its DTO {@link RaConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaConfigurationMapper extends EntityMapper<RaConfigurationDTO, RaConfiguration> {}
