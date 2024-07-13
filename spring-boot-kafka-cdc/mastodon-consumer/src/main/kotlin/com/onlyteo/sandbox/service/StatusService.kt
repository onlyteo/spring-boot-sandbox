package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.StatusDto
import com.onlyteo.sandbox.model.StatusEntity
import com.onlyteo.sandbox.repository.AccountRepository
import com.onlyteo.sandbox.repository.StatusRepository
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
class StatusService(
    private val conversionService: ConversionService,
    private val accountRepository: AccountRepository,
    private val statusRepository: StatusRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(StatusService::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun upsert(@Valid @NotNull statusDto: StatusDto) {
        try {
            logger.info("Processing status {} from account {}", statusDto.id, statusDto.account.id)
            val optionalStatusDto = statusRepository.findById(statusDto.id)
            if (optionalStatusDto.isEmpty) {
                val nullableStatusEntity = conversionService.convert(statusDto, StatusEntity::class.java)
                val statusEntity = requireNotNull(nullableStatusEntity) { "Failed to convert StatusEntity" }
                val optionalAccountEntity = accountRepository.findById(statusDto.account.id)
                if (optionalAccountEntity.isPresent) {
                    statusEntity.account = optionalAccountEntity.get()
                    statusRepository.save(statusEntity)
                } else {
                    logger.error("Account {} does not exist", statusDto.account.id)
                }
            } else {
                logger.warn("Status {} already exists", statusDto.id)
            }
        } catch (e: Exception) {
            logger.error(
                "Failed to process status {} from account {}",
                statusDto.id,
                statusDto.account.id
            )
            throw e
        }
    }
}