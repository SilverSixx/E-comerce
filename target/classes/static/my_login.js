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
            success: function() {
                window.open('/ECommerce/src/main/resources/static/index.html')
            },
            error: function(xhr) {
                showAlert(xhr.responseText);
            }
        })
    })

    let alertBox = $("#alert-box");
    let alertMessage = $("#alert-message");
    let alertButton = $("#alert-button");
    function showAlert(message) {
        alertMessage.innerHTML = message;
        alertBox.style.display = "block";
    }
    alertButton.click(function() {
        alertBox.style.display = "none";
    });

    $('#to-signup-btn').click(function(){
        // location.href = "http://localhost:63342/ECommerce/static/my_signup.html"
        window.open('/ECommerce/src/main/resources/static/my_signup.html', '_self')
    })

})
