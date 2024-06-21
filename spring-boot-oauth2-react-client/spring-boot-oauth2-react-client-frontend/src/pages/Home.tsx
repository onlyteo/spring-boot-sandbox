import React, {FC, ReactElement, useCallback, useReducer} from "react";
import {Col, Container, Row} from "react-bootstrap";
import {GreetingAlert, GreetingForm} from "../fragments";
import {greetingReducer, initialGreetingState} from "../state/reducers";
import {POST} from "../state/client";
import {Greeting, Person} from "../types";

export const Home: FC = (): ReactElement => {
    const [greetingState, greetingDispatch] = useReducer(greetingReducer, initialGreetingState);

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
                        <h2 className="emphasized-text">Welcome to this Ktor example!</h2>
                        <p>This example shows an OAuth2 client login for a React frontend with a Spring REST API</p>
                    </Col>
                </Row>
                <Row className="mt-5">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingForm greetingState={greetingState} getGreeting={getGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
                <Row className="mt-4">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingAlert greetingState={greetingState}/>
                    </Col>
                    <Col></Col>
                </Row>
            </div>
        </Container>
    );
}
