<!DOCTYPE html>
<html>
    <head>
        <title>WebChat</title>
        <link rel="stylesheet" type="text/css" href="css/chat.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>   
    <body>
        <div class="container clearfix">
            <div class="people-list" id="people-list">
                <div class="search"><%=(String)request.getAttribute("username")%></div>
                <ul class="list"></ul>
                <ul class="list-bottom">
                    <li class="clearfix" id="logout" onclick="disconnect()">
                        <div class="about">
                            <div class="name"><i class="fa fa-power-off"></i> Logout</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="chat">                
                <div class="chat-history">
                    <ul id="history"></ul>
                </div>
                <div class="chat-message clearfix">
                    <textarea name="toSend" id="toSend" placeholder="Type your message" rows="1"></textarea>
                    <button id="send" onclick="sendMessage()">Send <i class="fa fa-paper-plane"></i></button>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="js/chat.js"></script>
    </body>
</html>
