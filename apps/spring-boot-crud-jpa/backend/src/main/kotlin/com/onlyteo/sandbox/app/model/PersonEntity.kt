package com.onlyteo.sandbox.app.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table

@Table(name = "PERSONS")
@Entity
class PersonEntity(
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    var id: Int? = null,
    @Column(name = "NAME", nullable = false)
    val name: String,
    @OrderBy("id ASC")
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var greetings: MutableList<GreetingEntity> = mutableListOf()
)