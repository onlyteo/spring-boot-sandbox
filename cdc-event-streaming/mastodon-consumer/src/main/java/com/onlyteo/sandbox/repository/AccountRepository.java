package com.onlyteo.sandbox.repository;

import com.onlyteo.sandbox.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
}
