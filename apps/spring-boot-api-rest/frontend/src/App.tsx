import React, {FC, ReactElement, useEffect, useReducer} from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {userInitialState, userReducer} from "./state/reducers";
import {User} from "./types";
import {Footer, Header} from "./fragments";
import {Home, NotFound} from "./pages";
import {GET} from "./state/client";

interface LayoutProps {
    user: User
}

const Layout: FC<LayoutProps> = (props: LayoutProps): ReactElement => (
    <>
        <Header user={props.user}/>
        <Outlet/>
        <Footer/>
    </>
);

const App: FC = (): ReactElement => {
    const [userState, userDispatch] = useReducer(userReducer, userInitialState);

    useEffect(() => {
        GET<User>("/api/user")
            .then(response => userDispatch({status: 'SUCCESS', data: response.data}))
            .catch(error => {
                console.log("ERROR", error)
                userDispatch({status: 'FAILED'})
            });
    }, []);

    const router = createBrowserRouter([
        {
            element: <Layout user={userState.data}/>,
            children: [
                {
                    path: "/",
                    element: <Home/>,
                },
                {
                    path: "*",
                    element: <NotFound/>,
                }
            ]
        }
    ]);

    return <RouterProvider router={router}/>;
};

export default App;