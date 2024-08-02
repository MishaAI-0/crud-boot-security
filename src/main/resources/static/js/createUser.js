const createUserButton = document.getElementById("createUserButton");
createUserButton.addEventListener('click',() => {
    let inputUsername = document.getElementById("newUserInputFirstName").value;
    let inputLastName = document.getElementById("newUserInputLastName").value;
    let inputAge = document.getElementById("newUserInputAge").value;
    let inputEmail = document.getElementById("newUserInputEmail").value;
    let inputPassword = document.getElementById("newUserInputPassword").value;
    let inputRoles = document.getElementById("newUserInputRole").value;

    const editUserData = {
        "username": inputUsername,
        "surname": inputLastName,
        "age":inputAge,
        "email": inputEmail,
        "password":inputPassword,
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
        "method": 'POST',
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
        .then(async data => {
            console.log('Success:', data);
            await fillUsersTable();
            document.getElementById("newUserInputFirstName").value = '';
            document.getElementById("newUserInputLastName").value = '';
            document.getElementById("newUserInputAge").value = '';
            document.getElementById("newUserInputEmail").value = '';
            document.getElementById("newUserInputPassword").value = '';
            document.getElementById("newUserInputRole").value = '';
            const usersTab = new bootstrap.Tab(document.getElementById('usersTable'));
            usersTab.show();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

});