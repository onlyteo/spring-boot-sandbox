package com.onlyteo.sandbox.mapper

import com.onlyteo.sandbox.properties.AppSecurityProperties
import com.onlyteo.sandbox.properties.AppUserProperties
import org.springframework.boot.context.properties.PropertyMapper
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder

class AppSecurityPropertiesMapper(
    private val passwordEncoder: PasswordEncoder,
    private val properties: AppSecurityProperties
) {
    fun asUserDetails(): List<UserDetails> {
        return properties.users.stream()
            .map { asUserDetail(it) }
            .toList()
    }

    private fun asUserDetail(user: AppUserProperties): UserDetails {
        val map = PropertyMapper.get().alwaysApplyingWhenNonNull()
        val builder = User.builder().passwordEncoder { passwordEncoder.encode(it) }
        map.from(user.username).to { builder.username(it) }
        map.from(user.password).to { builder.password(it) }
        map.from(user.roles.toTypedArray()) to builder::roles
        map.from(false).to { builder.disabled(it) }
        map.from(false).to { builder.accountExpired(it) }
        map.from(false).to { builder.accountLocked(it) }
        map.from(false).to { builder.credentialsExpired(it) }
        return builder.build()
    }
}