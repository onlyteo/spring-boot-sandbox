package com.onlyteo.sandbox.handler

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.util.FileCopyUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

object KeyHandler {

    private const val PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----"
    private const val PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----"
    private const val PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----"
    private const val PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----"

    fun readRsaKey(
        keyId: String,
        publicKeyFile: File,
        privateKeyFile: File
    ): RSAKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.readPublicKey(publicKeyFile)
        val privateKey = keyFactory.readPrivateKey(privateKeyFile)
        return RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(keyId)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .build()
    }

    private fun KeyFactory.readPublicKey(publicKeyFile: File): RSAPublicKey {
        return FileInputStream(publicKeyFile).use {
            val pem = FileCopyUtils.copyToString(InputStreamReader(it))
            val pemBase64 = pem
                .replace(PUBLIC_KEY_PREFIX, "")
                .replace(PUBLIC_KEY_SUFFIX, "")
            val pemBytes = Base64.getMimeDecoder().decode(pemBase64)
            (generatePublic(X509EncodedKeySpec(pemBytes)) as RSAPublicKey)
        }
    }

    private fun KeyFactory.readPrivateKey(privateKeyFile: File): RSAPrivateKey {
        return FileInputStream(privateKeyFile).use {
            val pem = FileCopyUtils.copyToString(InputStreamReader(it))
            val pemBase64 = pem
                .replace(PRIVATE_KEY_PREFIX, "")
                .replace(PRIVATE_KEY_SUFFIX, "")
            val pemBytes = Base64.getMimeDecoder().decode(pemBase64)
            (generatePrivate(PKCS8EncodedKeySpec(pemBytes)) as RSAPrivateKey)
        }
    }
}