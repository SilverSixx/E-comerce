$(document).ready(function() {
    $('#login-btn').click(function (event){
        event.preventDefault();
        let username = $('#username').val()
        let password = $('#password').val()
        const url = `?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`

        $.ajax({
            type: "GET",
            url: 'http://localhost:8090/api/v1/login'+ url,
            contentType: 'application/json',
            success: function(response) {
                window.open(response.toString(), '_self')
            },
            error: function(xhr) {
                showMessage(xhr.responseText)
            }
        })
    })

    $('#to-signup-btn').click(function(event){
        event.preventDefault();
        window.open('my_signup.html', '_self')
    })
})

$(function() {
    setTimeout(function () {
        $('.loader_bg').fadeOut();
    }, 1500);
});

const alertBox = document.querySelector('#alert-box')
const alertMessage = document.querySelector('#message')
const alertBtn = document.querySelector('#alert-btn')

function showMessage(message) {
    alertMessage.innerHTML = message
    alertBox.style.display = 'block'
}

alertBtn.addEventListener('click',function (event){
    event.preventDefault()
    alertBox.style.display = 'none'
})

