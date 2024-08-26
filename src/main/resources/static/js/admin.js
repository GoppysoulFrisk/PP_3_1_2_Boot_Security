$(document).ready(function() {
    fetchUsers();

    function fetchUsers() {
        $.get('/api/v1/users', function(users) {
            const usersTableBody = $('#nav-users tbody');
            usersTableBody.empty(); // Очистка таблицы перед заполнением

            users.forEach(user => {
                const roles = user.roles.join(', '); // Преобразуем массив ролей в строку
                const userRow = `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${roles}</td>
                        <td>
                            <button type="button" data-bs-toggle="modal"
                                    data-bs-target="#editModal${user.id}"
                                    class="btn btn-info">Edit</button>
                            <div id="editModal${user.id}" class="modal" tabindex="-1">
                                <!-- HTML для модального окна редактирования -->
                            </div>
                        </td>
                        <td>
                            <button type="button" data-bs-toggle="modal"
                                    data-bs-target="#deleteModal${user.id}"
                                    class="btn btn-danger">Delete</button>
                            <div id="deleteModal${user.id}" class="modal" tabindex="-1">
                                <!-- HTML для модального окна удаления -->
                            </div>
                        </td>
                    </tr>`;
                usersTableBody.append(userRow);
            });
        });
    }
});
$(document).ready(function() {
    $('#newUserForm').submit(function(event) {
        event.preventDefault(); // Отключаем стандартное поведение формы

        const newUser = {
            username: $('#username').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            phone: $('#phone').val(),
            roles: $('#roles').val() // Массив с ID ролей
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/users',
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: function() {
                fetchUsers(); // Обновляем таблицу
                $('#nav-users-tab').click(); // Переключаемся на вкладку с таблицей
            },
            error: function(error) {
                console.error('Ошибка при добавлении пользователя:', error);
            }
        });
    });
});
$(document).on('click', '.btn-info', function() {
    const userId = $(this).closest('tr').find('td:first').text();

    $.get(`/api/v1/users/${userId}`, function(user) {
        $('#editUsername').val(user.username);
        $('#editEmail').val(user.email);
        $('#editPhone').val(user.phone);
        $('#editRoles').val(user.roles);

        $('#editUserForm').submit(function(event) {
            event.preventDefault();

            const updatedUser = {
                id: userId,
                username: $('#editUsername').val(),
                email: $('#editEmail').val(),
                phone: $('#editPhone').val(),
                roles: $('#editRoles').val()
            };

            $.ajax({
                type: 'PUT',
                url: '/api/v1/users',
                contentType: 'application/json',
                data: JSON.stringify(updatedUser),
                success: function() {
                    fetchUsers(); // Обновляем таблицу
                    $('#editModal' + userId).modal('hide'); // Закрываем модальное окно
                },
                error: function(error) {
                    console.error('Ошибка при редактировании пользователя:', error);
                }
            });
        });
    });
});
$(document).on('click', '.btn-danger', function() {
    const userId = $(this).closest('tr').find('td:first').text();

    $('#deleteUserButton').click(function() {
        $.ajax({
            type: 'DELETE',
            url: `/api/v1/users/${userId}`,
            success: function() {
                fetchUsers(); // Обновляем таблицу
                $('#deleteModal' + userId).modal('hide'); // Закрываем модальное окно
            },
            error: function(error) {
                console.error('Ошибка при удалении пользователя:', error);
            }
        });
    });
});