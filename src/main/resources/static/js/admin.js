$('.customer-control-opt').click(function () {
    $('.customer-control-container').toggle();
});
$('.product-control-opt').click(function () {
    $('.product-control-container').toggle();
});
$('#product-btn').click(function () {
    $('.upload-product-form').toggle();
});

$(function() {
    setTimeout(function () {
        $('.loader_bg').fadeOut();
    }, 1000);
});
$(document).ready(function(){
    $('#showCustomers').click(function(){
        showCustomers()
    });
    $('#showProducts').click(function(){
        showProducts()
    })
    $('.upload-product-form').submit(function (event){
        event.preventDefault();
        const formData = new FormData();
        formData.append('name', $('#name').val())
        formData.append('manufacturer', $('#manufacturer').val())
        formData.append('description', $('#description').val())
        formData.append('image', $('#image')[0].files[0])
        formData.append('price', $('#price').val())
        formData.append('inStock', $('#in-stock').val())
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8090/api/v1/products/upload',
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("successful")
                showProducts()
            },
            error: function (){
                alert('unsuccessful')
            }
        })
    })
});

function showCustomers(){
    $.get('http://localhost:8090/api/v1/customers', function(data){
        let tbody = $('.customerList tbody');
        tbody.empty();
        data.forEach(function(customer){
            let tr = $('<tr>');
            tr.append($('<td>').text(customer.id));
            tr.append($('<td>').text(customer.firstName));
            tr.append($('<td>').text(customer.lastName));
            tr.append($('<td>').text(customer.email));
            tr.append($('<td>').text(customer.password))
            tr.append($('<td>').text(customer.customerRole));
            tr.append($('<td>').html('<button class="deleteInTable" onclick="deleteCustomer('+customer.id+')">Delete</button>'))
            tbody.append(tr);
        });
    })
    .fail(function(textStatus){
        alert('Error fetching users: ' + textStatus);
    });
}
function showProducts(){
    $.get('http://localhost:8090/api/v1/products', function(data){
        let tbody = $('.product-list tbody');
        tbody.empty();
        data.forEach(function(p){
            let tr = $('<tr>');
            tr.append($('<td>').text(p.id));
            tr.append($('<td>').text(p.name));
            tr.append($('<td>').text(p.manufacturer));
            tr.append($('<td>').text(p.description));
            const byteCharacters = atob(p.image); // Convert base64 string to byte array
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            const blob = new Blob([byteArray], {type: 'image/*'});
            const imgUrl = URL.createObjectURL(blob);
            const imgElement = document.createElement('img');
            imgElement.src = imgUrl;
            tr.append($('<td>').append(imgElement))
            tr.append($('<td>').text(p.price));
            tr.append($('<td>').text(p.inStock))
            tr.append($('<td>')
                .html('<button id="editProduct" onclick="editProduct('+p.id+')">Edit</button> ' + '<button class="deleteInTable" onclick="deleteProduct('+p.id+')">Delete</button>'))
            tbody.append(tr);
        });

    })
    .fail(function(textStatus){
        alert('Error fetching products: ' + textStatus);
    });
}
function deleteCustomer(id){
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8090/api/v1/customers/delete/'+ id,
        contentType: 'application/json',
        success: function() {
            alert('delete customer successfully')
            showCustomers()
        },
        error: function(xhr) {
            alert(xhr.responseText)
        }
    })
}
function deleteProduct(id) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8090/api/v1/products/delete/' + id,
        contentType: 'application/json',
        success: function () {
            alert('delete product successfully')
            showProducts()
        },
        error: function (xhr) {
            alert(xhr.responseText)
        }
    })
}

function editProduct(id){

}