package com.onlyteo.sandbox.app.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "GREETINGS")
@Entity
class GreetingEntity(
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    var id: Int? = null,
    @Column(name = "MESSAGE", nullable = false)
    val message: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "PERSON_ID")
    val person: PersonEntity
)