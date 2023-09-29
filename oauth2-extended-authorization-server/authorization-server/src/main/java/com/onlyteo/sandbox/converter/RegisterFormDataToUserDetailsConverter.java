package com.onlyteo.sandbox.converter;

import com.onlyteo.sandbox.model.RegisterFormData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterFormDataToUserDetailsConverter implements Converter<RegisterFormData, UserDetails> {

    private static final String DEFAULT_ROLE = "USER";
    private final PasswordEncoder passwordEncoder;

    public RegisterFormDataToUserDetailsConverter(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @NonNull
    @Override
    public UserDetails convert(@NonNull final RegisterFormData source) {
        return User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username(source.username())
                .password(source.password())
                .roles(DEFAULT_ROLE)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}
