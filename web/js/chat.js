// Current Room
var chatTo = null;
var currentUser = null;
// Map from Room to List of all Chats
var chat = {};
// Socket
var socket = undefined;

function connect() {
    socket = new WebSocket("ws://"+ location.host + "/WebChat/chat");

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
    socket.send("newUser|" + currentUser);
}

function socketOnMessage(e) {
    var eventName = e.data.substr(0, e.data.indexOf("|"));
    var data = e.data.substr(e.data.indexOf("|") + 1);
    switch (eventName) {
        case 'init':
            initChat(data);
            break;
        case 'newRoom': 
            newRoom(data);
            break;
        case 'message':
            getMessage(data);
    }
}

function socketOnClose(e) {
    console.log(e);
    window.location.replace(location.host + "/WebChat/fail.jsp");
}

function createBasicElement(tag, className, inner) {
    var el = document.createElement(tag);
    el.classList.add(className);
    el.innerHTML = inner;
    return el;
}

function setActive(room) {
    var activeRoom = document.querySelector(".active");
    if (activeRoom != null) {
        activeRoom.classList.remove("active");
    }
    document.getElementById(chatTo).parentElement.parentElement.classList.add("active");
}

function createRoomDOM(room) {
    var roomNameEl = createBasicElement("div", "name", room);
    roomNameEl.setAttribute("id", room);
    var aboutEl = createBasicElement("div", "about", roomNameEl.outerHTML);
    var listEl = document.querySelector('.list');
    listEl.appendChild(createBasicElement("li", "clearfix", aboutEl.outerHTML));
}

function initChat(data) {
    console.log(data);
    var roomNames = data.split('|');
    roomNames.map(function(room) {
        chat.room = [];
        createRoomDOM(room);
    });
    chatTo = roomNames[0];
    setActive(chatTo);
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

currentUser = document.querySelector(".search").innerHTML;
connect();