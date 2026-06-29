import { useEffect, useState } from "react";
import api from "../api/api";

function Kiosk() {

    const [services, setServices] = useState([]);
    const [generatedTicket, setGeneratedTicket] = useState(null);

    useEffect(() => {

        loadServices();

    }, []);

    const loadServices = async () => {

        try {

            const response = await fetch(
                "http://localhost:8082/api/statistics/kiosk"
            );

            const data = await response.json();

            console.log(data);

            setServices(data);


        } catch {

            alert("Data could not be loaded");

        }

    };

    const createTicket = async (serviceTypeId) => {

        try {

            const response = await fetch(
                "http://localhost:8082/api/tickets",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        serviceTypeId: serviceTypeId
                    })
                }
            );

            const data = await response.json();

            alert("Ticket Number: " + data.ticketNumber);

            loadServices();

        } catch (error) {

            console.log(error);

            alert("Ticket could not be created");
        }

    };

    return (

        <div className="container mt-5">

            <h1
                className="text-center mb-5 text-danger"
            >

                Kontakt-a xoş gəlmisiniz

            </h1>

            <div className="row">

                {

                    <div className="row">

                        {services.map((service) => (

                            <div
                                className="col-md-4 mb-3"
                                key={service.serviceTypeId}
                            >

                                <div
                                    className="card shadow"
                                    style={{ cursor: "pointer" }}
                                    onClick={() => createTicket(service.serviceTypeId)}
                                >

                                    <div className="card-body text-center p-4">

                                        <h3>{service.serviceName}</h3>

                                        <hr />

                                        <p>
                                            Gözləmədə: {service.waitingCount}
                                        </p>

                                    </div>

                                </div>

                            </div>

                        ))}

                    </div>

                }


            </div>

            {
                generatedTicket && (

                    <div
                        className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
                        style={{
                            background: "rgba(0,0,0,0.6)"
                        }}
                    >

                        <div
                            className="bg-white p-5 rounded shadow text-center"
                        >

                            <h2>Sizin nömrəniz</h2>

                            <h1
                                className="display-1 text-danger"
                            >
                                {generatedTicket}
                            </h1>

                            <button
                                className="btn btn-primary mt-3"
                                onClick={() => setGeneratedTicket(null)}
                            >
                                Bağla
                            </button>

                        </div>

                    </div>

                )
            }


        </div>


    );

}

export default Kiosk;