package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<PersonEntity, Int> {
    fun findByName(name: String): PersonEntity?
}