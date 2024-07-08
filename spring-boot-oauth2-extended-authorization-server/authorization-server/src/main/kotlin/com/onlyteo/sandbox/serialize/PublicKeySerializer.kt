package com.onlyteo.sandbox.serialize

import org.springframework.core.serializer.Serializer
import org.springframework.stereotype.Component
import java.io.OutputStream
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class PublicKeySerializer : Serializer<RSAPublicKey> {

    private val publicKeyPrefix = "-----BEGIN PUBLIC KEY-----"
    private val publicKeySuffix = "-----END PUBLIC KEY-----"

    override fun serialize(publicKey: RSAPublicKey, outputStream: OutputStream) {
        val pemBytes = X509EncodedKeySpec(publicKey.encoded).encoded
        val pemBase64 = Base64.getMimeEncoder().encodeToString(pemBytes)
        val pem = "${publicKeyPrefix}\n${pemBase64}\n${publicKeySuffix}"
        outputStream.write(pem.toByteArray())
    }
}