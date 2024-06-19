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
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class PrivateKeyDeserializer implements Deserializer<RSAPrivateKey> {

    private static final String PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----";
    private final KeyFactory rsaKeyFactory;

    public PrivateKeyDeserializer(final KeyFactory rsaKeyFactory) {
        this.rsaKeyFactory = rsaKeyFactory;
    }

    @NonNull
    @Override
    public RSAPrivateKey deserialize(@NonNull final InputStream inputStream) throws IOException {
        try {
            final var pem = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
            var pemBase64 = pem
                    .replace(PRIVATE_KEY_PREFIX, "")
                    .replace(PRIVATE_KEY_SUFFIX, "");
            var pemBytes = Base64.getMimeDecoder().decode(pemBase64);
            return (RSAPrivateKey) rsaKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(pemBytes));
        } catch (InvalidKeySpecException e) {
            throw new KeyException("Invalid key format", e);
        }
    }
}
