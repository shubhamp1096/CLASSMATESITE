$(document).ready(function() {
    $('.anim').hover(function(){
        $(this).stop().animate({
            'background-position-y': '-6.863636363636363em'
        }, 65, 'linear');
    }, function(){
        $(this).stop().animate({
            'background-position-y': '0'
        });
    });
});