<!DOCTYPE html>
<html>
    <head>
        <title>WebChat</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="./css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
      <div class="pen-title">
        <h1>Error</h1>
      </div>
      <div class="container">
        <div class="card"></div>
        <div class="card">
          <h1 class="title"><%=(String)request.getAttribute("error")%> :(</h1>
          <div class="button-container">
            <a href="index.jsp">
              <button>
                <span>Return to Login Page</span>
              </button>
            </a>
          </div>
        </div>
      </div>
    </body>
</html>
