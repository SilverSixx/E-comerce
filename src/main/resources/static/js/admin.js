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
$(document).ready(function() {
    let id;
    let productAttr;
    $('table')
        .on('mouseenter', 'td:not(:first-child):not(:last-child):not(:nth-child(5))', function() {
        let index = $(this).index()
        productAttr = $(this).closest('table').find('thead th').eq(index).text()
        id = $(this).closest('tr').find('td:first-child').text()
        $(this).append('<i class="fas fa-pen edittip"></i>');
    })
        .on('mouseleave', 'td:not(:first-child):not(:last-child)', function() {
        $(this).find('.edittip').remove();
    })
        .on('click', 'td:not(:first-child):not(:last-child)', function(event) {
        let $td = $(this);
            if (event.target !== this) {
                return;
            }
        let originalContent = $td.text();
        $td.empty()
        $('<input>').attr({
            type: 'text',
            value: originalContent
        }).appendTo($td).focus();
    })
        .on('keypress', 'input', function(event) {
        let $input = $(this);
        if (event.which === 13) { // Enter key pressed
            let newContent = $input.val();
            let $td = $input.closest('td');
            $td.text(newContent);
            // TODO: Update the change in the database
            $.ajax({
                type: 'PUT',
                url: 'http://localhost:8090/api/v1/products/edit/' + id + '?' + productAttr + '=' + newContent,
                contentType: 'application/json',
                success: function(){
                    alert('update successfully')
                },
                error: function () {
                    alert('update failed')
                }
            })
        }
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
            tr.append($('<td>').append(getImgUrl(p.image)));
            tr.append($('<td>').text(p.price));
            tr.append($('<td>').text(p.inStock))
            tr.append($('<td>')
                .html('<button class="deleteInTable" onclick="deleteProduct('+p.id+')">Delete</button>'))
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
function getImgUrl(img){
    const byteCharacters = atob(img); // Convert base64 string to byte array
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], {type: 'image/*'});
    const imgUrl = URL.createObjectURL(blob);
    const imgElement = document.createElement('img');
    imgElement.src = imgUrl;
    return imgElement;
}