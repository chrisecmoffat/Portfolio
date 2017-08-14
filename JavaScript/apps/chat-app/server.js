var mongoose  = require('mongoose');
var express   = require('express');
var http      = require('http');
var app       = express();
var server    = http.Server(app);

app.use(express.static('client'));
mongoose.connect('mongodb://localhost/chatdb');

var chatSchema = new mongoose.Schema({  
  message: String
});

var io            = require('socket.io')(server);
var Message       = mongoose.model("Message", chatSchema);
var databaseArray = ["","","","",""];

io.on('connection', function (socket) {
  Message.find({}).sort({'_id': 1}).exec(function(err, allMessages) {
    if (err) {
      console.log("DATABASE READ ERROR");
      console.log(err);
    } else {
      for (var i = 0; i < databaseArray.length; i++) {
        socket.emit('history', allMessages[i].message);
        databaseArray[i] = allMessages[i].message
      }
    }
  });

  socket.on('message', function (msg) {
    io.emit('message', msg);
    databaseArray.push(msg);
    databaseArray.shift();
  
    Message.remove({}, function(err) {
      if(err){
        console.log("DATABASE REMOVAL ERROR");
        console.log(err);
      }
    });
  
    for (var i = 0; i < databaseArray.length; i++) {
      Message.create({message: databaseArray[i]}, function(err, chatmessage){
        if(err){
          console.log("DATABASE INSERT ERROR");
          console.log(err);
        }
      });
    }
  });
});

server.listen(8080, function() {
  console.log('CHAT SERVER STARTED!!!');
});
