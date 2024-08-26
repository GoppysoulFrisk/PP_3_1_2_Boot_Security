$.get('http://localhost:8080/api/v1/user', (user) => {
    console.log("GET /user in populateLayout to populate header and sidebar");

    // Обновление верхней панели
    $('#headerUsername').text(user.username);
    $('#headerRoles').text(Array.from(user.roles).join(', ')); // Преобразуем Set в массив и объединяем

    const path = $(location).attr("pathname");

    // Создание ссылок
    const userLink = $('<a href="/user" class="nav-link">User</a>');
    const adminLink = $('<a href="/admin" class="nav-link">Admin</a>');

    // Установка класса 'active' для текущей ссылки
    for (const link of [userLink, adminLink]) {
        if (path.includes(link.attr('href'))) {
            link.addClass('active');
        }
    }

    // Обновление боковой панели
    $('#sidebarNav').empty(); // Очищаем боковую панель перед добавлением новых ссылок
    $('#sidebarNav').append(userLink); // Добавляем ссылку пользователя

    // Добавляем ссылку администратора, если у пользователя есть роль ADMIN
    if (user.roles.includes('ROLE_ADMIN')) { // Проверяем наличие роли ADMIN в Set
        console.log("Роль есть");
        $('#sidebarNav').append(adminLink);
    }
});
$.get('http://localhost:8080/api/v1/user', (user) => {
    console.log(`GET /user in populateUser to populate user table`);
    $('#userMainHeading').text(`User ${user.username}`);

    $('#idCell').text(user.id);
    $('#usernameCell').text(user.username);
    $('#emailCell').text(user.email);
    $('#phoneCell').text(user.phone);
    $('#rolesCell').text(user.roles);
});

