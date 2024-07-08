package com.onlyteo.sandbox.mapper

import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties
import org.springframework.boot.context.properties.PropertyMapper
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import java.util.*
import java.util.function.Consumer

class OAuth2AuthorizationServerPropertiesMapper(
    private val properties: OAuth2AuthorizationServerProperties
) {

    fun asAuthorizationServerSettings(): AuthorizationServerSettings {
        val map = PropertyMapper.get().alwaysApplyingWhenNonNull()
        val endpoint = properties.endpoint
        val builder = AuthorizationServerSettings.builder()
        map.from { properties.issuer }.to { builder.issuer(it) }
        map.from { endpoint.authorizationUri }.to { builder.authorizationEndpoint(it) }
        map.from { endpoint.deviceAuthorizationUri }.to { builder.deviceAuthorizationEndpoint(it) }
        map.from { endpoint.deviceVerificationUri }.to { builder.deviceVerificationEndpoint(it) }
        map.from { endpoint.tokenUri }.to { builder.tokenEndpoint(it) }
        map.from { endpoint.jwkSetUri }.to { builder.jwkSetEndpoint(it) }
        map.from { endpoint.tokenRevocationUri }.to { builder.tokenRevocationEndpoint(it) }
        map.from { endpoint.tokenIntrospectionUri }.to { builder.tokenIntrospectionEndpoint(it) }
        map.from { endpoint.oidc.logoutUri }.to { builder.oidcLogoutEndpoint(it) }
        map.from { endpoint.oidc.clientRegistrationUri }.to { builder.oidcClientRegistrationEndpoint(it) }
        map.from { endpoint.oidc.userInfoUri }.to { builder.oidcUserInfoEndpoint(it) }
        return builder.build()
    }

    fun asRegisteredClients(): List<RegisteredClient> {
        return properties.client
            .map { (registrationId, client) -> getRegisteredClient(registrationId, client) }
    }

    private fun getRegisteredClient(
        registrationId: String,
        client: OAuth2AuthorizationServerProperties.Client
    ): RegisteredClient {
        val registration = client.registration
        val map = PropertyMapper.get().alwaysApplyingWhenNonNull()
        val builder = RegisteredClient.withId(registrationId)
        map.from { registration.clientId }.to { builder.clientId(it) }
        map.from { registration.clientSecret }.to { builder.clientSecret(it) }
        map.from { registration.clientName }.to { builder.clientName(it) }
        registration.clientAuthenticationMethods.forEach { method ->
            map.from(method)
                .`as` { ClientAuthenticationMethod(it) }
                .to { builder.clientAuthenticationMethod(it) }
        }
        registration.authorizationGrantTypes.forEach { grantType ->
            map.from(grantType)
                .`as` { AuthorizationGrantType(it) }
                .to { builder.authorizationGrantType(it) }
        }
        registration.redirectUris.forEach { redirectUri ->
            map.from(redirectUri).to { builder.redirectUri(it) }
        }
        registration.postLogoutRedirectUris
            .forEach { redirectUri: String ->
                map.from(redirectUri).to { builder.postLogoutRedirectUri(it) }
            }
        registration.scopes.forEach(Consumer { scope -> map.from(scope).to { builder.scope(it) } })
        builder.clientSettings(getClientSettings(client, map))
        builder.tokenSettings(getTokenSettings(client, map))
        return builder.build()
    }

    private fun getClientSettings(
        client: OAuth2AuthorizationServerProperties.Client,
        map: PropertyMapper
    ): ClientSettings {
        val builder = ClientSettings.builder()
        map.from { client.isRequireProofKey }.to { builder.requireProofKey(it) }
        map.from { client.isRequireAuthorizationConsent }.to { builder.requireAuthorizationConsent(it) }
        map.from { client.jwkSetUri }.to { builder.jwkSetUrl(it) }
        map.from { client.tokenEndpointAuthenticationSigningAlgorithm }
            .`as` { jwsAlgorithm(it) }
            .to { builder.tokenEndpointAuthenticationSigningAlgorithm(it) }
        return builder.build()
    }

    private fun getTokenSettings(
        client: OAuth2AuthorizationServerProperties.Client,
        map: PropertyMapper
    ): TokenSettings {
        val token = client.token
        val builder = TokenSettings.builder()
        map.from { token.authorizationCodeTimeToLive }.to { builder.authorizationCodeTimeToLive(it) }
        map.from { token.accessTokenTimeToLive }.to { builder.accessTokenTimeToLive(it) }
        map.from { token.accessTokenFormat }.`as` { OAuth2TokenFormat(it) }.to { builder.accessTokenFormat(it) }
        map.from { token.deviceCodeTimeToLive }.to { builder.deviceCodeTimeToLive(it) }
        map.from { token.isReuseRefreshTokens }.to { builder.reuseRefreshTokens(it) }
        map.from { token.refreshTokenTimeToLive }.to { builder.refreshTokenTimeToLive(it) }
        map.from { token.idTokenSignatureAlgorithm }
            .`as` { signatureAlgorithm(it) }
            .to { builder.idTokenSignatureAlgorithm(it) }
        return builder.build()
    }

    private fun jwsAlgorithm(signingAlgorithm: String): JwsAlgorithm? {
        val name = signingAlgorithm.uppercase(Locale.getDefault())
        return SignatureAlgorithm.from(name) ?: MacAlgorithm.from(name)
    }

    private fun signatureAlgorithm(signatureAlgorithm: String): SignatureAlgorithm {
        return SignatureAlgorithm.from(signatureAlgorithm.uppercase(Locale.getDefault()))
    }
}