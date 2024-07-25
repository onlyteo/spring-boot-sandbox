package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.handler.GreetingWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@EnableWebSocket
@Configuration(proxyBeanMethods = false)
class WebSocketConfig(private val webSocketHandler: GreetingWebSocketHandler) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSocketHandler, "/ws/greetings").setAllowedOrigins("*")
    }
}