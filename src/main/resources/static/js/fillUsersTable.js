async function fillUsersTable() {
    userTableData = await getAllUsers();
    console.log("Hello it worked!!");
    const userDataPlaceholder = document.getElementById("place_to_put_users_data");
    userDataPlaceholder.innerHTML = "";
    console.log("here is the html:");
    console.log(userDataPlaceholder.innerHTML);

    userTableData.forEach(user => {
        let resultRolesString = "";


        if (user.roles.some(role => role.name === "ROLE_ADMIN")) {
            resultRolesString += `<span>ADMIN </span>`;
        }

        if (user.roles.some(role => role.name === "ROLE_USER")) {
            resultRolesString += `<span>USER </span>`;
        }

        userDataPlaceholder.insertAdjacentHTML('beforeend',
            `<tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${resultRolesString}</td>
                <td>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                            data-bs-target="#editModal">Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                            data-bs-target="#deleteModal">Delete
                    </button>
                </td>
            </tr>`
        );
    });

}

(async () => {
    await fillUsersTable();
})();