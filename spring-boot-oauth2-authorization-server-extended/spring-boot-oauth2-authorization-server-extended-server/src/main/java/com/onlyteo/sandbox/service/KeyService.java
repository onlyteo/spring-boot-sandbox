package com.onlyteo.sandbox.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.onlyteo.sandbox.exception.KeyException;
import com.onlyteo.sandbox.serialize.PrivateKeyDeserializer;
import com.onlyteo.sandbox.serialize.PublicKeyDeserializer;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyService {

    private final ResourceLoader resourceLoader;
    private final PublicKeyDeserializer publicKeyDeserializer;
    private final PrivateKeyDeserializer privateKeyDeserializer;

    public KeyService(final ResourceLoader resourceLoader,
                      final PublicKeyDeserializer publicKeyDeserializer,
                      final PrivateKeyDeserializer privateKeyDeserializer) {
        this.resourceLoader = resourceLoader;
        this.publicKeyDeserializer = publicKeyDeserializer;
        this.privateKeyDeserializer = privateKeyDeserializer;
    }

    public RSAKey readPublicRsaKey(final String keyId,
                                   final String publicKeyPath) {
        try {
            final var publicKeyFile = resourceLoader.getResource(publicKeyPath).getFile();
            return readPublicRsaKey(keyId, publicKeyFile);
        } catch (IOException e) {
            throw new KeyException("Invalid key path", e);
        }
    }

    public RSAKey readPublicRsaKey(final String keyId,
                                   final File publicKeyFile) {
        return new RSAKey.Builder(readPublicKey(publicKeyFile))
                .keyID(keyId)
                .keyUse(KeyUse.SIGNATURE)
                .build();
    }

    public RSAKey readPrivateRsaKey(final String keyId,
                                    final String publicKeyPath,
                                    final String privateKeyPath) {
        try {
            final var publicKeyFile = resourceLoader.getResource(publicKeyPath).getFile();
            final var privateKeyFile = resourceLoader.getResource(privateKeyPath).getFile();
            return readPrivateRsaKey(keyId, publicKeyFile, privateKeyFile);
        } catch (IOException e) {
            throw new KeyException("Invalid key path", e);
        }
    }

    public RSAKey readPrivateRsaKey(final String keyId,
                                    final File publicKeyFile,
                                    final File privateKeyFile) {
        return new RSAKey.Builder(readPublicKey(publicKeyFile))
                .privateKey(readPrivateKey(privateKeyFile))
                .keyID(keyId)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .build();
    }

    private RSAPublicKey readPublicKey(final File publicKeyFile) {
        try (final FileInputStream inputStream = new FileInputStream(publicKeyFile)) {
            return publicKeyDeserializer.deserialize(inputStream);
        } catch (IOException e) {
            throw new KeyException("Could not read key", e);
        }
    }

    private RSAPrivateKey readPrivateKey(final File privateKeyFile) {
        try (final FileInputStream inputStream = new FileInputStream(privateKeyFile)) {
            return privateKeyDeserializer.deserialize(inputStream);
        } catch (IOException e) {
            throw new KeyException("Could not read key", e);
        }
    }
}
