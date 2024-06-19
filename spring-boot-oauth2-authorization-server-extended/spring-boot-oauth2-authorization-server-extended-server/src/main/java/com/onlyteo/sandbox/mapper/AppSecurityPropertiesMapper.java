package com.onlyteo.sandbox.mapper;

import com.onlyteo.sandbox.properties.AppSecurityProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class AppSecurityPropertiesMapper {

    private final PasswordEncoder passwordEncoder;
    private final AppSecurityProperties properties;

    public AppSecurityPropertiesMapper(final PasswordEncoder passwordEncoder,
                                       final AppSecurityProperties properties) {
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    public List<UserDetails> asUserDetails() {
        return properties.getUsers().stream()
                .map(this::asUserDetail)
                .toList();
    }

    private UserDetails asUserDetail(final AppSecurityProperties.AppUserProperties user) {
        final var map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        final var builder = User.builder().passwordEncoder(passwordEncoder::encode);
        map.from(user.getUsername()).to(builder::username);
        map.from(user.getPassword()).to(builder::password);
        map.from(user.getRoles().toArray(new String[]{})).to(builder::roles);
        map.from(false).to(builder::disabled);
        map.from(false).to(builder::accountExpired);
        map.from(false).to(builder::accountLocked);
        map.from(false).to(builder::credentialsExpired);
        return builder.build();
    }
}
