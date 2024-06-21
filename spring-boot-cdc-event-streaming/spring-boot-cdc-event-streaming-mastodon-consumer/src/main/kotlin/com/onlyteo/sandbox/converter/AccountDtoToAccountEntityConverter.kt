package com.onlyteo.sandbox.converter

import com.onlyteo.sandbox.model.AccountDto
import com.onlyteo.sandbox.model.AccountEntity
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component

@Component
class AccountDtoToAccountEntityConverter : Converter<AccountDto, AccountEntity> {

    @NonNull
    override fun convert(@NonNull source: AccountDto): AccountEntity {
        return AccountEntity(
            source.id,
            source.url.toString(),
            source.avatar.toString(),
            source.username,
            source.account,
            source.displayName,
            source.followersCount,
            source.followingCount,
            source.statusesCount,
            source.createdAt
        )
    }
}