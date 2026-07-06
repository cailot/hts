<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>

.menu-nav{
    color: white !important;
    font-size: 1.3em !important;
    text-decoration:none;
}

.menu-inactive{
    color: grey !important;
    font-size: 1.3em !important;
    text-decoration:none;
}

.menu-nav-wrapper {
    position: relative;
}
    
a:hover{
	color: white !important;
	text-decoration:none;
}    
      
.tooltip{
	pointer-events: none;
}	
.tooltip-inner {
   background-color: #007bff !important;
  	max-width: 800px;
}
.tooltip.bs-tooltip-right .arrow:before {
    border-right-color: #007bff !important;
}
.tooltip.bs-tooltip-left .arrow:before {
    border-right-color: #007bff !important;
}
.tooltip.bs-tooltip-bottom .arrow:before {
    border-right-color: #007bff !important;
}
.tooltip.bs-tooltip-top .arrow:before {
    border-right-color: #007bff !important;
}

.active {
	color: #007bff !important;
}

.nav-item.dropdown:hover .dropdown-menu {
	display: block;
}

.dropdown-menu {
    background-color: #bee0ec; /* Background color of the submenu */
    border: 1px solid #1a6985; /* Border around the submenu */
    border-radius: 4px; /* Rounded corners */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 1); /* Shadow for a subtle elevation effect */
    padding: 8px 0; /* Padding inside the submenu */
}

.dropdown-menu > .dropdown-submenu {
    position: relative;
}

.dropdown-menu > .dropdown-submenu > .dropdown-menu {
    display: none !important;
    position: absolute;
    left: 100%;
    top: 0;
	transition: visibility 0.5s ease-in-out, opacity 0.2s ease-in-out;
    visibility: hidden;
    opacity: 0;
}

.dropdown-menu > .dropdown-submenu:hover > .dropdown-menu {
    display: block !important;
	visibility: visible;
    opacity: 1;
}

/* Styling for individual dropdown items */
.dropdown-item {
    padding: 8px 20px;
    color: #333; /* Text color */
    text-decoration: none;
    display: block;
    transition: background-color 0.3s; /* Smooth transition for background color change */
}

/* Hover effect for dropdown items */
.dropdown-item:hover {
    background-color: #f8f9fa; /* Background color on hover */
}

.dropdown-menu .dropdown-item {
    color: #2d398e; /* Font color of submenu items */
	font-weight: bold;
}


.custom-dropdown-menu {
    margin-left: -80px; /* Adjust this value to move the menu to the left */
}


</style>
<script type="text/javascript">

	$(document).ready( function () {

		$('[data-toggle="tooltip"]').tooltip();
		
	});
</script>

<c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}" />


<%-- Disable 'Detail' & 'Audit' if user is VIEWER --%>
<c:set var="isViewer" value="${false}" />
<sec:authorize access="isAuthenticated()">
<sec:authentication var="role" property='principal.authorities'/>
	<c:if test="${role == '[ROLE_Viewer]'}" >
		<c:set var="isViewer" value="${true}" />
	</c:if>
</sec:authorize>


<div class="menu-style" >
 	<nav class="navbar pl-5">
 		<a href="${pageContext.request.contextPath}/dashboard" class="menu-nav ${currentPage.endsWith('/dashboard') ? 'active' : ''}" data-toggle="tooltip" data-placement="auto" data-html="true"
								title="<div class='text-left'>It displays the successful uploads stats per document types</div>"><i class="fa fa-pie-chart fa-lg"></i>
 		&nbsp;Upload
 		</a>
 	</nav>
</div>	
<div class="menu-style" >
 	<nav class="navbar">
 		<a href="${pageContext.request.contextPath}/summary" class="menu-nav ${currentPage.endsWith('/summary') ? 'active' : ''}" data-toggle="tooltip" data-placement="auto" data-html="true"
								title="<div class='text-left'>It breaks down total figures into several stages with success & failure</div>"><i class="fa fa-bar-chart fa-lg"></i>
 		&nbsp;Summary
 		</a>
 	</nav>
 </div>
 <div class="menu-style" >
 	<nav class="navbar">
 		<c:if test="${isViewer}">
 			<span class="menu-inactive"><i class="fa fa-list-ul fa-lg"></i>&nbsp;Detail</span>
 		</c:if>
 		<c:if test="${!isViewer}">
	 		<a href="${pageContext.request.contextPath}/detail" class="menu-nav ${currentPage.endsWith('/detail') ? 'active' : ''}" data-toggle="tooltip" data-placement="auto" data-html="true"
									title="<div class='text-left'>It shows exception list with patient information</div>"><i class="fa fa-list-ul fa-lg"></i>
	 		&nbsp;Detail
 		</a>
 		</c:if>
 	</nav>
</div>
<div class="menu-style" >
 	<nav class="navbar">
 		<c:if test="${isViewer}">
 			<span class="menu-inactive"><i class="fa fa-calendar-check-o fa-lg"></i>&nbsp;Audit</span>
 		</c:if>
 		<c:if test="${!isViewer}">
	 		<a href="${pageContext.request.contextPath}/audit" class="menu-nav ${currentPage.endsWith('/audit') ? 'active' : ''}" data-toggle="tooltip" data-placement="auto" data-html="true"
									title="<div class='text-left'>It tracks which clinicians access to which patient for what reason</div>"><i class="fa fa-calendar-check-o fa-lg"></i>
	 		&nbsp;Audit
 		</a>
 		</c:if>
 	</nav>
</div>
<div class="menu-style">
    <nav class="navbar">
        <c:if test="${isViewer}">
            <span class="menu-inactive"><i class="fa fa-search fa-lg"></i>&nbsp;HPI-I</span>
        </c:if>
        <c:if test="${!isViewer}">
        	<li class="nav-item dropdown" style="list-style-type: none;">
	            <div class="menu-nav-wrapper">
	                <a href="" class="menu-nav ${currentPage.endsWith('/singleHpii') || currentPage.endsWith('/batchHpii') ? 'active' : ''}" data-placement="auto" data-html="true"><i class="fa fa-search fa-lg"></i>
	                    &nbsp;HPI-I
	                </a>
	                <div class="dropdown-menu">
	                    <a class="dropdown-item" href="${pageContext.request.contextPath}/singleHpii" data-toggle="tooltip" data-placement="left" title="It looks up the HPI-I for a specific practitioner">Single Search</a>
	                    <a class="dropdown-item" href="${pageContext.request.contextPath}/batchHpii" data-toggle="tooltip" data-placement="left" title="It searches for HPI-I records for multiple practitioners">Batch Search</a>
	                </div>
	            </div>
            </li>
        </c:if>
    </nav>
</div>
<div class="menu-style" >
 	<nav class="navbar">
 		<a href="${pageContext.request.contextPath}/user" class="menu-nav ${currentPage.endsWith('/user') ? 'active' : ''}" data-toggle="tooltip" data-placement="auto" data-html="true" title="<div class='text-left'>It enables users to edit profile info & reset password</div>"><i class="fa fa-cog fa-lg"></i>
 		&nbsp;Settings
 		</a>
 	</nav>
</div>
<div class="menu-style mr-5">
    <nav class="navbar">
       	<li class="nav-item dropdown" style="list-style-type: none;">
            <div class="menu-nav-wrapper">
                <a href="" class="menu-nav ${currentPage.endsWith('/docoUG') || currentPage.endsWith('/docoEM') || currentPage.endsWith('/docoHS') ? 'active' : ''}" data-placement="auto" data-html="true"><i class="fa fa-book fa-lg"></i>
                    &nbsp;Document
                </a>
                <div class="dropdown-menu custom-dropdown-menu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/docoUG" data-toggle="tooltip" data-placement="left" title="The document serves as a user guide, providing instructions on navigating the application's contents">User Guide</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/docoEM" data-toggle="tooltip" data-placement="left" title="The document offers sample exceptions, demonstrating how to interpret and troubleshoot reported errors">Error Troubleshooting Manual</a>
                   	<a class="dropdown-item" href="${pageContext.request.contextPath}/docoHS" data-toggle="tooltip" data-placement="left" title="The document illustrates how to perform HPI-I searches for practitioners">HPI-I Search</a>
                </div>
            </div>
         </li>
    </nav>
</div>

