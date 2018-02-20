<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"></link>
<script src="http://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<link href="/css/floating-labels.css" rel="stylesheet" />
</head>
<body class="login-body">
	<form class="form-signin" method="post" action="/signup" name="signup-form" id="signup-form">
      <div class="text-center mb-4">
        <img class="mb-4" src="/images/nb_icon.png" alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal">Signup Page</h1>
      </div>
	 
	  <div class="form-label-group">
        <input type="text" name="fullname" id="fullname" class="form-control" placeholder="Full Name" required autofocus>
        <label for="fullname">Full Name</label>
      </div>
      
      <div class="form-label-group">
        <input type="text" name="username" id="username" class="form-control" placeholder="User Name" required autofocus>
        <label for="username">User Name</label>
      </div>

      <div class="form-label-group">
        <input type="password" name="password" id="password" class="form-control" placeholder="Password" required>
        <label for="password">Password</label>
      </div>
      
      <div class="form-label-group">
        <input type="password" name="cnfPassword" id="cnfPassword" class="form-control" placeholder="Confirm Password" required>
        <label for="cnfPassword">Confirm Password</label>
      </div>
      
      <div class="form-label-group">
      	<select name="roles" id="roles" class="form-control" required>
      		<option value="MEMBER">Member</option>
      		<option value="ADMIN">Admin</option>
      	</select>
      </div>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      <a class="btn btn-lg btn-primary btn-block" href="/">Sign in</a>
      <p class="mt-5 mb-3 text-muted text-center">&copy; 2017-2018</p>
    </form>
</body>
</html>