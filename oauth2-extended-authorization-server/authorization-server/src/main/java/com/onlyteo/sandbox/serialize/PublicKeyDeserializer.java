package com.onlyteo.sandbox.serialize;

import com.onlyteo.sandbox.exception.KeyException;
import org.springframework.core.serializer.Deserializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class PublicKeyDeserializer implements Deserializer<RSAPublicKey> {

    private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";
    private final KeyFactory rsaKeyFactory;

    public PublicKeyDeserializer(final KeyFactory rsaKeyFactory) {
        this.rsaKeyFactory = rsaKeyFactory;
    }

    @NonNull
    @Override
    public RSAPublicKey deserialize(@NonNull final InputStream inputStream) throws IOException {
        try {
            var pem = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
            final var pemBase64 = pem
                    .replace(PUBLIC_KEY_PREFIX, "")
                    .replace(PUBLIC_KEY_SUFFIX, "");
            var pemBytes = Base64.getMimeDecoder().decode(pemBase64);
            return (RSAPublicKey) rsaKeyFactory.generatePublic(new X509EncodedKeySpec(pemBytes));
        } catch (InvalidKeySpecException e) {
            throw new KeyException("Invalid key format", e);
        }
    }
}
