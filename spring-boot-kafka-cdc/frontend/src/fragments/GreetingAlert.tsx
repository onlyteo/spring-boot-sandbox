import React, {FC, ReactElement} from "react";
import {DelayedGreeting} from "../types";
import {Alert, Spinner} from "react-bootstrap";

export interface GreetingAlertProps {
    delayedGreeting: DelayedGreeting
}

export const GreetingAlert: FC<GreetingAlertProps> = (props: GreetingAlertProps): ReactElement => {
    const {delayedGreeting} = props;
    const {message, initial, waiting} = delayedGreeting

    if (initial && !waiting) {
        return (<></>);
    } else if (!initial && waiting) {
        return (
            <Alert variant="light">
                <Spinner animation="border"/>
            </Alert>
        );
    } else {
        return (
            <Alert variant="success">{message}</Alert>
        );
    }
};
