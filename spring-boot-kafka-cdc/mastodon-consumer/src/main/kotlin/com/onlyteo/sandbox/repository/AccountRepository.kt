package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, String>