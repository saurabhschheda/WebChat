<!DOCTYPE html>
<html>
    <head>
        <title>WebChat</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/fontello.css">
        <link rel="stylesheet" type="text/css" href="css/chat.css">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container clearfix">
            <div class="people-list" id="people-list">
                <div class="username"><%=(String)request.getAttribute("username")%></div>
                <ul class="team-list"></ul>
                <div class="button-bottom">
                </div>
            </div>
            <div class="chat">
                <div class="chat-history">
                    <ul id="history"></ul>
                </div>
                <div class="chat-message">
                    <input name="toSend" id="toSend" placeholder="Type your message">
                    <i id="send" onclick="sendMessage()" class="icon-paper-plane"></i>
                </div>
            </div>
        </div>
        <div class="logout" id="logout" onclick="disconnect()">
            <i class="icon-off"></i>
        </div>
        <script type="text/javascript" src="js/chat.js"></script>
    </body>
</html>
