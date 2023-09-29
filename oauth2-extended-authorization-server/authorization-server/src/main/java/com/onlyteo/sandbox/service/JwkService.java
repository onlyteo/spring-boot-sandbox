package com.onlyteo.sandbox.service;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.onlyteo.sandbox.properties.AppSecurityProperties;

import java.util.ArrayList;
import java.util.List;

public class JwkService implements JWKSource<SecurityContext> {

    private final AppSecurityProperties securityProperties;
    private final KeyService keyService;

    public JwkService(final AppSecurityProperties securityProperties,
                      final KeyService keyService) {
        this.securityProperties = securityProperties;
        this.keyService = keyService;
    }

    @Override
    public List<JWK> get(final JWKSelector jwkSelector, final SecurityContext context) {
        var jwkList = new ArrayList<JWK>();
        securityProperties.getKeys().stream()
                .map(key -> keyService.readPrivateRsaKey(key.getKeyId(), key.getPublicKeyLocation(), key.getPrivateKeyLocation()))
                .filter(jwkSelector.getMatcher()::matches)
                .forEach(jwkList::add);
        return jwkList;
    }
}
