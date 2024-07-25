import React, {FC, ReactElement} from "react";
import {Navbar} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {
    faCircleInfo,
    faCircleUser,
    faHouse,
    faRightFromBracket,
    faScrewdriverWrench
} from "@fortawesome/free-solid-svg-icons";

library.add(faCircleInfo, faCircleUser, faHouse, faRightFromBracket, faScrewdriverWrench);

export const Header: FC = (): ReactElement => {

    return (
        <header className="header mb-5">
            <Navbar variant="dark" expand="lg" className="p-4">
                <Navbar.Brand className="mx-4" href="/">
                    <h1>
                        <FontAwesomeIcon className="navbar-logo emphasized-text" icon="screwdriver-wrench"/><span
                        className="navbar-title ms-3">Spring Boot Sandbox</span>
                    </h1>
                </Navbar.Brand>
            </Navbar>
        </header>
    );
};
