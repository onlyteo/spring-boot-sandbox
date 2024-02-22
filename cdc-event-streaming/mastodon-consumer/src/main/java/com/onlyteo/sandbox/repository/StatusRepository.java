package com.onlyteo.sandbox.repository;

import com.onlyteo.sandbox.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, String> {
}
