package com.onlyteo.sandbox.serialize;

import org.springframework.core.serializer.Serializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class PrivateKeySerializer implements Serializer<RSAPrivateKey> {

    private static final String PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----";

    @Override
    public void serialize(@NonNull final RSAPrivateKey privateKey,
                          @NonNull final OutputStream outputStream) throws IOException {
        final var pemBytes = new PKCS8EncodedKeySpec(privateKey.getEncoded()).getEncoded();
        final var pemBase64 = Base64.getMimeEncoder().encodeToString(pemBytes);
        final var pem = PRIVATE_KEY_PREFIX + "\n" + pemBase64 + "\n" + PRIVATE_KEY_SUFFIX;
        outputStream.write(pem.getBytes());
    }
}
