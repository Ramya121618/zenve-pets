import "./App.css";
import { useState, useEffect } from "react";

function App() {
    const [pets, setPets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const [name, setName] = useState("");
    const [breed, setBreed] = useState("");
    const [age, setAge] = useState("");
    const [owner, setOwner] = useState("");
    const [editId, setEditId] = useState(null);


    const loadPets = () => {
        fetch("http://localhost:8080/api/pets")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch pets");
                }
                return response.json();
            })
            .then(data => {
                setPets(data);
                setLoading(false);
            })
            .catch(error => {
                console.error(error);
                setError("Failed to load pets..");
                setLoading(false);
            });
    };
      useEffect(() => {
          loadPets();
      }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch(
                editId
                    ? `http://localhost:8080/api/pets/${editId}`
                    : "http://localhost:8080/api/pets",
                {
                    method: editId ? "PUT" : "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    name: name,
                    breed: breed,
                    age: age,
                    owner: owner
                })
            });

            if (!response.ok) {
                throw new Error("Failed to add pet");
            }


            if (editId) {
                alert("Pet updated successfully!");
            } else {
                alert("Pet added successfully!");
            }

            setName("");
            setBreed("");
            setAge("");
            setOwner("");
            setEditId(null);


            loadPets();


        } catch (error) {
            console.error(error);
            alert("Failed to add pet");
        }
    };
    const handleEdit = (pet) => {
        setEditId(pet.id);
        setName(pet.name);
        setBreed(pet.breed);
        setAge(pet.age);
        setOwner(pet.owner);
    };
    const handleDelete = async (id) => {
        const confirmDelete = window.confirm(
            "Are you sure you want to delete this pet?"
        );

        if (!confirmDelete) {
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/pets/${id}`,
                {
                    method: "DELETE"
                }
            );

            if (!response.ok) {
                throw new Error("Failed to delete pet");
            }

            alert("Pet deleted successfully!");

            loadPets();

        } catch (error) {
            console.error(error);
            alert("Failed to delete pet");
        }
    };

    return (
        <div>
            <h1>Zenve Pets</h1>
            <h2>Pets List</h2>
            <br />
            <h2>Add Pet</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input
                        type = "text"
                        value={name}
                        onChange={ (e) => setName(e.target.value)}
                    />

                </label>

                <br />

                    <label>
                        Breed:
                        <input
                            type ="text"
                            value={breed}
                            onChange={(e) => setBreed(e.target.value)}
                        />
                    </label>

                    <br />
                    <label>
                        Age:
                            <input
                                type ="number"
                                value={age}
                                onChange={(e) => setAge(e.target.value)}
                            />
                    </label>
                    <br />
                <label>
                    Owner:
                    <input
                        type="text"
                        value={owner}
                        onChange={(e) => setOwner(e.target.value)}
                    />
                </label>
                     <br />
                <button type="submit">
                    {editId ? "Update Pet" : "Add Pet"}
                </button>
                </form>


            {loading && <p>Loading pets....</p>}
            {error && <p>{error}</p>}
            {!loading && !error && (

                <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Breed</th>
                    <th>Age</th>
                    <th>Owner</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                {pets.map((pet) => (
                    <tr key={pet.id}>
                        <td>{pet.id}</td>
                        <td>{pet.name}</td>
                        <td>{pet.breed}</td>
                        <td>{pet.age}</td>
                        <td>{pet.owner}</td>
                        <td>
                            <button
                                type="button"
                                onClick={() => handleEdit(pet)}
                            >
                                Edit
                            </button>

                            <button
                                type="button"
                                onClick={() => handleDelete(pet.id)}
                                style={{ marginLeft: "10px" }}
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            )}
        </div>

    );
}

export default App;
