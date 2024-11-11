import React, {FC, ReactElement, useCallback, useEffect, useReducer, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import useWebSocket from 'react-use-websocket';
import {GreetingAlert, GreetingForm} from "../fragments";
import {Greeting, initialDelayedGreeting, Person, receivedDelayedGreeting} from "../types";
import {POST} from "../state/client";
import {greetingReducer, initialGreetingState} from "../state/reducers";

export const Home: FC = (): ReactElement => {
    const [greetingState, greetingDispatch] = useReducer(greetingReducer, initialGreetingState);
    const [delayedGreeting, setDelayedGreeting] = useState(initialDelayedGreeting)
    const {lastJsonMessage} = useWebSocket<Greeting>("ws://localhost:8080/ws/greetings", {
        onOpen: (event) => {
            console.log("WebSocket connection opened", event);
        },
        onError: (event) => {
            console.log("WebSocket connection error", event);
        },
        onClose: (event) => {
            console.log("WebSocket connection closed", event);
        },
        shouldReconnect: () => false,
    });

    useEffect(() => {
        console.log("Greeting state", greetingState);
    }, [greetingState]);

    useEffect(() => {
        console.log("Last message", lastJsonMessage);
        if (lastJsonMessage) {
            setDelayedGreeting({...receivedDelayedGreeting, message: lastJsonMessage.message});
        }
    }, [lastJsonMessage, setDelayedGreeting]);

    const getGreeting = useCallback((person: Person) => {
        greetingDispatch({status: 'LOADING'})
        POST<Greeting, Person>("/api/greetings", person)
            .then((response) => {
                greetingDispatch({status: 'SUCCESS', data: response.data})
            })
            .catch(error => {
                console.log("ERROR", error)
                greetingDispatch({status: 'FAILED'})
            });
    }, [greetingDispatch]);

    return (
        <Container>
            <div className="description-box px-3 py-5 rounded-3">
                <Row>
                    <Col>
                        <h2 className="emphasized-text">Welcome to this Spring Boot example!</h2>
                        <p>This example shows a Kafka CDC architecture</p>
                    </Col>
                </Row>
                <Row className="mt-5">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingForm delayedGreeting={delayedGreeting}
                                      getGreeting={getGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
                <Row className="mt-4">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingAlert delayedGreeting={delayedGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
            </div>
        </Container>
    );
}
