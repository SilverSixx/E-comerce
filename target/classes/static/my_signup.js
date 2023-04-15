$(document).ready(function() {
    $('#submit-btn').click(function (event){
        event.preventDefault();
        let data = {
            firstName: $('#fname').val(),
            lastName: $('#lname').val(),
            email: $('#email').val(),
            password: $('#password').val()
        }
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8090/api/v1/registration',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function(){
                showSuccess("Signup successful!")
            },
            error: function () {
                showFailure("Signup failed. Please try again.")
            }
        })
    })
})
// get the alert box element
var alertBoxSuccess = document.getElementById("alert-box-success");
var alertBoxFail = document.getElementById("alert-box-fail");

// get the alert message element
var successMessage = document.getElementById("success-message");
var failMessage = document.getElementById("fail-message");

// get the alert button element
var successButton = document.getElementById("success-button");
var failButton = document.getElementById("fail-button");

function showSuccess(message) {
    successMessage.innerHTML = message;
    alertBoxSuccess.style.display = "block";
}

successButton.addEventListener("click", function() {
    window.open('/ECommerce/src/main/resources/static/my_login.html')
    alertBoxSuccess.style.display = "none";
});

function showFailure(message) {
    failMessage.innerHTML = message;
    alertBoxFail.style.display = "block";
}

failButton.addEventListener("click", function() {
    alertBoxFail.style.display = "none";
});