package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.StatusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository : JpaRepository<StatusEntity, String>