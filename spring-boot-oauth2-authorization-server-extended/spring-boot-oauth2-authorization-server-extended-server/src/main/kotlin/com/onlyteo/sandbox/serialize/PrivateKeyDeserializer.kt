package com.onlyteo.sandbox.serialize

import com.onlyteo.sandbox.exception.KeyException
import org.springframework.core.serializer.Deserializer
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import java.io.InputStream
import java.io.InputStreamReader
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Component
class PrivateKeyDeserializer(private val keyFactory: KeyFactory) : Deserializer<RSAPrivateKey> {

    private val privateKeyPrefix = "-----BEGIN PRIVATE KEY-----"
    private val privateKeySuffix = "-----END PRIVATE KEY-----"

    override fun deserialize(inputStream: InputStream): RSAPrivateKey {
        try {
            val pem = FileCopyUtils.copyToString(InputStreamReader(inputStream))
            val pemBase64 = pem
                .replace(privateKeyPrefix, "")
                .replace(privateKeySuffix, "")
            val pemBytes = Base64.getMimeDecoder().decode(pemBase64)
            return (keyFactory.generatePrivate(PKCS8EncodedKeySpec(pemBytes)) as RSAPrivateKey)
        } catch (e: InvalidKeySpecException) {
            throw KeyException("Invalid key format", e)
        }
    }
}