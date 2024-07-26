package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<PersonEntity, Int> {
    fun findByName(name: String): PersonEntity?
}