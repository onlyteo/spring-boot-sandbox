package com.onlyteo.sandbox.service;

import com.onlyteo.sandbox.model.AccountDto;
import com.onlyteo.sandbox.model.AccountEntity;
import com.onlyteo.sandbox.repository.AccountRepository;
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
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final ConversionService conversionService;
    private final AccountRepository accountRepository;

    public AccountService(final ConversionService conversionService,
                          final AccountRepository accountRepository) {
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void upsert(@Valid @NotNull final AccountDto accountDto) {
        try {
            LOGGER.info("Processing account {}", accountDto.id());
            final var optionalAccountEntity = accountRepository.findById(accountDto.id());
            if (optionalAccountEntity.isEmpty()) {
                final var accountEntity = conversionService.convert(accountDto, AccountEntity.class);
                Assert.notNull(accountEntity, "Failed to convert AccountEntity");
                accountRepository.save(accountEntity);
            } else {
                LOGGER.info("Account ${accountDto.id} already exists");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to process account ${accountDto.id}");
            throw e;
        }
    }
}
