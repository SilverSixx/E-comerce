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
                window.open(response.text(), '_self')
            },
            error: function(xhr) {
                alert(xhr.responseText)
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

