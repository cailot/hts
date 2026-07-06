<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.dataTables.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dataTables.buttons.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hips.report.css"/>

	
<script type="text/javascript">

$(document).ready(function(){
	 
	$('table .edit').on('click', function(){
		var username = $(this).parent().find('#username').val();
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/findUser/' + username,
			success: function(user){
				$('#editUserModal #usernameEdit').val(user.username);
				$('#editUserModal #firstnameEdit').val(user.firstname);
				$('#editUserModal #lastnameEdit').val(user.lastname);
				var roleName = user.role.replace("ROLE_","");
				$('#editUserModal #roleEdit').val(roleName);
			}	
		});
	});

	$('table .password').on('click', function(){
		var username = $(this).parent().find('#username').val();
		$('#passwordModal #usernamepassword').val(username);
	});
	
	$('table .suspend').on('click', function(){
		var username = $(this).parent().find('#username').val();
		$('#suspendUserModal #usernameSuspend').val(username);
	});

	$('table .activate').on('click', function(){
		var username = $(this).parent().find('#username').val();
		$('#activateUserModal #usernameActivate').val(username);
	});

	$('table .delete').on('click', function(){
		var username = $(this).parent().find('#username').val();
		$('#deleteUserModal #usernameDelete').val(username);
	});
	
	$('#userTable').DataTable();

	// Add validation on input
    $('#passwordpassword').on('input', function() {
        validatePassword($(this).val());
    });

    $('#confirmPasswordpassword').on('input', function() {
        validateConfirmPassword();
    });
	
});


function showUserInfo(username){
	$.ajax({
		type: 'GET',
		url: '${pageContext.request.contextPath}/findUser/' + username,
		success: function(user){
			$('#detailTable #firstname').html(user.firstname);
			$('#detailTable #lastname').html(user.lastname);
			var roleName = user.role.replace("ROLE_","");
			$('#detailTable #role').html(roleName);
		}	
	});
}

//Password validation function
function validatePassword(password) {
    // Define validation rules
    const minLength = 15;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumbers = /\d/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    // Clear previous messages
    $('.password-requirements').remove();

    // Return true only if all conditions are met
    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumbers && hasSpecialChar;
}

function validateConfirmPassword() {
    const password = $('#passwordpassword').val();
    const confirmPassword = $('#confirmPasswordpassword').val();
    
    // Clear previous messages
    $('.confirm-password-message').remove();
    
    return password == confirmPassword;
}

function passwordChange() {
    const password = $('#passwordpassword').val();
    const isPasswordValid = validatePassword(password);
    const isConfirmValid = validateConfirmPassword();
    
    if (!isPasswordValid) {
        alert('Please ensure your password meets all requirements:\n' +
              '- Minimum 15 characters\n' +
              '- At least one uppercase letter\n' +
              '- At least one lowercase letter\n' +
              '- At least one number\n' +
              '- At least one special character');
        return false;
    }
    
    if (!isConfirmValid) {
        alert('Passwords do not match');
        return false;
    }
    
    return true;
}



</script>
  
<sec:authorize access="isAuthenticated()">
<sec:authentication var="role" property='principal.authorities'/>
<sec:authentication var="username" property="principal.username"/>
<c:choose>
	<c:when test="${fn:replace(role,'ROLE_','') == '[Administrator]'}">
	
	<div style="width: 85%; margin:0 auto;">
		<div class="row m-3">
			<div class="col-lg-9" style="text-align: center;">
				<span class="font-weight-bolder text-info" style="font-size: 2rem;">Manage Users</span>
			</div>
			<div class="col-lg-3"  style="text-align: right;">
				<a href="#addUserModal" class="btn btn-primary" data-toggle="modal">
					<i class="fa fa-plus-square"></i>&nbsp;&nbsp;&nbsp;<span>New User</span>
				</a>
			</div>
		</div>
		<table class="table table-striped table-hover" id="userTable">
			<thead>
			<tr>
				<th class="left header" width="15%">Username</th>
				<th class="left header" width="12.5%">First Name</th>
				<th class="left header" width="12.5%">Last Name</th>
				<th class="left header" width="20%">Role</th>
				<th class="left header" width="10%" data-orderable="false">Status</th>
				<th class="left header" width="13%">Last Login</th>
				<th class="left header" width="5%">Fail</th>
				<th class="centre header" width="12%" data-orderable="false">Action</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td class="small ellipsis"><span><c:out value="${user.username}" /></span></td>
						<td class="small ellipsis"><span><c:out value="${user.firstname}" /></span></td>
						<td class="small ellipsis"><span><c:out value="${user.lastname}" /></span></td>
						<td class="small ellipsis"><span><c:out value="${fn:replace(user.role,'ROLE_','')}" /></span></td>
						<td class="small ellipsis text-center" style="font-size: 1.15em;">
						    <c:choose>
						        <c:when test="${user.enabled == 1}">
						            <i class="fa fa-check-circle text-success"></i>
						        </c:when>
						        <c:otherwise>
						            <i class="fa fa-check-circle text-muted"></i>
						        </c:otherwise>
						    </c:choose>
						</td>
						<td class="small ellipsis">
						  <span>
						    <fmt:formatDate value="${user.logintime}" pattern="yyyy-MM-dd HH:mm" />
						  </span>
						</td>
						<td class="small ellipsis">
						  <span>
						    <c:out value="${user.loginfail}"/>
						  </span>
						</td>
						<td>
							<a href="#editUserModal" class="edit mr-1" data-toggle="modal"><i class="fa fa-edit text-primary" data-toggle="tooltip" title="Edit"></i></a>
							<a href="#passwordModal" class="password mr-1" data-toggle="modal"><i class="fa fa-key text-warning" data-toggle="tooltip" title="Change Password"></i></a>
							<c:choose>
						        <c:when test="${user.enabled == 1}">
						            <a href="#suspendUserModal" class="suspend" data-toggle="modal"><i class="fa fa-pause text-danger" data-toggle="tooltip" title="Suspend User"></i></a>
						        </c:when>
						        <c:otherwise>
						            <a href="#activateUserModal" class="activate" data-toggle="modal"><i class="fa fa-play text-success" data-toggle="tooltip" title="Reactivate User"></i></a>
						        </c:otherwise>
						    </c:choose>
						    <a href="#deleteUserModal" class="delete ml-1" data-toggle="modal"><i class="fa fa-trash text-secondary" data-toggle="tooltip" title="Delete User"></i></a>
								
							<input type="hidden" id="username" value="${user.username}" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</c:when>
<c:otherwise>
	<div class="row h-100 justify-content-center align-items-center" style="width: 50%; margin:0 auto;">
		<table class="table table-hover" id="detailTable">
		<thead>
			<tr height="100px">
				<th colspan="2" style="text-align:center; vertical-align:top; border: 1px solid white;">
					<i class="fas fa-user-check text-primary" data-toggle="tooltip" title="User Info" style="font-size:2rem;"></i>&nbsp;
					<span class="text-dark" style="font-size: 1.5rem;">User <span class="font-weight-bolder text-primary" style="font-size: 1.75rem;">${username}</span> Information</span>
				</th>
			</tr>
		</thead>
		<tr height="80px">
			<td class="left-cell"><b>Username </b></td>
			<td class="left-cell">${username}</td>
		</tr>
		<tr height="80px">
			<td class="left-cell"><b>First Name </b></td>
			<td id="firstname" class="left-cell"></td>
		</tr>
		<tr height="80px">
			<td class="left-cell"><b>Last Name </b></td>
			<td id="lastname" class="left-cell"></td>
		</tr>
		<tr height="80px">
			<td class="left-cell"><b>Organisation </b></td>
			<td id="role" class="left-cell"></td>
		</tr>
		<tr height="80px">
			<td colspan="2" class="center-cell">
				<a href="#editUserModal" class="btn btn-primary edit" data-toggle="modal">
					<i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;<span>Edit</span>
				</a>&nbsp;
				<a href="#passwordModal" class="btn btn-success password" data-toggle="modal">
					<i class="fa fa-key"></i>&nbsp;&nbsp;&nbsp;<span>Change Password</span>
				</a>
				<input type="hidden" id="username" value="${username}" />		
			</td>
		</tr>
		</table> 
	</div>
	<script>
		showUserInfo('${username}');
	</script>
	
</c:otherwise>
</c:choose>
</sec:authorize>


<!--  Add Modal HTML -->
<div id="addUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="${pageContext.request.contextPath}/addUser">
				<sec:csrfInput />
				<div class="modal-header">
					<h4 class="modal-title">Add New User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>Username</label> <input type="text" class="form-control" required="required" name="usernameAdd" />
					</div>
					<div class="form-group">
						<label>First Name</label> <input type="text" class="form-control" required="required" name="firstnameAdd" />
					</div>
					<div class="form-group">
						<label>Last Name</label> <input type="text" class="form-control" required="required" name="lastnameAdd" />
					</div>
					<div class="form-group">
						<label>Password</label> <input type="text" class="form-control" required="required" name="passwordAdd" />
					</div>
					<div class="form-group">
						<label>Organisation</label> <input type="text" class="form-control" required="required" name="roleAdd" />
						<!--<form:select path="role" items="${roleList}" />-->
					</div>
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"><input type="submit" class="btn btn-info" value="Add">
				</div>
			</form>
		</div>
	</div>
</div>


<!--  Edit Modal HTML -->
<div id="editUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="${pageContext.request.contextPath}/editUser">
				<sec:csrfInput />
				<div class="modal-header">
					<h4 class="modal-title">Edit User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>Username</label> <input type="text" class="form-control" required="required" name="usernameEdit" id="usernameEdit" readonly/>
					</div>
					<div class="form-group">
						<label>First Name</label> <input type="text" class="form-control" required="required" name="firstnameEdit" id="firstnameEdit"/>
					</div>
					<div class="form-group">
						<label>Last Name</label> <input type="text" class="form-control" required="required" name="lastnameEdit"  id="lastnameEdit"/>
					</div>
					<div class="form-group">
						<label>Organisation</label>
						<sec:authentication var="role" property='principal.authorities'/>
						<c:choose>
							<c:when test="${fn:replace(role,'ROLE_','') == '[Administrator]'}">
								<input type="text" class="form-control" required="required" name="roleEdit" id="roleEdit" />
							</c:when>
							<c:otherwise>
								<input type="text" class="form-control" required="required" name="roleEdit" id="roleEdit" readonly/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"><input type="submit" class="btn btn-info" value="Save">
				</div>
			</form>
		</div>
	</div>
</div>


<!--  Password Modal HTML -->
<div id="passwordModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="${pageContext.request.contextPath}/changePassword" onsubmit="return passwordChange();">
                <sec:csrfInput />
                <div class="modal-header">
                    <h4 class="modal-title">Change Password</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" 
                               class="form-control" 
                               required="required" 
                               name="passwordpassword" 
                               id="passwordpassword" 
                               autocomplete="new-password" />
                        <small class="form-text text-info">
                            Password must contain at least 15 characters, including uppercase, lowercase, numbers, and special characters.
                        </small>
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input type="password" 
                               class="form-control" 
                               required="required" 
                               name="confirmPasswordpassword" 
                               id="confirmPasswordpassword"
                               autocomplete="new-password" />
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Change Password">
                    <input type="hidden" name="usernamepassword" id="usernamepassword" />
                </div>
            </form>
        </div>
    </div>
</div>

<!--  Suspend Modal HTML -->
<div id="suspendUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="${pageContext.request.contextPath}/suspendUser">
				<sec:csrfInput />
				<div class="modal-header">
					<h4 class="modal-title">Suspend User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<p>Are you sure you want to suspend this user ?</p>
						<p class="text-warning"><small>This action cannot be undone.</small></p>	
					</div>
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"><input type="submit" class="btn btn-danger" value="Suspend">
					<input type="hidden" name="usernameSuspend" id="usernameSuspend"/> 
				</div>
			</form>
		</div>
	</div>
</div>

<!--  Activate Modal HTML -->
<div id="activateUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="${pageContext.request.contextPath}/activateUser">
				<sec:csrfInput />
				<div class="modal-header">
					<h4 class="modal-title">Activate User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<p>Are you sure you want to activate this user again ?</p>
						<p class="text-warning"><small>This action cannot be undone.</small></p>	
					</div>
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"><input type="submit" class="btn btn-success" value="Activate">
					<input type="hidden" name="usernameActivate" id="usernameActivate"/> 
				</div>
			</form>
		</div>
	</div>
</div>

<!--  Delete Modal HTML -->
<div id="deleteUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="${pageContext.request.contextPath}/deleteUser">
				<sec:csrfInput />
				<div class="modal-header">
					<h4 class="modal-title">Delete User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<p>Are you sure you want to delete this user ?</p>
						<p class="text-warning"><small>This action cannot be undone.</small></p>	
					</div>
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"><input type="submit" class="btn btn-danger" value="Delete">
					<input type="hidden" name="usernameDelete" id="usernameDelete"/> 
				</div>
			</form>
		</div>
	</div>
</div>

