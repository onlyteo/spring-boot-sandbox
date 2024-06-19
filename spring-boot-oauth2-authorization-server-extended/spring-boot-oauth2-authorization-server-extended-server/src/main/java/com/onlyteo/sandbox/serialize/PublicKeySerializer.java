package com.onlyteo.sandbox.serialize;

import org.springframework.core.serializer.Serializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class PublicKeySerializer implements Serializer<RSAPublicKey> {

    private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";

    @Override
    public void serialize(@NonNull final RSAPublicKey publicKey,
                          @NonNull final OutputStream outputStream) throws IOException {
        final var pemBytes = new X509EncodedKeySpec(publicKey.getEncoded()).getEncoded();
        final var pemBase64 = Base64.getMimeEncoder().encodeToString(pemBytes);
        final var pem = PUBLIC_KEY_PREFIX + "\n" + pemBase64 + "\n" + PUBLIC_KEY_SUFFIX;
        outputStream.write(pem.getBytes());
    }
}
