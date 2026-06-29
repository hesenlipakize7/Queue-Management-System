import { BrowserRouter, Routes, Route } from "react-router-dom";

import Display from "./pages/Display";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Kiosk from "./pages/Kiosk";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route path="/display" element={<Display />} />

                <Route
                    path="/"
                    element={<Login/>}
                />

                <Route
                    path="/dashboard"
                    element={<Dashboard/>}
                />

                <Route
                    path="/kiosk"
                    element={<Kiosk/>}
                />

            </Routes>

        </BrowserRouter>

    );

}

export default App;
