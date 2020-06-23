// Current Room
let chatTo = null;
let currentUser = null;
// Map from Room to List of all Chats
let chat = {};
// Socket
let socket = undefined;

function connect() {
    socket = new WebSocket("ws://"+ location.host + "/WebChat/chat");

    //add the event listener for the socket object
    socket.onopen = socketOnOpen;
    socket.onmessage = socketOnMessage;
    socket.onclose = socketOnClose;
}

function disconnect() {
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
        case 'message':
            getMessage(data);
    }
}

function socketOnClose(e) {
    console.log(e);
    window.location.href = "http://" + location.host + "/WebChat";
}

function createBasicElement(tag, className, inner) {
    var el = document.createElement(tag);
    el.classList.add(className);
    el.innerHTML = inner;
    return el;
}

function setActive(room) {
    if (chatTo != room) {
        var activeRoom = document.querySelector(".active");
        if (activeRoom != null) {
            activeRoom.classList.remove("active");
        }
        document.getElementById(room).parentElement.parentElement.classList.add("active");
        chatTo = room;
        renderChatHistory(chatTo);
        inputField.focus();
    }
}

function createRoomDOM(room) {
    var roomNameEl = createBasicElement("div", "name", room);
    roomNameEl.setAttribute("id", room);
    var aboutEl = createBasicElement("div", "about", roomNameEl.outerHTML);
    var listEl = createBasicElement("li", "clearfix", aboutEl.outerHTML);
    listEl.setAttribute("onclick", "setActive('" + room + "')");
    var list = document.querySelector('.team-list');
    list.appendChild(listEl);
}

function initChat(data) {
    var roomNames = data.split('|');
    roomNames.map(function(room) {
        chat[room] = [];
        createRoomDOM(room);
    });
    setActive(roomNames[0]);
}

function renderChatBubble(sender, message) {
    var senderSpan = createBasicElement("span", "message-data-name", sender);
    var senderDiv = createBasicElement("div", "message-data", senderSpan.outerHTML);
    var messageDiv = createBasicElement("div", "message", message);
    if (sender === currentUser) {
        senderDiv.classList.add("align-right");
        messageDiv.classList.add("other-message");
        messageDiv.classList.add("float-right");
    } else {
        messageDiv.classList.add("my-message");
    }
    var bubble = createBasicElement("li", "clearfix", "");
    bubble.appendChild(senderDiv);
    bubble.appendChild(messageDiv);
    return bubble;
}

function renderChatHistory() {
    var chatHistoryEl = document.getElementById('history');
    chatHistoryEl.innerHTML = "";
    for (const chatData of chat[chatTo]) {
            var bubble = renderChatBubble(chatData.sender, chatData.message);
            chatHistoryEl.appendChild(bubble);
    }
    chatHistoryEl.scrollTop = chatHistoryEl.scrollHeight;
}

function getMessage(data) {
    data = data.split("|");
    var sender = data[0];
    var message = data[1];
    var receiver = data[2];
    chat[receiver].push({
        "sender": sender,
        "message": message
    });
    renderChatHistory();
}

function sendMessage() {
    console.log("sending message ...");
    var messageInputEl = document.getElementById('toSend');
    var message = messageInputEl.value;
    if (message === '') return;
    socket.send('chat|' + currentUser + '|' + message + '|' + chatTo);
    messageInputEl.value = '';
    console.log(message);
}

const inputField = document.getElementById("toSend");
inputField.focus();

inputField.addEventListener("keyup", function (e) {
    if (e.keyCode === 13) {
        sendMessage();
        inputField.focus();
    }
});



currentUser = document.querySelector(".username").innerHTML;
connect();