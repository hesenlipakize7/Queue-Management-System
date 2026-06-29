import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

function Login() {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const login = async () => {

        try {

            const response = await api.post(
                "/operators/login",
                {
                    username,
                    password
                }
            );

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("operatorId", response.data.operatorId);
            localStorage.setItem("operatorName", response.data.name);

            navigate("/dashboard");

        } catch (error) {

            alert("Username or password is incorrect");

        }

    };

    return (

        <div>

            <h2>Operator Login</h2>

            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />

            <br /><br />

            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />

            <br /><br />

            <button onClick={login}>
                Login
            </button>

        </div>

    );

}

export default Login;
