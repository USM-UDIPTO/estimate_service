package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import org.mapstruct.*;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link SubEstimate} and its DTO {@link SubEstimateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubEstimateMapper extends EntityMapper<SubEstimateDTO, SubEstimate> {
}
