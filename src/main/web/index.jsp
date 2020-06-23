<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>WebChat</title>
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
  <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>

<div class="pen-title">
  <h1>Web Chat</h1>
</div>

<div class="container">

  <!-- Login Card -->
  <div class="card">
    <h1 class="title">Login</h1>
    <form action="LoginServlet" method="POST">

      <div class="input-container">
        <input type="text" id="username" name="username" required="required" autocomplete="false" />
        <label for="username">Username</label>
        <div class="bar"></div>
      </div>

      <div class="input-container">
        <input type="password" id="password" name="password" required="required" />
        <label for="password">Password</label>
        <div class="bar"></div>
      </div>

      <div class="button-container">
        <button><span>Go</span></button>
      </div>

    </form>
  </div>
  <!-- End of Login Card -->

  <!-- Register Card -->
  <div class="card alt">

    <div class="toggle"></div>

    <h1 class="title">Register
      <div class="close"></div>
    </h1>

    <form action="RegisterServlet" method="POST">

      <div class="input-container">
        <input type="text" id="username" name="username" required="required" autocomplete="false" />
        <label for="username">Username</label>
        <div class="bar"></div>
      </div>

      <div class="input-container">
        <input type="password" id="password" name="parssword" required="required" />
        <label for="password">Password</label>
        <div class="bar"></div>
      </div>

      <div class="input-container">
        <input type="text" id="team" name="team" required="required" autocomplete="false" />
        <label for="team">Team Name</label>
        <div class="bar"></div>
      </div>

      <div class="button-container">
        <button><span>Next</span></button>
      </div>

    </form>

  </div>
  <!-- End of Register Card -->

</div>

<script src="js/login.js"></script>
</body>
</html>
