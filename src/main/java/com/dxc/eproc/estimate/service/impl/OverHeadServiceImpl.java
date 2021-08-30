package com.dxc.eproc.estimate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.OverHead;
import com.dxc.eproc.estimate.repository.OverHeadRepository;
import com.dxc.eproc.estimate.service.OverHeadService;
import com.dxc.eproc.estimate.service.dto.OverHeadDTO;
import com.dxc.eproc.estimate.service.mapper.OverHeadMapper;

/**
 * Service Implementation for managing {@link OverHead}.
 */
@Service
@Transactional
public class OverHeadServiceImpl implements OverHeadService {

    private final Logger log = LoggerFactory.getLogger(OverHeadServiceImpl.class);

    @Autowired
    private OverHeadRepository overHeadRepository;

    @Autowired
    private OverHeadMapper overHeadMapper;

    public OverHeadServiceImpl() {
    }

    @Override
    public OverHeadDTO save(OverHeadDTO overHeadDTO) {
    	OverHead overHead = null;
		if (overHeadDTO.getId() == null) {
			log.debug("Request to save OverHead");
			overHead = overHeadMapper.toEntity(overHeadDTO);
		} else {
			log.debug("Request to update OverHead - Id : {}", overHeadDTO.getId());

			Optional<OverHead> overHeadOptional = overHeadRepository
					.findById(overHeadDTO.getId());
			if (overHeadOptional.isPresent()) {
				overHead = overHeadOptional.get();
				overHead.overHeadName(overHeadDTO.getOverHeadName()).overHeadType(overHeadDTO.getOverHeadType())
				.activeYn(overHeadDTO.getActiveYn());
			}
		}
		overHead = overHeadRepository.save(overHead);
		return overHeadMapper.toDto(overHead);
    }

    @Override
    public Optional<OverHeadDTO> partialUpdate(OverHeadDTO overHeadDTO) {
        log.debug("Request to partially update OverHead : {}", overHeadDTO);

        return overHeadRepository
            .findById(overHeadDTO.getId())
            .map(
                existingOverHead -> {
                    overHeadMapper.partialUpdate(existingOverHead, overHeadDTO);
                    return existingOverHead;
                }
            )
            .map(overHeadRepository::save)
            .map(overHeadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OverHeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OverHeads");
        return overHeadRepository.findAll(pageable).map(overHeadMapper::toDto);
    }

	@Override
	@Transactional(readOnly = true)
	public Page<OverHeadDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all active DeptEstimateTypes");
		return overHeadRepository.findAllByActiveYn(true, pageable).map(overHeadMapper::toDto);
	}

	
    @Override
    @Transactional(readOnly = true)
    public Optional<OverHeadDTO> findOne(Long id) {
        log.debug("Request to get OverHead : {}", id);
        return overHeadRepository.findById(id).map(overHeadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OverHead : {}", id);
        Optional<OverHead> overHeadOptional = overHeadRepository.findById(id);
		if (overHeadOptional.isPresent()) {
			OverHead overHead = overHeadOptional.get();
			overHead.activeYn(false);
			overHeadRepository.save(overHead);
		}
    }
}
