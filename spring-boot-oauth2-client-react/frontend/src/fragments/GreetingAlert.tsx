import React, {FC, ReactElement} from "react";
import {Alert, Spinner} from "react-bootstrap";
import {Greeting, State} from "../types";

export interface GreetingAlertProps {
    greetingState: State<Greeting>
}

export const GreetingAlert: FC<GreetingAlertProps> = (props: GreetingAlertProps): ReactElement => {
    const {greetingState} = props;
    const {loading, data: greeting, error} = greetingState

    if (error) {
        return (<Alert variant="danger">{error}</Alert>);
    } else if (!loading && !greeting) {
        return (<></>);
    } else if (loading && !greeting) {
        return (
            <Alert variant="light">
                <Spinner animation="border"/>
            </Alert>
        );
    } else {
        const {message} = greeting
        return (<Alert variant="success">{message}</Alert>);
    }
};
