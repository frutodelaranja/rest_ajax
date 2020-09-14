// const getUsersButton = document.querySelector("#loadUsers");

function listUsers() {
    $.ajax({
        url: '/admin/users',
        success: function (users) {
            users.forEach(el => {

                let roles = el.roles.length > 1 ? el.roles[0].name + ", " + el.roles[1].name : el.roles[0].name;


                $('#users').append(
                    `<tr id='${el.id}'>
                            <td id="tableId">${el.id}</td>
                            <td id="tableId">${el.name}</td>
                            <td id="tableId">${el.username}</td>
                            <td id="tableId">${el.password}</td>
                            <td id="tableId">${roles}</td>
                            <td>
                            <button id="editUser" editID="${el.id}" type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#modal">Edit</button>
                            </td>
                            <td>
                            <button id="deleteUser" deleteID="${el.id}" type="button" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#modal">Delete</button>
                            </td>
                     </tr>`
                );
            });
        }
    });
}

$(document).ready(function (){

    $.ajax({
        url: '/user',
        contentType: 'application/json',
        type: "GET",
        success: function (userAuthority) {
            window.currentUser = userAuthority;
            window.currentRoles = userAuthority.roles.length > 1 ? userAuthority.roles[0].name + ", " + userAuthority.roles[1].name : userAuthority.roles[0].name;
            $('#infoLogin').html(currentUser.username);
            $('#infoRole').html(currentRoles);
            if (currentRoles.includes('ROLE_ADMIN')){
                $('#adm').click();
                listUsers();
            }else {
                $('#adm').remove();
                $('#user').append(
                    `<tr id='${currentUser.id}'>
                            <td>${currentUser.id}</td>
                            <td>${currentUser.name}</td>
                            <td>${currentUser.username}</td>
                            <td>${currentUser.password}</td>
                            <td>${currentRoles}</td>
                            <td>                            
                     </tr>`
                );
                $('#usr').click();
            }
        }
    });



});
$(document).on('click', '#usr', function (event){
    event.preventDefault();
    // $('#user').empty();
    $('#user').empty().append(
        `<tr id='${currentUser.id}'>
                            <td>${currentUser.id}</td>
                            <td>${currentUser.name}</td>
                            <td>${currentUser.username}</td>
                            <td>${currentUser.password}</td>
                            <td>${currentRoles}</td>
                            <td>                            
                     </tr>`
    );
});
$(document).ready(function addUser() {
    $('#addForm').submit(function (e) {
        e.preventDefault();
        let user = {};
        let roles = [{}, {}];
        let i = 0;
        $(this).serializeArray().map(function (x) {
            if (x.name === 'roles') {
                if (x.value === "ROLE_ADMIN") {
                    roles[i]['id'] = 1;
                    roles[i]['name'] = x.value;
                }
                if (x.value === "ROLE_USER") {
                    roles[i]['id'] = 2;
                    roles[i]['name'] = x.value;
                }
                i++;
                return;
            }
            user[x.name] = x.value;
        });
        user['roles'] = roles;
        $.ajax({
            contentType: 'application/json',
            url: "/admin/saveUser",
            type: "POST",
            data: JSON.stringify(user),
            success: function () {
                $('#users').empty();
                listUsers();
                $('#addForm')[0].reset();

            }
        });
        $('#listTab').click();
    });
});

$(document).on('click', '#listTAb', function (event){
    event.preventDefault();
    $('#users').empty();
    listUsers();
});
$(document).on('click', '#editUser', function (e) {
    let id = parseInt(e.target.getAttribute('editID'), 10);
    $.ajax({
        contentType: 'application/json',
        url: '/admin/users',
        type: 'GET',
        success: function (users) {
            let user = users.find(el => el.id === id);
            document.querySelector('.modalForm').setAttribute('action', `/edit`);
            document.querySelector('.modalForm').setAttribute('id', `edit`);
            document.querySelector('#userId').setAttribute('value', user.id);
            document.querySelector('#nameEdit').setAttribute('value', user.name);
            document.querySelector('#emailEdit').setAttribute('value', user.username);
            document.querySelector('#passEdit').setAttribute('value', user.password);
            document.querySelector('#userId').setAttribute('readonly', 'true');
            document.querySelector('#nameEdit').removeAttribute('readonly');
            document.querySelector('#emailEdit').removeAttribute('readonly');
            document.querySelector('#passEdit').removeAttribute('readonly');
            document.querySelector('#roleEdit').removeAttribute('disabled');
            document.querySelector('#editLabel').innerHTML = 'Edit user';
            document.querySelector('#modalButton').setAttribute('class', 'btn btn-sm btn-primary');
            document.querySelector('#modalButton').innerHTML = 'Edit';

        }
    });
});
$(function () {
    $(document).on('submit', '#edit', function (event) {
        event.preventDefault();
        let user = {};
        let roles = [{}, {}];
        let i = 0;
        $(this).serializeArray().map(function (x) {
            if (x.name === 'roles') {
                if (x.value === "ROLE_ADMIN") {
                    roles[i]['id'] = 1;
                    roles[i]['name'] = x.value;
                }
                if (x.value === "ROLE_USER") {
                    roles[i]['id'] = 2;
                    roles[i]['name'] = x.value;
                }
                i++;
                return;
            }
            user[x.name] = x.value;
        });
        user['roles'] = roles;
        $.ajax({
            contentType: 'application/json',
            url: "/admin/edit",
            type: "PUT",
            data: JSON.stringify(user),
            success: function () {
                let roles = user.roles.length > 1 ? user.roles[0].name + ", " + user.roles[1].name : user.roles[0].name;
                $('#edit')[0].reset();
                $(".modal").modal("hide");
                // $('#myTableId tbody').empty();
                $('#users').empty();
                listUsers();

                // $(`#${user.id}`).html(
                //     `
                //     <tr id='${user.id}'>
                //     <td>${user.id}</td>
                //     <td>${user.name}</td>
                //     <td>${user.username}</td>
                //     <td>${user.password}</td>
                //     <td>${roles}</td>
                //     <td>
                //         <button id="editUser" editID="${user.id}" type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#modal">Edit</button>
                //     </td>
                //     <td>
                //         <button id="deleteUser" deleteID="${user.id}" type="button" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#modal">Delete</button>
                //     </td>
                //     </tr>`
                // );
            }
        });

    });
});
$(document).on('click', '#deleteUser', function (e) {
    let id = parseInt(e.target.getAttribute('deleteID'), 10);
    $.ajax({
        contentType: 'application/json',
        url: '/admin/users',
        type: 'GET',
        success: function (users) {
            let user = users.find(el => el.id === id);
            document.querySelector('.modalForm').setAttribute('action', `/delete/${user.id}`);
            document.querySelector('.modalForm').setAttribute('id', `delete`);
            document.querySelector('#userId').setAttribute('value', user.id);
            document.querySelector('#nameEdit').setAttribute('value', user.name);
            document.querySelector('#emailEdit').setAttribute('value', user.username);
            document.querySelector('#passEdit').setAttribute('value', user.password);
            document.querySelector('#userId').setAttribute('readonly', 'true');
            document.querySelector('#nameEdit').setAttribute('readonly', 'true');
            document.querySelector('#emailEdit').setAttribute('readonly', 'true');
            document.querySelector('#passEdit').setAttribute('readonly', 'true');
            document.querySelector('#roleEdit').removeAttribute('disabled');
            document.querySelector('#editLabel').innerHTML = 'Delete user';
            document.querySelector('#modalButton').setAttribute('class', 'btn btn-sm btn-danger');
            document.querySelector('#modalButton').innerHTML = 'Delete';

        }
    });
});
$(function () {
    $(document).on('submit', '#delete', function (e) {
        e.preventDefault();
        let user = {};
        let roles = [{}, {}];
        let i = 0;
        $(this).serializeArray().map(function (x) {
            if (x.name === 'roles') {
                if (x.value === "ROLE_ADMIN") {
                    roles[i]['id'] = 1;
                    roles[i]['name'] = x.value;
                }
                if (x.value === "ROLE_USER") {
                    roles[i]['id'] = 2;
                    roles[i]['name'] = x.value;
                }
                i++;
                return;
            }
            user[x.name] = x.value;
        });
        user['roles'] = roles;
        $.ajax({
            contentType: 'application/json',
            url: `/admin/delete/${user.id}`,
            type: "DELETE",
            success: function () {
                $('#delete')[0].reset();
                $(`#${user.id}`).remove();
                $(".modal").modal("hide");
            }
        });
    });
});






