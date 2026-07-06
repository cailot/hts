<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar background-color my-4">
	<div class="container-fluid d-flex align-items-center">
	<sec:authorize access="isAuthenticated()">
	 <span class="text-white ms-auto me-1">
	 <i class="fas fa-user"></i>&nbsp;&nbsp;&nbsp;
	  Welcome <sec:authentication property="principal.firstName" />
	  <sec:authentication property="principal.lastName" />
	  <span class="text-warning ml-1 mr-2">[<sec:authentication property="principal.username" />]</span>
	  Logged at <c:out value="<%= new java.util.Date() %>" />
	  <a href="${pageContext.request.contextPath}/pdf/hume.pdf" target="_blank"><i class="fas fa-file-alt text-warning fa-lg mx-3"></i></a>
	  <form:form action="${pageContext.request.contextPath}/logout" method="POST" id="logout" style="display: inline;">
	    <sec:csrfInput />
	    <button class="btn mr-1" style="background: none; border: none;"><i class="fas fa-power-off fa-lg text-warning" title="Log Out"></i></button>
	  </form:form>
	</span>
	</sec:authorize>
	</div>
</nav>