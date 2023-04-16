$(function() {
    setTimeout(function () {
        $('.loader_bg').fadeOut();
    }, 1500);

});

$(document).ready(function() {

    $('.sign-in').click(function(event){
        event.preventDefault();
        window.open('my_login.html', '_self')
    })
})
const aboutSection = document.querySelector('#about');
const aboutLink = document.querySelector('a[href="#about"]');

aboutLink.addEventListener('click', (event) => {
    event.preventDefault();
    aboutSection.scrollIntoView({ behavior: 'smooth' });
});
