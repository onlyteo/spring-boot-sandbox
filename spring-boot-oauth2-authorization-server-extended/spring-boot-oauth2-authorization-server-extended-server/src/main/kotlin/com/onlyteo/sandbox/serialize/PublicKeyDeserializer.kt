package com.onlyteo.sandbox.serialize

import com.onlyteo.sandbox.exception.KeyException
import org.springframework.core.serializer.Deserializer
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import java.io.InputStream
import java.io.InputStreamReader
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class PublicKeyDeserializer(private val keyFactory: KeyFactory) : Deserializer<RSAPublicKey> {

    private val publicKeyPrefix = "-----BEGIN PUBLIC KEY-----"
    private val publicKeySuffix = "-----END PUBLIC KEY-----"

    override fun deserialize(inputStream: InputStream): RSAPublicKey {
        try {
            val pem = FileCopyUtils.copyToString(InputStreamReader(inputStream))
            val pemBase64 = pem
                .replace(publicKeyPrefix, "")
                .replace(publicKeySuffix, "")
            val pemBytes = Base64.getMimeDecoder().decode(pemBase64)
            return (keyFactory.generatePublic(X509EncodedKeySpec(pemBytes)) as RSAPublicKey)
        } catch (e: InvalidKeySpecException) {
            throw KeyException("Invalid key format", e)
        }
    }
}