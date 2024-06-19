package com.onlyteo.sandbox.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Table(name = "ACCOUNT")
@Entity
public class AccountEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "avatar", nullable = false)
    private String avatar;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "account", nullable = false, unique = true)
    private String account;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(name = "followers_count", nullable = false)
    private Integer followersCount;
    @Column(name = "following_count", nullable = false)
    private Integer followingCount;
    @Column(name = "statuses_count", nullable = false)
    private Integer statusesCount;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @OneToOne(mappedBy = "account")
    private StatusEntity status;

    public AccountEntity() {
    }

    public AccountEntity(String id,
                         String url,
                         String avatar,
                         String username,
                         String account,
                         String displayName,
                         Integer followersCount,
                         Integer followingCount,
                         Integer statusesCount,
                         ZonedDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.avatar = avatar;
        this.username = username;
        this.account = account;
        this.displayName = displayName;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.statusesCount = statusesCount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public StatusEntity getStatus() {
        return status;
    }
}
