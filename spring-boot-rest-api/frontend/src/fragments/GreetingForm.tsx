import React, {ChangeEvent, FC, FormEvent, ReactElement, useCallback, useState} from "react";
import {Greeting, initialFormData, Person, State,} from "../types";
import {Button, Form} from "react-bootstrap";

export interface GreetingFormProps {
    greetingState: State<Greeting>,
    getGreeting: (person: Person) => void
}

export const GreetingForm: FC<GreetingFormProps> = (props: GreetingFormProps): ReactElement => {
    const {greetingState, getGreeting} = props;
    const [formData, setFormData] = useState(initialFormData)

    const onChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
        const {value: name} = e.target
        const enableSubmit = !greetingState.loading && name.trim().length > 0
        setFormData({name, enableSubmit});
    }, [greetingState, setFormData]);

    const onSubmit = useCallback((e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        getGreeting({name: formData.name})
        setFormData(initialFormData)
    }, [getGreeting, formData]);

    return (
        <Form onSubmit={onSubmit}>
            <Form.Group controlId="nameInput">
                <Form.Control type="text" size="lg" placeholder="Enter your name here"
                              isInvalid={!!greetingState.error}
                              value={formData.name}
                              onChange={onChange}/>
            </Form.Group>
            <div className="mt-3 d-grid d-flex justify-content-end">
                <Button type="submit" variant="primary" disabled={!formData.enableSubmit}>Submit</Button>
            </div>
        </Form>
    );
}
