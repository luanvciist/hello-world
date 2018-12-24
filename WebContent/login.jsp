<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
	<link rel="stylesheet" type="text/css" href="./css/login.css">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<!------ Include the above in your HEAD tag ---------->
  </head>
<body id="LoginForm">
<div class="container">
<h1 class="form-heading">Login Form</h1>
<div class="login-form">
<div class="main-div">
    <div class="panel">
   <h2>Admin Login</h2>
   <p>Please enter your username and password</p>
   </div>
	    <s:form action="doLogin" id="Login">
	        <div class="form-group">
	            <input type="text" class="form-control" name="username" placeholder="User name" value="${username}" autofocus>
	        </div>
	        <div class="form-group">
	            <input type="password" class="form-control" name="password" placeholder="Password">
	        </div>
	        <button type="submit" class="btn btn-primary">Login</button>
	        <c:if test="${errorMessage != null}">
		        <div class="form-group">
		        	<br/>
		            <p style="color: red"><s:property value="errorMessage" escapeHtml="false"/></p>
		        </div>
	        </c:if>
	    </s:form>
    </div>
<!-- <p class="botto-text"> Designed by Sunil Rajput</p> -->
</div></div>
</body>
</html>
