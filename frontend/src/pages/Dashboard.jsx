import React, { useEffect, useState } from "react";

function Dashboard() {

    const [pets, setPets] = useState([]);

    useEffect(() => {

        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/owners/1/pets", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(response => {
                console.log("STATUS =", response.status);
                console.log("RESPONSE OK =", response.ok);

                return response.json();
            })
            .then(data => {
                console.log("PET DATA =", data);
                setPets(data);
            })
            .catch(error => {
                console.log("ERROR =", error);
            });

    }, []);

    return (
        <div>
            <h1>My Dashboard</h1>

            <h2>My Pets</h2>

            {pets.map(pet => (
                <div key={pet.id} style={{ marginBottom: "30px" }}>
                    <p>Name: {pet.name}</p>
                    <p>Breed: {pet.breed}</p>
                    <p>Age: {pet.age}</p>
                </div>
            ))}
        </div>
    );
}

export default Dashboard;