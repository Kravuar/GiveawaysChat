import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Navbar from "./components/layout/Navbar";
import {StompSessionProvider} from "react-stomp-hooks";
import {socketURL} from "./config/Stomp";
import {getTheme} from "./config/Theme";
import withTheme from "./components/hoc/withTheme";

function app() {
    return (
        <StompSessionProvider url={socketURL}>
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/" element={<Home />} />
                </Routes>
            </BrowserRouter>
        </StompSessionProvider>
    );
}

const App = withTheme(app, { themeProvider: getTheme, defaultMode: "light" });
export default App;
