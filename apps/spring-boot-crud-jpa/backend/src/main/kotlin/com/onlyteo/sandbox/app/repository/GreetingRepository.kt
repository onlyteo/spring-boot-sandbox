package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.GreetingEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GreetingRepository : JpaRepository<GreetingEntity, Int> {

    @Suppress("FunctionName")
    fun findByPerson_Name(name: String): List<GreetingEntity>
}