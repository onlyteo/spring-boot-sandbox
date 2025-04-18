package com.onlyteo.sandbox.model

import jakarta.persistence.*

@Table(name = "PERSONS")
@Entity
class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false)
    val name: String? = null,
    @Column(nullable = false)
    var count: Int? = null
) {
    fun increaseCount() {
        count = if (count == null) {
            1
        } else {
            count!! + 1
        }
    }
}
