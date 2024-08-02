let id,username,surname,age,email,password,roles;

const editModal = document.getElementById('editModal');
editModal.addEventListener('show.bs.modal', async function (event) {
    const button = event.relatedTarget;
    const parent = button.parentElement.parentElement;

    const rows = parent.querySelectorAll("td");
    let current_user;
    let userTableData = await getAllUsers();
    id = rows[0];
    userTableData.forEach(user => {
        if (user.id == id.innerText) {
            current_user = user;
        }
    });

    id = current_user.id,
        username = current_user.username,
        surname = current_user.surname,
        age = current_user.age,
        email = current_user.email,
        password = current_user.password,
        roles = current_user.roles;


    const modalUserId = editModal.querySelector('#inputId');
    const modalUserUsername = editModal.querySelector('#inputFirstName');
    const modalUserLastName = editModal.querySelector('#inputLastName');
    const modalUserAge = editModal.querySelector('#inputAge');
    const modalUserEmail = editModal.querySelector('#inputEmail');


    modalUserId.value = id;
    modalUserUsername.value = username;
    modalUserLastName.value = surname;
    modalUserAge.value = age;
    modalUserEmail.value = email;

});

const editUserSubmitButton = document.getElementById('edit-user-submit-button')
editUserSubmitButton.addEventListener('click', () => {
    let inputId = document.getElementById("inputId").value;
    let inputUsername = document.getElementById("inputFirstName").value;
    let inputLastName = document.getElementById("inputLastName").value;
    let inputAge = document.getElementById("inputAge").value;
    let inputEmail = document.getElementById("inputEmail").value;
    let inputPassword = document.getElementById("inputPassword").value;
    let inputRoles = document.getElementById("inputRole").value;
    console.log('${inputId},${inputUsername},${inputLastName},${inputAge},${inputEmail},${inputPassword},${inputRoles},');

    const editUserData = {
        "id": inputId,
        "username": inputUsername,
        "surname": inputLastName,
        "age":inputAge,
        "email": inputEmail,
    }
    if (!(inputPassword === "")) {
        editUserData.password = inputPassword;
    }
    if (!(inputRoles === "")) {
        if (inputRoles === "USER") {
            editUserData.roles = [
                {
                    name: "ROLE_USER"
                }
            ];
        } else if (inputRoles === "ADMIN") {
            editUserData.roles = [
                {
                    "name": "ROLE_USER"
                },
                {
                    "name": "ROLE_ADMIN"
                }
            ]
        }
    }
    const options = {
        "method": 'PATCH',
        "headers": {
            'Content-Type': 'application/json',
        },
        "body": JSON.stringify(editUserData)
    }

    fetch("http://localhost:8080/api", options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            const modal = bootstrap.Modal.getInstance(editModal);
            modal.hide();
            fillUsersTable();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

});