<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="au.org.htsv.hips.report.entity.HpiiDTO" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hips.report.css"/>

<script type="text/javascript">

$(document).ready( function () {


});

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Toggle Single Name
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function toggleFirstName(checkbox) {
	var firstNameField = document.getElementById("firstNameSection");
	if (checkbox.checked) {
		firstNameField.style.display = "none";
	} else {
		firstNameField.style.display = "flex";
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Clear Form Data
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function clearForm() {
	document.querySelector("#lastName").value = '';
	document.querySelector("#firstName").value = '';
	document.querySelector("#ahpra").value = '';
}

</script> 

<style>
    /* Custom CSS styles */
    #searchCondition {
        background-color: #f8f9fa;
        border-radius: 15px;
        padding: 50px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
    
    #hpiiResult {
        padding: 20px;
    }
    
    label {
        font-weight: bold;
    }
    .form-check-input {
        margin-top: 5px;
    }
    .btn-primary {
        background-color: #007bff;
        border-color: #007bff;
    }
    .btn-secondary {
        background-color: #6c757d;
        border-color: #6c757d;
    }
    
    .stats {
        margin-top: 20px;
    }

    #singleResultTable {
        width: 100%;
        border-collapse: collapse;
    }

    #singleResultTable th,
    #singleResultTable td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }

    #singleResultTable th {
        background-color: #f2f2f2;
    }

    #singleResultTable tbody tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    .ellipsis {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    
    #searchCondition h2 {
        color: #007bff;
        margin-bottom: 20px;
    }
</style>

<div style="width: 85%; margin:0 auto;">
	<!-- Facility Code -->
	<c:if test="${not empty hospitals}">
  		<c:set var="firstHospital" value="${hospitals[1]}" />
  	</c:if>
	<!-- Search section -->
	<div class="row m-2 pt-3 justify-content-center">
		<div id="searchCondition" class="col-md-8">
			<h2 class="text-center">HPI-I Individual Search</h2>
	        <form method="get" action="${pageContext.request.contextPath}/singleHpii">
	            <div class="form-row p-2 mt-2">
	                <div class="col-md-4 align-items-center d-flex">
	                    <label for="lastName">Last Name:</label>
	                </div>
	                <div class="col-md-8">
	                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Last Name" />
	                </div>
	            </div>
	            <div class="form-row p-2 mt-2" id="firstNameSection">
	                <div class="col-md-4 align-items-center d-flex">
	                    <label for="firstName">First Name:</label>
	                </div>
	                <div class="col-md-8">
	                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="First Name" />
	                </div>
	            </div>
	            <div class="form-row p-2 mt-2">
	                <div class="col-md-4 align-items-center d-flex">
	                    <label for="ahpra">Ahpra Number:</label>
	                </div>
	                <div class="col-md-8">
	                    <input type="text" class="form-control" id="ahpra" name="ahpra" placeholder="Ahpra Number" />
	                </div>
	            </div>
	              <div class="form-row p-2 mt-2">
	                <div class="col-md-4 align-items-center d-flex">
	                    <label for="ahpra">Provider Number (Optional): </label>
	                </div>
	                <div class="col-md-8">
	                    <input type="text" class="form-control" id="providerNumber" name="providerNumber" placeholder="Provider Number" />
	                </div>
	            </div>                 			
				<div class="form-group p-4 mt-2">
					<div class="form-row">
						<div class="offset-md-9">
						</div>
						<div class="custom-control custom-switch">
						  	<input type="checkbox" class="custom-control-input" id="singleNameSwitch" onchange="toggleFirstName(this)">
	  						<label class="custom-control-label" for="singleNameSwitch">Single Name</label>
						</div>
					</div>
				</div>
	            <div class="form-row mt-3 justify-content-center">
	                <button type="submit" class="btn btn-primary mr-2" onclick="return validateIndividualForm();">Get HPI-I</button>&nbsp;&nbsp;&nbsp;
	                <button type="button" class="btn btn-secondary" onclick="clearForm()">Clear</button>
	            </div>
	            <input type="hidden" name="searchCheck" value="true" />
	            
				<sec:authorize access="isAuthenticated()">
				<sec:authentication var="role" property='principal.authorities'/>
				<c:choose>
					<c:when test="${fn:replace(role,'ROLE_','') == '[Administrator]'}">
					<input type="hidden" name="facility" value="DHV" />
				</c:when>
				<c:otherwise>
					<input type="hidden" name="facility" value="${firstHospital.value}" />            
				</c:otherwise>
				</c:choose>
				</sec:authorize>
	            
	        </form>
	    </div>
	</div>
	<!-- Result/Error display -->
	<c:choose>
		<c:when test="${errors != null}">
			<!-- Handle errors -->
			<div class="row m-3 pt-5 justify-content-center">
				<div class="col-md-8 alert alert-danger" role="alert">
					<h5>
						<i class="fa fa-times-circle fa-lg"></i>&nbsp;&nbsp;<c:out value="${errors}" />
					</h5>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<!-- Result Section -->
			<div class="row m-3 pt-1 pb-5 justify-content-center">
				<div id="hpiiResult" class="col-md-10">
				<c:choose>
					<c:when test="${hpiiData != null}">
						
						<%-- 
						<div class="alert alert-success mt-5 mb-4" role="alert">
							<h7>
								<i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;HPI-I for <strong>${hpiiData.firstName} ${hpiiData.lastName}</strong> is retrieved successfully
							</h7>
						</div>
						--%>
					
						<div class="stats">
							<table id="singleResultTable" class="hips-table-border">
								<thead>
									<tr>
										<th class="center-cell" style="width: 25%;">Last Name</th>
										<th class="center-cell" style="width: 25%;">First Name</th>
										<th class="center-cell" style="width: 15%;">AHPRA</th>
										<th class="center-cell" style="width: 15%;">HPI-I</th>
										<th class="center-cell" style="width: 15%;">Retrieved Time</th>
										<th class="center-cell" style="width: 5%;">Status</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="small ellipsis pl-3"><span><c:out value="${hpiiData.lastName}" /></span></td>
										<td class="small ellipsis pl-3"><span><c:out value="${hpiiData.firstName}" /></span></td>
										<td class="small ellipsis pl-3"><span><c:out value="${hpiiData.ahpra}" /></span></td>
										<td class="small ellipsis pl-3"><span><c:out value="${hpiiData.hpii}" /></span></td>
										<td class="small ellipsis pl-3"><span><c:out value="${hpiiData.createDate}" /></span></td>
										<td class="small ellipsis text-center">
											<c:choose>
												<c:when test="${fn:toLowerCase(hpiiData.status) eq 'active'}">
													<i class="fa fa-check-circle text-success fa-2x" data-toggle="tooltip" title="HPI-I Active"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-check-circle text-secondary fa-2x" data-toggle="tooltip" title="HPI-INot Active"></i>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning" role="alert">
							<h5>
								<i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;Please fill in <strong>Name</strong> and <strong>Registered ID</strong> for HPI-I search.
							</h5>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>


<!-- Validation Error Pop up -->
<div id="validation-error" class="modal fade">
	<div class="modal-dialog">
		<div class="alert alert-block alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<i class="fa fa-frown-o fa-lg"></i>&nbsp;&nbsp;Please make sure <b>Name</b> & <b>Registered ID</b> filled in
		</div>
	</div>
</div>
