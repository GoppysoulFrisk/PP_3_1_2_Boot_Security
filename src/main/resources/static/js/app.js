document.addEventListener('DOMContentLoaded', () => {
    // Fetch users from the server
    fetch('/api/users')
        .then(response => response.json())
        .then(data => {
            populateTable(data);
        })
        .catch(error => console.error('Error fetching users:', error));
});

function populateTable(users) {
    const tableBody = document.querySelector('#user-table tbody');
    tableBody.innerHTML = ''; // Clear existing rows

    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td><button class="view-details" data-id="${user.id}">View Details</button></td>
        `;
        tableBody.appendChild(row);
    });

    // Add event listeners for buttons
    document.querySelectorAll('.view-details').forEach(button => {
        button.addEventListener('click', (event) => {
            const userId = event.target.getAttribute('data-id');
            showUserDetails(userId);
        });
    });
}

function showUserDetails(userId) {
    fetch(`/api/users/${userId}`)
        .then(response => response.json())
        .then(user => {
            document.querySelector('#modal-user-name').textContent = `Name: ${user.name}`;
            document.querySelector('#modal-user-email').textContent = `Email: ${user.email}`;
            document.querySelector('#user-modal').style.display = 'block';
        })
        .catch(error => console.error('Error fetching user details:', error));
}

// Close modal
document.querySelector('#close-modal').addEventListener('click', () => {
    document.querySelector('#user-modal').style.display = 'none';
});