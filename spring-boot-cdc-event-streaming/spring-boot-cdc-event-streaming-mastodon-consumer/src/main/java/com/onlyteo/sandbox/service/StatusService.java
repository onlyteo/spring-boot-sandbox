package com.onlyteo.sandbox.service;

import com.onlyteo.sandbox.model.StatusDto;
import com.onlyteo.sandbox.model.StatusEntity;
import com.onlyteo.sandbox.repository.AccountRepository;
import com.onlyteo.sandbox.repository.StatusRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class StatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

    private final ConversionService conversionService;
    private final AccountRepository accountRepository;
    private final StatusRepository statusRepository;

    public StatusService(final ConversionService conversionService,
                         final AccountRepository accountRepository,
                         final StatusRepository statusRepository) {
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void upsert(@Valid @NotNull final StatusDto statusDto) {
        try {
            LOGGER.info("Processing status {} from account {}", statusDto.id(), statusDto.account().id());
            final var optionalStatusDto = statusRepository.findById(statusDto.id());
            if (optionalStatusDto.isEmpty()) {
                final var statusEntity = conversionService.convert(statusDto, StatusEntity.class);
                Assert.notNull(statusEntity, "Failed to convert StatusEntity");
                final var optionalAccountEntity = accountRepository.findById(statusDto.account().id());
                if (optionalAccountEntity.isPresent()) {
                    statusEntity.setAccount(optionalAccountEntity.get());
                    statusRepository.save(statusEntity);
                } else {
                    LOGGER.error("Account {} does not exist", statusDto.account().id());
                }
            } else {
                LOGGER.warn("Status {} already exists", statusDto.id());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to process status {} from account {}", statusDto.id(), statusDto.account().id());
            throw e;
        }
    }
}
