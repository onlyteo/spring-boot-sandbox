package com.onlyteo.sandbox.model

import jakarta.persistence.*

@Table(name = "PERSONS")
@Entity
class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false)
    var name: String? = null
)
