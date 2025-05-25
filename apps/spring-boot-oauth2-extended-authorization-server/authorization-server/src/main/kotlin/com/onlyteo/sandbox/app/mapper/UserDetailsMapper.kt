package com.onlyteo.sandbox.app.mapper

import com.onlyteo.sandbox.app.model.RegisterFormData
import com.onlyteo.sandbox.app.properties.SecurityProperties
import com.onlyteo.sandbox.app.properties.UserProperties
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

private const val DEFAULT_ROLE = "USER"

fun SecurityProperties.asUserDetailsList(encodePassword: (String) -> String): List<UserDetails> {
    return users.map { it.asUserDetails(encodePassword) }
}

private fun UserProperties.asUserDetails(encodePassword: (String) -> String): UserDetails {
    return User.builder().passwordEncoder(encodePassword)
        .username(username)
        .password(password)
        .roles(*roles.toTypedArray())
        .disabled(false)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .build()
}

fun RegisterFormData.asUserDetails(encodePassword: (String) -> String): UserDetails {
    return User.builder().passwordEncoder(encodePassword)
        .username(username)
        .password(password)
        .roles(DEFAULT_ROLE)
        .disabled(false)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .build()
}
