package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<PersonEntity, Int> {
    fun findByName(name: String): PersonEntity?
}
