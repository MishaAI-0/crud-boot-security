let  userId;
const deleteModal = document.getElementById('deleteModal');
deleteModal.addEventListener('show.bs.modal', async function (event) {
    const button = event.relatedTarget;
    const parent = button.closest('tr');
    const rows = parent.querySelectorAll("td");
    userId = rows[0].innerText;

    let userTableData = await getAllUsers();
    const currentUser = userTableData.find(user => user.id == userId);

    if (currentUser) {
        const modalUserId = this.querySelector('#deleteId');
        const modalUserUsername = this.querySelector('#deleteFirstName');
        const modalUserLastName = this.querySelector('#deleteLastName');
        const modalUserAge = this.querySelector('#deleteAge');
        const modalUserEmail = this.querySelector('#deleteEmail');

        modalUserId.value = currentUser.id;
        modalUserUsername.value = currentUser.username;
        modalUserLastName.value = currentUser.surname;
        modalUserAge.value = currentUser.age;
        modalUserEmail.value = currentUser.email;
    } else {
        console.error('User not found');
    }
});
const deleteUserSubmitButton = document.getElementById('delete-user-submit-button')
deleteUserSubmitButton.addEventListener('click', () => {

    const options = {
        "method": 'DELETE',
        "headers": {
            'Content-Type': 'application/json',
        },
        "body": JSON.stringify()}

    fetch(`http://localhost:8080/api/${userId}`,options)
        .then(response => {
            const modal = bootstrap.Modal.getInstance(deleteModal);
            modal.hide()
            fillUsersTable();
        })
        .catch(error => {
        });

});


