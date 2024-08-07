package com.onlyteo.sandbox.service

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSelector
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.onlyteo.sandbox.properties.ApplicationProperties

class JwkService(
    private val properties: ApplicationProperties,
    private val keyService: KeyService
) : JWKSource<SecurityContext> {

    override fun get(selector: JWKSelector, context: SecurityContext): MutableList<JWK> {
        val jwkList = ArrayList<JWK>()
        properties.security.keys.stream()
            .map { key ->
                keyService.readPrivateRsaKey(
                    key.keyId,
                    key.publicKeyLocation,
                    key.privateKeyLocation
                )
            }
            .filter { key ->
                selector.matcher.matches(key)
            }
            .forEach { key -> jwkList.add(key) }
        return jwkList
    }
}