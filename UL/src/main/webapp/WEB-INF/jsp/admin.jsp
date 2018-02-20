<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%-- <h2>${sessionScope.loginUser}</h2>
<h2>${sessionScope.loginStatus}</h2>
<h2>${sessionScope.roles}</h2> --%>
<c:choose>
<c:when test="${sessionScope.loginUser != '' && sessionScope.loginStatus == 'success' && sessionScope.roles == 'ADMIN'}">
	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Welcome <c:out
					value="${loginUser}"></c:out>
			</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="/home">Home</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<!-- <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li> -->
			<li><a href="/logout"><span
					class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		</ul>
	</div>
	</nav>

	<div class="container">
		<div id="user-container">
			<table class="table">
			<thead>
				<tr>
					<th>User Name</th>
					<th>Full Name</th>
					<th>Roles</th>
					<th>Manage</th>
				</tr>
			</thead>
			<tbody id="user-table">
				<%-- <c:forEach var="user" items="${users}">
					<tr>
						<td>${user.id}</td>
						<td>${user.username}</td>
						<td>${user.fullname}</td>
						<td>${user.roles}</td>
						<td><a href="#" data-id="${user.id}"
							data-fullname="${user.fullname}" data-username="${user.username}"
							data-roles="${user.roles}" onclick="return editUser(this)">Edit</a> <a href="#"
							data-id="${user.id}" data-fullname="${user.fullname}"
							onclick="return deleteUser(this)">Delete</a></td>
					</tr>
				</c:forEach> --%>

			</tbody>
		</table>
		</div>

		<!-- Edit Modal -->
		<div class="modal fade" id="edit-modal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit User</h4>
					</div>
					<div class="modal-body">
						<form action="/edit" method="post" id="edit-form" name="edit-form">
							<div class="form-group">
								<label for="username">User Name</label> <input type="text" class="form-control" id="username" name="username" required autofocus readonly="readonly"/>
							</div>
							<div class="form-group">
								<label for="fullname">Full Name</label> <input type="text" class="form-control" id="fullname" name="fullname" required autofocus />
							</div>
							
							<div class="form-group">
								<label for="password">Password</label> <input type="password" class="form-control" id="password" name="password" required autofocus />
							</div>
							
							<div class="form-group">
								<label for="cnfpassword">Confirm Password</label> <input type="password" class="form-control" id="cnfPassword" name="cnfPassword" required autofocus />
							</div>
							<div class="form-group">
								<label for="roles">Roles</label> <input type="text" class="form-control" id="roles" name="roles" required autofocus />
							</div>
							<input type="hidden" name="id" id="id" />
							<button type="submit" class="btn btn-default" id="edit-form-submit">Submit</button>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
		<!-- Edit Modal end -->

		<!-- delete Modal -->
		<div class="modal fade" id="delete-modal" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Delete</h4>
					</div>
					<div class="modal-body">
						<p id="cnf-delete"></p>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default" data-dismiss="modal">Close</a> <a
							id="cnf" href="/delete" class="btn btn-default"
							data-dismiss="modal">Yes</a>
					</div>
				</div>

			</div>
		</div>
		<!-- delete Modal end -->
	</div>
</c:when>
 <c:otherwise>
    <c:redirect url = "/"/>
</c:otherwise>
</c:choose>
</body>
<script>
	function deleteUser(e) {
		$("#cnf-delete")
				.html(
						"Do you want to delete User "
								+ e.getAttribute('data-fullname'));
		/* $("#cnf").attr("href", "/delete/" + e.getAttribute('data-id')); */
		$('#delete-modal').modal('show');
		$("#cnf").click(function() {
			getCallAjax("/delete?id="+e.getAttribute('data-id'));
		});
	}

	function editUser(e) {
		$("#id").val(e.getAttribute('data-id'));
		$("#username").val(e.getAttribute('data-username'));
		$("#fullname").val(e.getAttribute('data-fullname'));
		$("#roles").val(e.getAttribute('data-roles'));
		$('#edit-modal').modal('show');

	}
</script>

<script type="text/javascript">
$(function() {
	getCallAjax("/findAllUsers");
   $('#edit-form-submit').click(function(e){
	   e.preventDefault();
	   //$('input').next().remove();
	   console.log("Hello");
	   console.log($('form[name=edit-form]').serialize());
	   postCallAjax("/edit", $('form[name=edit-form]').serialize());
   } );
});

function postCallAjax(url, data) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.post({
        url : url,
        data : data,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(res) {       	
       		//$('#user-container').html(res);
       		var resObj = JSON.parse(res);
       		console.log(resObj.length)
       		populateUserContent(resObj)
        }
     })
}

function getCallAjax(url) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
        url : url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(res) {       	
       		//$('#user-container').html(res);
       		//console.log(res);
       		var resObj = JSON.parse(res);
       		console.log(resObj.length);
       		populateUserContent(resObj)
        }
     });
}

function populateUserContent(resObj) {
	var content = '';
	console.log(resObj);
		for(i=0; i<resObj.length; i++) {
			content += '<tr>';
			content +='<td>'+resObj[i].username+'</td>';
			content +='<td>'+resObj[i].fullname+'</td>';
			content +='<td>'+resObj[i].roles+'</td>';
			content +='<td><a href="#" data-id="'+resObj[i].id+'"';
			content +='data-fullname="'+resObj[i].fullname+'" data-username="'+resObj[i].username+'"';
			content +='data-roles="'+resObj[i].roles+'" onclick="return editUser(this)">Edit</a> <a href="#"';
			content +='data-id="'+resObj[i].id+'" data-fullname="'+resObj[i].fullname+'"';
			content +=' onclick="return deleteUser(this)">Delete</a></td>';
			content +='</tr>';
			console.log(content);
		}
		console.log(content);
		$('#user-table').html(content);
}
</script>
</html>
