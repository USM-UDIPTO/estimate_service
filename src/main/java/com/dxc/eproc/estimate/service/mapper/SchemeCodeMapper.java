package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchemeCode} and its DTO {@link SchemeCodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchemeCodeMapper extends EntityMapper<SchemeCodeDTO, SchemeCode> {
}
