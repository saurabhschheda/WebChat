// DOM Elements
var disconnectBtnEl = document.getElementById('logout');
var usernameListEl = document.querySelector("#userList");
var articleEl = document.querySelector('article');
var messageBoardEl = articleEl.querySelector('#message-board');
var messageInputEl = articleEl.querySelector('#message');
var sendBtnEl = articleEl.querySelector('#send');
//label in the message board title
var chatToEl = articleEl.querySelector('#chatTo'); 
// All btn, to chat to all people in the room
var chatToAllEl = document.querySelector('#all');

// current chat destination
var chatTo = 'all'; 

//Chat room that holds every conversation
var chatRoom = {
    'all': []
};

//socket object.
var socket = undefined;

connectBtnEl.onclick = connect;
disconnectBtnEl.onclick = disconnect;

function connect() {
    //ws is a websocket protocol
    //location.host + location.pathname is the current url
    //new WebSocket(url) will immediately open a websocket connection
    socket = new WebSocket("ws://"+ location.host + location.pathname +"chat?username=" + usernameInputEl.value);

    //add the event listener for the socket object
    socket.onopen = socketOnOpen;
    socket.onmessage = socketOnMessage;
    socket.onclose = socketOnClose;
}

function disconnect() {
    //close the connection and the reset the socket object
    socket.close();
    socket = undefined;
}

function socketOnOpen(e) {
    usernameInputEl.disabled = true;
    connectBtnEl.disabled = true;
    disconnectBtnEl.disabled = false;
}

function socketOnMessage(e) {
    var eventName = e.data.substr(0, e.data.indexOf("|"));
    var data = e.data.substr(e.data.indexOf("|") + 1);

    var fn;
    if(eventName == 'newUser') fn = newUser;
    else if(eventName == 'removeUser') fn = removeUser;
    else if(eventName == 'message') fn = getMessage;

    fn.apply(null, data.split('|'));
}

function socketOnClose(e) {
    usernameInputEl.disabled = false;
    connectBtnEl.disabled = false;
    disconnectBtnEl.disabled = true;
    usernameInputEl.value = '';
    messageBoardEl.innerHTML = '';
    chatToEl.innerHTML = 'All';
    usernameListEl.innerHTML = '';
}

function newUser() {
    //if there is no users, return from the function
    if(arguments.length == 1 && arguments[0] == "") return;
    var usernameList = arguments;

    //Loop through all online users and create a list from it
    var documentFragment = document.createDocumentFragment();
    for(var i = 0; i < usernameList.length; i++) {
        var username = usernameList[i];
        var liEl = document.createElement("li");
        liEl.id = username;
        liEl.textContent = username;
        liEl.onclick = chatToFn(username);
        if(username != usernameInputEl.value) liEl.classList.add('hoverable');
        documentFragment.appendChild(liEl);
    };
    usernameListEl.appendChild(documentFragment);
}

function getMessage(sender, message, to) {
        to = to || sender;

        if(chatTo == to) {
            var newChatEl = createNewChat(sender, message);
            messageBoardEl.appendChild(newChatEl);
        } else {
            var toEl = usernameListEl.querySelector('#' + to);
            addCountMessage(toEl);
        }

        if(chatRoom[to]) chatRoom[to].push(newChatEl);
        else chatRoom[to] = [newChatEl];
}

function removeUser(removedUsername) {
    var usernameList = usernameListEl.children;
    for(var i = 0; i < usernameList.length; i++) {
        var username = usernameList[i].textContent;
        if(username == removedUsername) {
            usernameListEl.removeChild(usernameList[i]);
        }
    }
}

function createNewChat(sender, message) {
    var newChatDivEl = document.createElement('div');
    var senderEl = document.createElement('span');
    var messageEl = document.createElement('span');

    if(sender == usernameInputEl.value)
        sender = 'me';

    senderEl.textContent = sender;
    messageEl.textContent = message;

    newChatDivEl.appendChild(senderEl);
    newChatDivEl.appendChild(messageEl);
    return newChatDivEl;
}

function addCountMessage(toEl) {
    var countEl = toEl.querySelector('.count');
    if(countEl) {
        var count = countEl.textContent;
        count = +count;
        countEl.textContent = count + 1;
    } else {
        var countEl = document.createElement('span');
        countEl.classList.add('count');
        countEl.textContent = '1';
        toEl.appendChild(countEl);
    }
}

sendBtnEl.onclick = sendMessage;
chatToAllEl.onclick = chatToFn('all');

function sendMessage() {
    var message = messageInputEl.value;
    if(message == '') return;
    socket.send(chatTo + '|' + message );
    messageInputEl.value = '';

    var sender = usernameInputEl.value;
    getMessage(sender, message, chatTo);
    messageBoardEl.scrollTop = messageBoardEl.scrollHeight;
}

function chatToFn(username) {
    return function(e) {
        if(username == usernameInputEl.value) return;

        var countEl = usernameListEl.querySelector('#' + username + '>.count');
        if(countEl) {
            countEl.remove();
        }

        chatToEl.textContent = username;
        chatTo = username;
        messageBoardEl.innerHTML = '';

        var conversationList = chatRoom[chatTo];
        if(!conversationList) return;
        var df = document.createDocumentFragment();
        conversationList.forEach(function (conversation) {
            df.appendChild(conversation);
        });
        messageBoardEl.appendChild(df);
    }
}