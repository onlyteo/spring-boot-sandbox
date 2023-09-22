import React, {FC, ReactElement} from "react";
import {Container} from "react-bootstrap";

export const Home: FC = (): ReactElement => {
    return (
        <Container>
            <div className="description-box px-3 py-5 rounded-3">
                <h2 className="emphasized-text">Welcome to this Spring Boot example!</h2>
                <p>This example shows an OIDC client login for a React frontend with a Spring REST API</p>
            </div>
        </Container>
    );
};
