import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

function Dashboard() {

    const navigate = useNavigate();

    const operatorId = localStorage.getItem("operatorId");
    const operatorName = localStorage.getItem("operatorName");

    const [ticketNumber, setTicketNumber] = useState("-");
    const [deskNumber, setDeskNumber] = useState("-");
    const [currentTime, setCurrentTime] = useState("");
    const [serviceTypeId, setServiceTypeId] = useState("");

    useEffect(() => {

        const timer = setInterval(() => {

            setCurrentTime(
                new Date().toLocaleString()
            );

        }, 1000);

        return () => clearInterval(timer);

    }, []);

    const nextTicket = async () => {

        try {

            const response =
                await api.post(
                    `/operators/${operatorId}/next`
                );

            setTicketNumber(
                response.data.ticketNumber
            );

            setDeskNumber(
                response.data.deskNumber
            );

        } catch {

            alert("No waiting ticket");

        }

    };

    const recallTicket = async () => {

        try {

            const response =
                await api.post(
                    `/operators/${operatorId}/recall`
                );

            setTicketNumber(
                response.data.ticketNumber
            );

            setDeskNumber(
                response.data.deskNumber
            );

        } catch {

            alert("No active ticket");

        }

    };

    const skipTicket = async () => {

        try {

            const response =
                await api.post(
                    `/operators/${operatorId}/skip`
                );

            setTicketNumber(
                response.data.ticketNumber
            );

            setDeskNumber(
                response.data.deskNumber
            );

        } catch {

            alert("Skip failed");

        }

    };

    const finishTicket = async () => {

        try {

            await api.post(
                `/operators/${operatorId}/finish`
            );

            setTicketNumber("-");
            setDeskNumber("-");

            alert("Ticket finished");

        } catch {

            alert("Finish failed");

        }

    };

    const transferTicket = async () => {

        if (serviceTypeId === "") {

            alert("Select service type");

            return;

        }

        try {

            await api.post(

                `/operators/${operatorId}/transfer`,

                {
                    serviceTypeId:
                        Number(serviceTypeId)
                }

            );

            setTicketNumber("-");
            setDeskNumber("-");

            alert("Ticket transferred");

        }

        catch {

            alert("Transfer failed");

        }

    };

    const logout = () => {

        localStorage.clear();

        navigate("/");

    };

    return (

        <div className="container mt-5">

            <div className="card shadow-lg p-5">

                <h2 className="text-center">

                    Queue Management System

                </h2>

                <h5 className="text-center">

                    Welcome, {operatorName}

                </h5>

                <p className="text-center text-muted">

                    {currentTime}

                </p>

                <hr/>

                <div className="card text-center p-5 shadow">

                    <h4>

                        Current Ticket

                    </h4>

                    <h1
                        className="display-1 text-danger fw-bold"
                    >

                        {ticketNumber}

                    </h1>

                    <h3>

                        Desk {deskNumber}

                    </h3>

                </div>

                <div className="row mt-4 g-2">

                    <div className="col-6">

                        <button
                            className="btn btn-primary w-100"
                            onClick={nextTicket}
                        >

                            Next

                        </button>

                    </div>

                    <div className="col-6">

                        <button
                            className="btn btn-warning w-100"
                            onClick={recallTicket}
                        >

                            Recall

                        </button>

                    </div>

                    <div className="col-6">

                        <button
                            className="btn btn-secondary w-100"
                            onClick={skipTicket}
                        >

                            Skip

                        </button>

                    </div>

                    <div className="col-6">

                        <button
                            className="btn btn-success w-100"
                            onClick={finishTicket}
                        >

                            Finish

                        </button>

                    </div>

                </div>

                <hr/>

                <h5>

                    Transfer Ticket

                </h5>

                <select

                    className="form-select"

                    value={serviceTypeId}

                    onChange={(e) =>
                        setServiceTypeId(
                            e.target.value
                        )
                    }

                >

                    <option value="">
                        Select Service
                    </option>

                    <option value="1">
                        Kredit
                    </option>

                    <option value="2">
                        Ödəniş
                    </option>

                    <option value="3">
                        Nağd alış
                    </option>

                    <option value="4">
                        Dəyişdirmə
                    </option>

                    <option value="5">
                        Qaytarma
                    </option>

                    <option value="6">
                        Fiziki məhdudiyyətli
                    </option>

                </select>

                <button

                    className="btn btn-info w-100 mt-2"

                    onClick={transferTicket}

                >

                    Transfer Ticket

                </button>

                <hr/>

                <button

                    className="btn btn-danger w-100"

                    onClick={logout}

                >

                    Logout

                </button>

            </div>

        </div>

    );

}

export default Dashboard;