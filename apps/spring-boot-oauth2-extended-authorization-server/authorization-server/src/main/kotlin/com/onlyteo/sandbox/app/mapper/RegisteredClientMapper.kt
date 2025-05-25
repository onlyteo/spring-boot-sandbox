package com.onlyteo.sandbox.app.mapper

import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import java.util.*

fun OAuth2AuthorizationServerProperties.asRegisteredClients(): List<RegisteredClient> {
    return client
        .map { (registrationId, client) -> client.asRegisteredClient(registrationId) }
}

private fun OAuth2AuthorizationServerProperties.Client.asRegisteredClient(registrationId: String): RegisteredClient {
    val methods = registration.clientAuthenticationMethods.map { ClientAuthenticationMethod(it) }
    val grantTypes = registration.authorizationGrantTypes.map { AuthorizationGrantType(it) }
    return RegisteredClient.withId(registrationId)
        .clientId(registration.clientId)
        .clientSecret(registration.clientSecret)
        .clientName(registration.clientName)
        .clientAuthenticationMethods { it.addAll(methods) }
        .authorizationGrantTypes { it.addAll(grantTypes) }
        .redirectUris { it.addAll(registration.redirectUris) }
        .postLogoutRedirectUris { it.addAll(registration.postLogoutRedirectUris) }
        .scopes { it.addAll(registration.scopes) }
        .clientSettings(asClientSettings())
        .tokenSettings(asTokenSettings())
        .build()
}

private fun OAuth2AuthorizationServerProperties.Client.asClientSettings(): ClientSettings {
    val builder = ClientSettings.builder()
        .requireProofKey(isRequireProofKey)
        .requireAuthorizationConsent(isRequireAuthorizationConsent)
    jwkSetUri?.let { builder.jwkSetUrl(it) }
    tokenEndpointAuthenticationSigningAlgorithm
        ?.asJwsAlgorithm()
        ?.let { builder.tokenEndpointAuthenticationSigningAlgorithm(it) }
    return builder.build()
}

private fun OAuth2AuthorizationServerProperties.Client.asTokenSettings(): TokenSettings {
    return TokenSettings.builder()
        .authorizationCodeTimeToLive(token.authorizationCodeTimeToLive)
        .accessTokenTimeToLive(token.accessTokenTimeToLive)
        .accessTokenFormat(OAuth2TokenFormat(token.accessTokenFormat))
        .deviceCodeTimeToLive(token.deviceCodeTimeToLive)
        .reuseRefreshTokens(token.isReuseRefreshTokens)
        .refreshTokenTimeToLive(token.refreshTokenTimeToLive)
        .idTokenSignatureAlgorithm(token.idTokenSignatureAlgorithm.asSignatureAlgorithm())
        .build()
}

private fun String.asJwsAlgorithm(): JwsAlgorithm {
    val algorithm = this.uppercase(Locale.getDefault())
    return SignatureAlgorithm.from(algorithm) ?: MacAlgorithm.from(algorithm)
}

private fun String.asSignatureAlgorithm(): SignatureAlgorithm {
    val algorithm = this.uppercase(Locale.getDefault())
    return SignatureAlgorithm.from(algorithm)
}
