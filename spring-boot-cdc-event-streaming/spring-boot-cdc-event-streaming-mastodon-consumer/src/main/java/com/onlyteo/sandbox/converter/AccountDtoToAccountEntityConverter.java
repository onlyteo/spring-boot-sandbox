package com.onlyteo.sandbox.converter;

import com.onlyteo.sandbox.model.AccountDto;
import com.onlyteo.sandbox.model.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoToAccountEntityConverter implements Converter<AccountDto, AccountEntity> {

    @NonNull
    @Override
    public AccountEntity convert(@NonNull final AccountDto source) {
        return new AccountEntity(
                source.id(),
                source.url().toString(),
                source.avatar().toString(),
                source.username(),
                source.account(),
                source.displayName(),
                source.followersCount(),
                source.followingCount(),
                source.statusesCount(),
                source.createdAt()
        );
    }
}
