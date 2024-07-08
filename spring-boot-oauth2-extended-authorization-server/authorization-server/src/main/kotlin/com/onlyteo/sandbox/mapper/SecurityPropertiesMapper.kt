package com.onlyteo.sandbox.mapper

import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.UserProperties
import org.springframework.boot.context.properties.PropertyMapper
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder

class SecurityPropertiesMapper(
    private val passwordEncoder: PasswordEncoder,
    private val properties: ApplicationProperties
) {
    fun asUserDetails(): List<UserDetails> {
        return properties.security.users.stream()
            .map { asUserDetail(it) }
            .toList()
    }

    private fun asUserDetail(user: UserProperties): UserDetails {
        val map = PropertyMapper.get().alwaysApplyingWhenNonNull()
        val builder = User.builder().passwordEncoder { passwordEncoder.encode(it) }
        map.from(user.username).to { builder.username(it) }
        map.from(user.password).to { builder.password(it) }
        map.from(user.roles.toTypedArray()).to { builder.roles(*it) }
        map.from(false).to { builder.disabled(it) }
        map.from(false).to { builder.accountExpired(it) }
        map.from(false).to { builder.accountLocked(it) }
        map.from(false).to { builder.credentialsExpired(it) }
        return builder.build()
    }
}