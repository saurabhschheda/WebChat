// Current Room
var chatTo = null;
var currentUser = null;
// Map from Room to List of all Chats
var chat = {};
// Socket
var socket = undefined;

function connect() {
    socket = new WebSocket("ws://"+ location.host + location.pathname +"chat");

    //add the event listener for the socket object
    socket.onopen = socketOnOpen;
    socket.onmessage = socketOnMessage;
    socket.onclose = socketOnClose;
}

function disconnect() {
    //close the connection and the reset the socket object
    socket.close();
    socket = null;
}

function socketOnOpen(e) {
    console.log("WebSocket now Ready");
}

function socketOnMessage(e) {
    var eventName = e.data.substr(0, e.data.indexOf("|"));
    var data = e.data.substr(e.data.indexOf("|") + 1);
    var fn;
    switch (eventName) {
        case 'init':
            fn = initChat;
            break;
        case 'newRoom': 
            fn = newRoom;
            break;
        case 'message':
            fn = getMessage;
    }
    fn.apply(null, data.split('|'));
}

function socketOnClose(e) {
    // TODO: Check if this works.
    window.location.replace(location.host + location.pathname);
}

function initChat(user, rooms) {
    // Initialize currentUser with user
    // Initialize chat, chatTo
    // Create Rooms with rooms
}

function newRoom(id, name) {
    // Add to room list
    // Update chat
    // Add DOM Element to chatlist
}

function getMessage(sender, message, to) {
    // Update chat
    // Rerender chatTo's chat history
}

function sendMessage() {
    var messageBoardEl = document.querySelector('.chat-history');
    var messageInputEl = document.getElementById('message-to-send');
    var message = messageInputEl.value;
    if (message == '') return;
    socket.send(currentUser + '|' + message + '|' + chatTo);
    messageInputEl.value = '';
    messageBoardEl.scrollTop = messageBoardEl.scrollHeight;
}

function addTeam() {
    // TODO: Figure out UX flow for this 
    // Send new room details to socket
}

document.onload(function() {
    connect();
});