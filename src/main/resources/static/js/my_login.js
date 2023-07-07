$(function() {
    // Move the fade-out function to a variable to store the reference to the fade animation
    const fadeOutAnimation = function () {
        $('.loader_bg').fadeOut();
    };

    // Call the fadeOutAnimation function after a delay
    setTimeout(fadeOutAnimation, 1500);

    $('#login-btn').click(function (event) {
        event.preventDefault();
        let requestBody = {
            username: $('#username').val(),
            password: $('#password').val()
        };
        fetch('http://localhost:8080/api/login', {
            method: 'POST',
            mode: 'no-cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                window.open("index.html", '_self')
            })
            .catch(error => {
                console.error(error)
            })
    });


    $('#to-signup-btn').click(function (event) {
        event.preventDefault();
        window.open('my_signup.html', '_self');
    });
});

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

