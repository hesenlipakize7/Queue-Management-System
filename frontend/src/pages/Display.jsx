import { useEffect, useState } from "react";

function Display() {

    const [operators, setOperators] = useState([]);

    const loadDisplay = async () => {

        try {

            const response = await fetch(
                "http://localhost:8082/api/display"
            );

            const data = await response.json();

            console.log(data);

            setOperators(data);

        } catch (error) {

            console.log(error);

        }

    };

    useEffect(() => {

        loadDisplay();

        const interval = setInterval(loadDisplay, 3000);

        return () => clearInterval(interval);

    }, []);

    return (

        <div className="container mt-5">

            <h1 className="text-center text-danger mb-5">
                Queue Display
            </h1>
            <h3>Operator sayı: {operators.length}</h3>

            <table className="table table-bordered table-striped text-center">

                <thead>

                <tr>

                    <th>Desk</th>

                    <th>Current Ticket</th>

                </tr>

                </thead>

                <tbody>

                {

                    operators.map((operator) => (

                        <tr key={operator.operatorId}>

                            <td>
                                {operator.deskNumber}
                            </td>

                            <td>
                                {operator.ticketNumber}
                            </td>

                        </tr>

                    ))

                }

                </tbody>

            </table>

        </div>

    );

}

export default Display;