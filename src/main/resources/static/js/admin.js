
const customerControlOpt = document.querySelector('.customer-control-opt');
const customerControlContainer = document.querySelector('.customer-control-container');
customerControlOpt.addEventListener('click', () => {
    customerControlContainer.style.display = customerControlContainer.style.display === 'none' ? 'block' : 'none';
})

const productControlOpt = document.querySelector('.product-control-opt')
const productControlContainer = document.querySelector('.product-control-container')

productControlOpt.addEventListener('click', () => {
    productControlContainer.style.display = productControlContainer.style.display === 'none' ? 'block' : 'none'
})

const delBtn = document.querySelector('#delCustomer');
const delCustContainer = document.querySelector('.delCustomer-container');
delBtn.addEventListener('click', () => {
    delCustContainer.style.display = delCustContainer.style.display === 'none' ? 'block' : 'none';
})

$(function() {
    setTimeout(function () {
        $('.loader_bg').fadeOut();
    }, 1000);

});
$(document).ready(function(){
    $('#showCustomer').click(function(){
        showCustomer()
    });
    $('#del-btn').click(function(event){
        event.preventDefault();
        let id = $('#id-to-del').val()
        $.ajax({
            type: 'DELETE',
            url: 'http://localhost:8090/api/v1/customers/delete/'+ id,
            contentType: 'application/json',
            success: function(response) {
                alert(response)
                showCustomer()
            },
            error: function(xhr) {
                alert(xhr.responseText)
            }
        })
    })


    $('.upload-pc-form').submit(function (event){
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
            url: 'http://localhost:8090/api/v1/pcs/upload',
            data: formData,
            contentType: false,
            processData: false,
            success: function() {
                alert("successful")
            },
            error: function (){
                alert('unsuccessful')
            }
        })

    })


});

function showCustomer(){
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
            tbody.append(tr);
        });

    })
    .fail(function(textStatus){
        alert('Error fetching users: ' + textStatus);
    });
}
