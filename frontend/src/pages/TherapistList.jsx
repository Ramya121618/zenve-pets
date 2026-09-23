
import { useState, useEffect } from "react";

function TherapistList() {

    // states
    const [name, setName] = useState("");
    const [specialization, setSpecialization] = useState("");
    const [sessionType, setSessionType] = useState("");
    const [therapists, setTherapists] = useState([]);
    const [error, setError] = useState("");
    const [editId, setEditId] = useState(null);

    // GET
    useEffect(() => {
        fetch("http://localhost:8080/api/therapists")
            .then((response) => response.json())
            .then((data) => {
                setTherapists(data);
            })
            .catch((error) => {
                console.error("Error fetching therapists:", error);
            });
    }, []);

    // ADD //UPDATE
    const handleSubmit = async (e) => {
        e.preventDefault();

        const therapist = {
            name: name,
            specialization: specialization,
            sessionType: sessionType
        };

        try {
            const response = await fetch(
                editId
                    ? `http://localhost:8080/api/therapists/${editId}`
                    : "http://localhost:8080/api/therapists",
                {
                    method: editId ? "PUT" : "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(therapist)
                }
            );
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error("Please enter a valid therapist name and specialization.");
            }

            const data = await response.json();
            if (editId) {
                setTherapists((prev) =>
                    prev.map((therapist) =>
                        therapist.id === data.id ? data : therapist
                    )
                );
            } else {
                setTherapists((prev) => [...prev, data]);
            }
            setError("");
            setName("");
            setSpecialization("");
            setSessionType("");
            setEditId(null);

        } catch (error) {
            console.error("Error:", error);
            setError(error.message);
        }
    };
    const handleEdit = (therapist) => {
        setError("");
        setEditId(therapist.id);
        setName(therapist.name);
        setSpecialization(therapist.specialization);
        setSessionType(therapist.sessionType);
    };

    const handleDelete = async (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this therapist?"
        );

        if (!confirmDelete) {
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/therapists/${id}`,
                {
                    method: "DELETE"
                }
            );

            if (!response.ok) {
                throw new Error("Failed to delete therapist");
            }

            setTherapists((prev) =>
                prev.filter((therapist) => therapist.id !== id)
            );

        } catch (error) {
            console.error("Error deleting therapist:", error);
            setError(error.message);
        }
    };
    return (
        <div>
            <h1>Therapist List</h1>
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Therapist Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                <br />

                <div>
                    <label>Specialization:</label>
                    <input
                        type="text"
                        value={specialization}
                        onChange={(e) => setSpecialization(e.target.value)}
                    />
                </div>

                <br />

                <div>
                    <label>Session Type:</label>
                    <select
                        value={sessionType}
                        onChange={(e) => setSessionType(e.target.value)}
                    >
                        <option value="">Select Session Type</option>
                        <option value="Online">Online</option>
                        <option value="In-Person">In-Person</option>
                    </select>
                </div>

                <br />


                <button type="submit">
                    {editId ? "Update Therapist" : "Update"}
                </button>


            </form>

            <br />

            <h2>Therapists</h2>

            <table className="therapist-table">
                <thead>
                <tr>
                    <th className="therapist-cell">ID</th>
                    <th className="therapist-cell">Name</th>
                    <th className="therapist-cell">Specialization</th>
                    <th className="therapist-cell">Session Type</th>
                    <th className="therapist-cell">Action</th>
                </tr>
                </thead>

                <tbody>
                {therapists.map((therapist) => (
                    <tr key={therapist.id}>
                        <td className="therapist-cell">{therapist.id}</td>
                        <td className="therapist-cell">{therapist.name}</td>
                        <td className="therapist-cell">{therapist.specialization}</td>
                        <td className="therapist-cell">{therapist.sessionType}</td>
                        <td className="therapist-cell">
                            <td className="therapist-cell">
                                <button
                                    type="button"
                                    className="action-button"
                                    onClick={() => handleEdit(therapist)}
                                >
                                    Edit
                                </button>

                                <button
                                    type="button"
                                    className="action-button"
                                    onClick={() => handleDelete(therapist.id)}
                                >
                                    Delete
                                </button>
                            </td>
                        </td>
                    </tr>

                ))}
                </tbody>
            </table>
        </div>
    );
}

export default TherapistList;