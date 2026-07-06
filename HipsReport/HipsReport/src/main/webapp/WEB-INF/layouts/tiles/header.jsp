<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
a:hover{
	color: #007BFF !important;
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


.rpl-site-header__divider {
    margin: 0 1rem;
    height: 3.0rem;
    border-right: 1px solid #fff;
}

 
</style>
<script type="text/javascript">

	$(document).ready( function () {

		$('[data-toggle="tooltip"]').tooltip();
		
	});
</script>


<div class="col-md-9 d-flex justify-centent-center dhhs-color" style="padding-top: 1rem; padding-left: 1.5rem;">
	<div class="px-3">
		<img src="${pageContext.request.contextPath}/images/vicgov.png" style="width:7rem;" alt="State Government of Victoria">
	</div>
	<div class="rpl-site-header__divider"></div>
	<div class="px-3 pt-3">
		<h5>Department of Health</h5>
	</div>
	<div class="px-3 d-flex align-items-center">
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HIPS Exception Report</h1>
	</div>
</div>
<div class="col-md-3 dhhs-color">
<sec:authorize access="isAuthenticated()">
<sec:authentication var="role" property='principal.authorities'/>
<sec:authentication var="username" property="principal.username"/>
	<table class="dhhs-color" style="font-size: 120%;">
		<tr>
			<td>
				<span class="font-weight-bolder text-info">${username}</span> on behalf of
				<span class="font-weight-bolder text-info">${fn:replace(role,'ROLE_','')}</span> 
			</td>
			<td>
				<form:form action="${pageContext.request.contextPath}/logout" method="POST" id="logout">
					&nbsp;&nbsp;
						<button class="btn">
	    			 		<img src="${pageContext.request.contextPath}/images/logout.png" class="img-fluid" style="max-width: 25%; height: auto;" data-toggle="tooltip" data-placement="auto" data-html="true"
								title="<div class='text-left'>Log out from the site</div>">
	    				</button>
				</form:form>
			</td>
		</tr>
	</table> 
</sec:authorize>
</div>