package com.onlyteo.sandbox.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Table(name = "STATUS")
@Entity
class StatusEntity(
    @Id @Column(name = "id", nullable = false, unique = true)
    var id: String? = null,
    @Column(name = "url", nullable = false)
    val url: String? = null,
    @Column(name = "language", nullable = false)
    var language: String? = null,
    @Column(name = "visibility", nullable = false)
    var visibility: String? = null,
    @Column(name = "content", nullable = false)
    var content: String? = null,
    @Column(name = "in_reply_to_id", nullable = true)
    var inReplyToStatusId: String? = null,
    @Column(name = "in_reply_to_account_id", nullable = true)
    var inReplyToAccountId: String? = null,
    @Column(name = "replies_count", nullable = false)
    var repliesCount: Int? = null,
    @Column(name = "reblogs_count", nullable = false)
    var reblogsCount: Int? = null,
    @Column(name = "favourites_count", nullable = false)
    var favouritesCount: Int? = null,
    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: AccountEntity? = null
)