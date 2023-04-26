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
    $('table')
        .on('mouseenter', 'td', function() {
        let $td = $(this);
        let tooltipText = $td.data('tooltip');
        $td.append('<div class="tooltip" style="max-width: 50%;margin-left: 25%;background-color:#ccc"> <i class="fas fa-pen"></i> ' + tooltipText + '</div>'); // create a new div with the tooltip text and append it to the td
    })
        .on('mouseleave', 'td', function() {
        let $td = $(this);
        $td.find('.tooltip').remove(); // remove the tooltip div when mouse leaves the td
    })
        .on('click', 'td', function(event) {
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
            tr.append($('<td data-tooltip="Edit">').text(p.id));
            tr.append($('<td data-tooltip="Edit">').text(p.name));
            tr.append($('<td data-tooltip="Edit">').text(p.manufacturer));
            tr.append($('<td data-tooltip="Edit">').text(p.description));
            tr.append($('<td>').append(getImgUrl(p.image)));
            tr.append($('<td data-tooltip="Edit">').text(p.price));
            tr.append($('<td data-tooltip="Edit">').text(p.inStock))
            tr.append($('<td>')
                .html('<button data-tooltip="Delete" class="deleteInTable" onclick="deleteProduct('+p.id+')">Delete</button>'))
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