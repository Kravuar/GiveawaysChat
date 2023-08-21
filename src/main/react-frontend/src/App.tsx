import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Navbar from "./components/layout/Navbar";
import {StompSessionProvider} from "react-stomp-hooks";
import {socketURL} from "./config/Stomp";

function App() {
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

export default App;
