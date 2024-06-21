package com.onlyteo.sandbox.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Table(name = "ACCOUNT")
@Entity
class AccountEntity(
    @Id @Column(name = "id", nullable = false, unique = true)
    var id: String? = null,
    @Column(name = "url", nullable = false)
    val url: String? = null,
    @Column(name = "avatar", nullable = false)
    var avatar: String? = null,
    @Column(name = "username", nullable = false)
    var username: String? = null,
    @Column(name = "account", nullable = false, unique = true)
    var account: String? = null,
    @Column(name = "display_name", nullable = false)
    var displayName: String? = null,
    @Column(name = "followers_count", nullable = false)
    var followersCount: Int? = null,
    @Column(name = "following_count", nullable = false)
    var followingCount: Int? = null,
    @Column(name = "statuses_count", nullable = false)
    var statusesCount: Int? = null,
    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime? = null,
    @OneToOne(mappedBy = "account")
    val status: StatusEntity? = null
)
