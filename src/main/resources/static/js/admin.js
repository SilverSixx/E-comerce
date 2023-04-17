$(document).ready(function(){
    $('#addUser').click(function(){
        $('#addUserModal').show();
    });
    $('#showUsers').click(function(){
        showUsers();
    });

});

function showUsers(){
    $.get('http://localhost:8090/api/v1/users', function(data){
        let tbody = $('#userList tbody');
        tbody.empty();
        data.forEach(function(customer){
            let tr = $('<tr>');
            tr.append($('<td>').text(customer.id));
            tr.append($('<td>').text(customer.firstName));
            tr.append($('<td>').text(customer.lastName));
            tr.append($('<td>').text(customer.email));
            tr.append($('<td>').text(customer.password))
            tr.append($('<td>').text(customer.customerRole));
            tbody.append(tr);
        });

    })
        .fail(function(textStatus){
            alert('Error fetching users: ' + textStatus);
        });
}
