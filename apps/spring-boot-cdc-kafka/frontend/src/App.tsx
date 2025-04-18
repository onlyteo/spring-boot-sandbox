import React, {FC, ReactElement} from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Footer, Header} from "./fragments";
import {Home, NotFound} from "./pages";

const Layout: FC = (): ReactElement => (
    <>
        <Header/>
        <Outlet/>
        <Footer/>
    </>
);

const App: FC = (): ReactElement => {

    const router = createBrowserRouter([
        {
            element: <Layout/>,
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