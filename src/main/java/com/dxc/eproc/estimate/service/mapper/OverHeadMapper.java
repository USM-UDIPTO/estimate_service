package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.OverHead;
import com.dxc.eproc.estimate.service.dto.OverHeadDTO;

/**
 * Mapper for the entity {@link OverHead} and its DTO {@link OverHeadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OverHeadMapper extends EntityMapper<OverHeadDTO, OverHead> {}
