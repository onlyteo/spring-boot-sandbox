package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.AccountDto
import com.onlyteo.sandbox.model.AccountEntity
import com.onlyteo.sandbox.repository.AccountRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Validated
@Service
class AccountService(
    private val conversionService: ConversionService,
    private val accountRepository: AccountRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun upsert(@Valid @NotNull accountDto: AccountDto) {
        try {
            logger.info("Processing account {}", accountDto.id)
            val optionalAccountEntity = accountRepository.findById(accountDto.id)
            if (optionalAccountEntity.isEmpty) {
                val nullableAccountEntity = conversionService.convert(accountDto, AccountEntity::class.java)
                val accountEntity = requireNotNull(nullableAccountEntity) { "Failed to convert AccountEntity" }
                accountRepository.save(accountEntity)
            } else {
                logger.info("Account ${accountDto.id} already exists")
            }
        } catch (e: Exception) {
            logger.error("Failed to process account ${accountDto.id}")
            throw e
        }
    }
}