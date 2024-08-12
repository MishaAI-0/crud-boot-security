async function getAllUsers() {
    const response = await fetch("http://localhost:8080/admin/api");
    return await response.json();
}