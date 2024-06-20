package com.onlyteo.sandbox.serialize

import org.springframework.core.serializer.Serializer
import org.springframework.stereotype.Component
import java.io.OutputStream
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Component
class PrivateKeySerializer : Serializer<RSAPrivateKey> {

    private val privateKeyPrefix = "-----BEGIN PRIVATE KEY-----"
    private val privateKeySuffix = "-----END PRIVATE KEY-----"

    override fun serialize(privateKey: RSAPrivateKey, outputStream: OutputStream) {
        val pemBytes = PKCS8EncodedKeySpec(privateKey.encoded).encoded
        val pemBase64 = Base64.getMimeEncoder().encodeToString(pemBytes)
        val pem = "${privateKeyPrefix}\n${pemBase64}\n${privateKeySuffix}"
        outputStream.write(pem.toByteArray())
    }
}