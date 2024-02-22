package com.onlyteo.sandbox.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Table(name = "STATUS")
@Entity
public class StatusEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "language", nullable = false)
    private String language;
    @Column(name = "visibility", nullable = false)
    private String visibility;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "in_reply_to_id", nullable = true)
    private String inReplyToStatusId;
    @Column(name = "in_reply_to_account_id", nullable = true)
    private String inReplyToAccountId;
    @Column(name = "replies_count", nullable = false)
    private Integer repliesCount;
    @Column(name = "reblogs_count", nullable = false)
    private Integer reblogsCount;
    @Column(name = "favourites_count", nullable = false)
    private Integer favouritesCount;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    public StatusEntity() {
    }

    public StatusEntity(String id,
                        String url,
                        String language,
                        String visibility,
                        String content,
                        String inReplyToStatusId,
                        String inReplyToAccountId,
                        Integer repliesCount,
                        Integer reblogsCount,
                        Integer favouritesCount,
                        ZonedDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.language = language;
        this.visibility = visibility;
        this.content = content;
        this.inReplyToStatusId = inReplyToStatusId;
        this.inReplyToAccountId = inReplyToAccountId;
        this.repliesCount = repliesCount;
        this.reblogsCount = reblogsCount;
        this.favouritesCount = favouritesCount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getLanguage() {
        return language;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getContent() {
        return content;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public String getInReplyToAccountId() {
        return inReplyToAccountId;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public Integer getReblogsCount() {
        return reblogsCount;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
