async function getAllUsers() {
    const response = await fetch("http://localhost:8080/api");
    return await response.json();
}