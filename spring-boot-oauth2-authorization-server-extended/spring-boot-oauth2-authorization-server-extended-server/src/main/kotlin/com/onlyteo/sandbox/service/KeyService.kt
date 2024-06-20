package com.onlyteo.sandbox.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.onlyteo.sandbox.exception.KeyException
import com.onlyteo.sandbox.serialize.PrivateKeyDeserializer
import com.onlyteo.sandbox.serialize.PublicKeyDeserializer
import org.springframework.core.io.ResourceLoader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

class KeyService(
    private val resourceLoader: ResourceLoader,
    private val publicKeyDeserializer: PublicKeyDeserializer,
    private val privateKeyDeserializer: PrivateKeyDeserializer
) {
    fun readPublicRsaKey(
        keyId: String,
        publicKeyPath: String
    ): RSAKey {
        try {
            val publicKeyFile = resourceLoader.getResource(publicKeyPath).file
            return readPublicRsaKey(keyId, publicKeyFile)
        } catch (e: IOException) {
            throw KeyException("Invalid key path", e)
        }
    }

    fun readPublicRsaKey(
        keyId: String,
        publicKeyFile: File
    ): RSAKey {
        return RSAKey.Builder(readPublicKey(publicKeyFile))
            .keyID(keyId)
            .keyUse(KeyUse.SIGNATURE)
            .build()
    }

    fun readPrivateRsaKey(
        keyId: String,
        publicKeyPath: String,
        privateKeyPath: String
    ): RSAKey {
        try {
            val publicKeyFile = resourceLoader.getResource(publicKeyPath).file
            val privateKeyFile = resourceLoader.getResource(privateKeyPath).file
            return readPrivateRsaKey(keyId, publicKeyFile, privateKeyFile)
        } catch (e: IOException) {
            throw KeyException("Invalid key path", e)
        }
    }

    fun readPrivateRsaKey(
        keyId: String,
        publicKeyFile: File,
        privateKeyFile: File
    ): RSAKey {
        return RSAKey.Builder(readPublicKey(publicKeyFile))
            .privateKey(readPrivateKey(privateKeyFile))
            .keyID(keyId)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .build()
    }

    private fun readPublicKey(publicKeyFile: File): RSAPublicKey {
        try {
            FileInputStream(publicKeyFile).use { inputStream ->
                return publicKeyDeserializer.deserialize(inputStream)
            }
        } catch (e: IOException) {
            throw KeyException("Could not read key", e)
        }
    }

    private fun readPrivateKey(privateKeyFile: File): RSAPrivateKey {
        try {
            FileInputStream(privateKeyFile).use { inputStream ->
                return privateKeyDeserializer.deserialize(inputStream)
            }
        } catch (e: IOException) {
            throw KeyException("Could not read key", e)
        }
    }
}