package com.onlyteo.sandbox.app.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.GreetingEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class GreetingWebSocketHandler(
    private val objectMapper: ObjectMapper
) : TextWebSocketHandler(), ApplicationListener<GreetingEvent> {

    private val logger = buildLogger
    private val sessions = mutableMapOf<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("Opening websocket session {}", session.id)
        sessions[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info("Closing websocket session {}", session.id)
        sessions.remove(session.id)
    }

    override fun onApplicationEvent(greetingEvent: GreetingEvent) {
        val greeting = greetingEvent.greeting
        if (sessions.isEmpty()) {
            logger.warn("No open websocket sessions for greeting {}", greeting.message)
        } else {
            val message = TextMessage(objectMapper.writeValueAsString(greeting))
            sessions.forEach { (_, session) ->
                logger.info(
                    "Sending greeting \"{}\" to websocket \"/ws/greetings\" for session {}",
                    greeting.message,
                    session.id
                )
                session.sendMessage(message)
            }
        }
    }
}