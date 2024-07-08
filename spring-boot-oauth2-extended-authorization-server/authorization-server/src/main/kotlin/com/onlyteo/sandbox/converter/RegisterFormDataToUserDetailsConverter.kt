package com.onlyteo.sandbox.converter

import com.onlyteo.sandbox.model.RegisterFormData
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class RegisterFormDataToUserDetailsConverter(
    private val passwordEncoder: PasswordEncoder
) : Converter<RegisterFormData, UserDetails> {

    private val defaultRole = "USER"

    override fun convert(source: RegisterFormData): UserDetails {
        return User.builder()
            .passwordEncoder { passwordEncoder.encode(it) }
            .username(source.username)
            .password(source.password)
            .roles(defaultRole)
            .disabled(false)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .build()
    }
}