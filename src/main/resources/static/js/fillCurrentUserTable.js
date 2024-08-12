document.addEventListener('DOMContentLoaded', function() {
    fetch('user/api/current')
        .then(response => response.json())
        .then(user => {
            const userDataPlaceholder = document.getElementById("place_to_put_currentUser_data");
            userDataPlaceholder.innerHTML = "";

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
            </tr>`
            );
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        });
});