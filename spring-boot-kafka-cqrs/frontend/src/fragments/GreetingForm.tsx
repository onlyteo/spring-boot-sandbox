import React, {ChangeEvent, FC, FormEvent, ReactElement, useCallback, useState} from "react";
import {DelayedGreeting, initialFormData, Person} from "../types";
import {Button, Form} from "react-bootstrap";

export interface GreetingFormProps {
    delayedGreeting: DelayedGreeting,
    getGreeting: (person: Person) => void
}

export const GreetingForm: FC<GreetingFormProps> = (props: GreetingFormProps): ReactElement => {
    const {delayedGreeting, getGreeting} = props;
    const [formData, setFormData] = useState(initialFormData)

    const onChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
        const {value: name} = e.target
        const enableSubmit = !delayedGreeting.waiting && name.trim().length > 0
        setFormData({name, enableSubmit});
    }, [delayedGreeting]);

    const onSubmit = useCallback((e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        getGreeting({name: formData.name})
        setFormData(initialFormData)
    }, [formData, setFormData, getGreeting]);

    return (
        <Form onSubmit={onSubmit}>
            <Form.Group controlId="nameInput">
                <Form.Control type="text" size="lg" placeholder="Enter your name here"
                              value={formData.name}
                              onChange={onChange}/>
            </Form.Group>
            <div className="mt-3 d-grid d-flex justify-content-end">
                <Button type="submit" variant="primary" disabled={!formData.enableSubmit}>Submit</Button>
            </div>
        </Form>
    );
}
