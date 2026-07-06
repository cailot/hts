<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
   	<!-- Topbar Navbar -->
	<ul class="navbar-nav ml-auto">
        <!-- Nav Item - User Information -->
		
		
<sec:authorize access="isAuthenticated()">
    <li class="nav-item dropdown no-arrow">
        
		<p>
		
		 User : <span sec:authentication="principal.username"></span>
		</p>	
		
		
        <form:form action="${pageContext.request.contextPath}/logout" method="POST" id="logout">
            <sec:csrfInput />
            <input type="submit" class="btn btn-sm btn-danger" value="Logout" />
        </form:form>
    </li>
</sec:authorize>
		
		
		
		
		
		
		
		
		
		
    
	</ul>
</nav>