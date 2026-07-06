<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap5.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	
<style>

 	body {
        background: url('${pageContext.request.contextPath}/img/login.jpg') no-repeat center center fixed;
    	background-size: cover;    
        height: 100vh;
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        font-family: 'Nunito', sans-serif;
    }

    .login-card {
        background: #ffffff;
        border-radius: 16px;
        border: 3px solid #c63663;
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
        padding: 2rem;
        width: 100%;
        max-width: 420px;
        z-index: 1;
        animation: fadeIn 0.8s ease-in-out;
        position: relative;
    }

    .login-card .card-header {
        background: #c63663;
        color: white;
        font-size: 1.5rem;
        font-weight: bold;
        border-radius: 12px 12px 0 0;
        padding: 1rem;
        text-align: center;
    }

    .form-control {
        border-radius: 10px;
        padding: 0.75rem 1rem;
    }

    .input-group-text {
        background-color: #f3f3f3;
        border-radius: 10px 0 0 10px;
        padding: 0.75rem;
    }

    .btn-primary {
        border-radius: 10px;
        font-weight: 600;
        padding: 0.75rem;
        margin-top: 1rem;
        font-size: 1.1rem;
        background-color: #c63663;
        border: none;
    }

    .btn-primary:hover {
        background-color: #a42c52;
    }

    .alert {
        font-size: 0.95rem;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-20px); }
        to { opacity: 1; transform: translateY(0); }
    }
</style>

<div class="d-flex flex-column" id="wrapper">
	<div class="content main-content mb-5 bg">
		<!-- Main Login Card -->
		<div class="login-card">
		    <h4 class="card-header">Hume Dashboard Monitor</h4>
		    <div class="card-body">
		   		<!--  Error Message -->
		        <c:choose>
				    <c:when test="${param.error eq 'unauthorised'}">
				        <div class="alert alert-danger text-center mt-4">
				            You are authenticated, but not authorised.
				        </div>
				    </c:when>
				    <c:when test="${param.error eq 'bad_credentials'}">
				        <div class="alert alert-danger text-center mt-4">
				            Invalid username or password.
				        </div>
				    </c:when>
				    <c:when test="${param.error eq 'no_user'}">
				        <div class="alert alert-danger text-center mt-4">
				            No user found in the system.
				        </div>
				    </c:when>
				    <c:otherwise>
				        <c:if test="${param.error != null}">
				            <div class="alert alert-danger text-center mt-4">
				                Invalid username or password.
				            </div>
				        </c:if>
				    </c:otherwise>
				</c:choose>		        
		        <!-- Logout Message  -->
		        <c:if test="${param.logout != null}">
		            <div class="alert alert-success text-center mt-4 ">You have been logged out.</div>
		        </c:if>
		        <!-- Login Form -->
		        <form:form action="${pageContext.request.contextPath}/processLogin" method="POST">
		            <div class="my-3">
		                <label class="form-label">Username</label>
		                <div class="input-group">
		                    <span class="input-group-text"><i class="fa fa-user mx-2"></i></span>
		                    <input type="text" class="form-control" name="username" required />
		                </div>
		            </div>
		            <div class="mb-3">
		                <label class="form-label">Password</label>
		                <div class="input-group">
		                    <span class="input-group-text"><i class="fa fa-lock mx-2"></i></span>
		                    <input type="password" name="password" class="form-control" required />
		                </div>
		            </div>
		            <input type="hidden" name="redirect" value="">
		            <button type="submit" class="btn btn-primary w-100 d-flex align-items-center justify-content-start mt-4">
    					<img src="${pageContext.request.contextPath}/img/vicgov.png" alt="Login" class="mx-5" style="height: 24px;">&nbsp;&nbsp;Login
					</button>
		        </form:form>
		    </div>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="include/footer.jsp"/>
</div>