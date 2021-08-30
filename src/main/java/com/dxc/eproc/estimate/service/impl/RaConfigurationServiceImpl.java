package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.RaConfiguration;
import com.dxc.eproc.estimate.repository.RaConfigurationRepository;
import com.dxc.eproc.estimate.service.RaConfigurationService;
import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
import com.dxc.eproc.estimate.service.mapper.RaConfigurationMapper;

/**
 * Service Implementation for managing {@link RaConfiguration}.
 */
@Service
@Transactional
public class RaConfigurationServiceImpl implements RaConfigurationService {

    private final Logger log = LoggerFactory.getLogger(RaConfigurationServiceImpl.class);

    @Autowired
    private RaConfigurationRepository raConfigurationRepository;

    @Autowired
    private RaConfigurationMapper raConfigurationMapper;

    @Override
    public RaConfigurationDTO save(RaConfigurationDTO raConfigurationDTO) {
        log.debug("Request to save RaConfiguration : {}", raConfigurationDTO);
        RaConfiguration raConfiguration = raConfigurationMapper.toEntity(raConfigurationDTO);
        if (raConfiguration.getId() != null) {
        	RaConfiguration dbData = raConfigurationRepository
					.findById(raConfiguration.getId()).get();
			BeanUtils.copyProperties(raConfiguration, dbData);
			raConfiguration = raConfigurationRepository.save(dbData);
		} else {
			raConfiguration = raConfigurationRepository.save(raConfiguration);
		}
        raConfiguration = raConfigurationRepository.save(raConfiguration);
        return raConfigurationMapper.toDto(raConfiguration);
    }

    @Override
    public Optional<RaConfigurationDTO> partialUpdate(RaConfigurationDTO raConfigurationDTO) {
        log.debug("Request to partially update RaConfiguration : {}", raConfigurationDTO);

        return raConfigurationRepository
            .findById(raConfigurationDTO.getId())
            .map(
                existingRaConfiguration -> {
                    raConfigurationMapper.partialUpdate(existingRaConfiguration, raConfigurationDTO);
                    return existingRaConfiguration;
                }
            )
            .map(raConfigurationRepository::save)
            .map(raConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RaConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RaConfigurations");
        return raConfigurationRepository.findAll(pageable).map(raConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RaConfigurationDTO> findAll() {
        log.debug("Request to get all RaConfigurations");
        return raConfigurationMapper.toDto(raConfigurationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RaConfigurationDTO> findOne(Long id) {
        log.debug("Request to get RaConfiguration : {}", id);
        return raConfigurationRepository.findById(id).map(raConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RaConfiguration : {}", id);
        raConfigurationRepository.deleteById(id);
    }

	@Override
	public List<RaConfigurationDTO> findAllByDeptId(Long deptId) {
		log.debug("Request to get List of RaConfiguration by deptId : {}", deptId);
		return raConfigurationMapper.toDto(raConfigurationRepository.findAllByDeptId(deptId));
	}
}
