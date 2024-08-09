document.addEventListener('DOMContentLoaded', function() {
    fetch('api/current')
        .then(response => response.json())
        .then(user => {

            document.getElementById('user-email').textContent = user.email;

            const rolesContainer = document.getElementById('user-roles');
            const sidebarNav = document.getElementById('sidebar-nav');
            const roles = user.roles.map(role => {
                if (role.name === 'ROLE_ADMIN') {
                    return 'ADMIN';
                }
                else {
                    return 'USER';
                }
            })
            if (user.roles.some(role => role.name === "ROLE_ADMIN")) {
                const adminLink = document.createElement('a');
                adminLink.className = 'nav-link mt-4';
                adminLink.textContent = 'Admin';
                adminLink.href = '/admin';

                if (window.location.pathname === '/admin') {
                    adminLink.classList.add('active');
                }
                sidebarNav.appendChild(adminLink);

                addUserRole();
            } else {
                addUserRole();
            }

            rolesContainer.textContent = roles.join(' ');
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        });
});



function addUserRole() {
    const sidebarNav = document.getElementById('sidebar-nav');
    const userLink = document.createElement('a');
    userLink.className = 'nav-link mt-4';
    userLink.textContent = 'User';
    userLink.href = '/user';

    if (window.location.pathname === '/user') {
        userLink.classList.add('active');
    }

    sidebarNav.appendChild(userLink);
}

