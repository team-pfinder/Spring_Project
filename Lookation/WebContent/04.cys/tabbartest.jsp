<!doctype html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 4</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <style>
      p { margin:20px 0px; }
    </style>
  </head>
  <body>
<h3>goo.gl/iLQgH2</h3>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="../css/css/bootstrap-4-floating-labels-material-inputs/input-material.css">
<script src="../css/css/bootstrap-4-floating-labels-material-inputs/materialize-inputs.jquery.js"></script>

<script type="text/javascript">

$('document').ready(function(){
 
  // enable material-style inputs in entire body
  $('body').materializeInputs();
});


</script>

<form id="contact-form">
  <div class="form-group input-material">
      <input type="text" class="form-control" id="name-field" required>
      <label for="name-field">Name</label>
  </div>
  <div class="form-group input-material">
      <input type="email" class="form-control" id="email-field" required>
      <label for="email-field">Email</label>
  </div>
  <div class="form-group input-material">
      <textarea class="form-control" id="textarea-field" rows="3" required></textarea>
      <label for="textarea-field">Your Message</label>
  </div>
  <div class="text-center">
      <button type="submit" class="btn btn-primary">Send</button>
  </div>
</form>
</html>