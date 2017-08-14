var socket = io();
var count = 0;

$('form').on('submit',function () {
    if ($('#initials').val() === '')
        alert("Please enter a name");
    else if ($('#message').val() != '') {
        var text = $('#initials').val() + ": " + $('#message').val();
        socket.emit('message', text);
        $('#message').val('');    
    }
    return false;
});

socket.on('history', function (msg) {
    if (count < 5) 
        $('#history').append($('<li><i class="fa fa-comments-o" aria-hidden="true"></i>').append(" " + msg).append('</li>'));
    count++;
});

socket.on('message', function (msg) {
    // var d = new Date();
    // var h = addZero(d.getHours());
    // var m = addZero(d.getMinutes());
    // var s = addZero(d.getSeconds());
    //$('#history').append($('<li><i class="fa fa-commenting-o" aria-hidden="true"></i>').append(" [" + h + ":" + m + ":" + s + "] " +  msg).append('</li>'));
    
    $('#history').append($('<li><i class="fa fa-commenting-o" aria-hidden="true"></i>').append(" " +  msg).append('</li>'));
    $('ol#history li:first-child').remove();
});

function addZero(number) {
    var num;
    if (number < 10)
        num = "0" + number;
    return num;
}
