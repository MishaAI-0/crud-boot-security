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
    // const buttons = document.querySelectorAll('.btn.btn-danger');
    //
    // buttons.forEach((button) => {
    //     button.addEventListener('click', async (event) => {
    //         const button = event.target;
    //         const parent = button.parentElement.parentElement;
    //
    //         const rows = parent.querySelector("td");
    //         let new_id = rows.innerText;
    //         console.log(`clicked ${new_id}`);
    //
    //         fetch(`http://localhost:8080/api/${new_id}`, {
    //             method: 'DELETE',
    //             headers: {
    //                 'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({})
    //         })
    //             .then(response => {
    //                 fillUsersTable();
    //             })
    //             .catch(error => {
    //             });
    //     })
    // })
}

(async () => {
    await fillUsersTable();
})();