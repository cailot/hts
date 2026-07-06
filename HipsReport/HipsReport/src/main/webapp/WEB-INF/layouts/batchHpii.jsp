<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="au.org.htsv.hips.report.entity.HpiiDTO" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hips.report.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jszip.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pdfmake.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/vfs_fonts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttons.print.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datetime-moment.js"></script>
   	

<script type="text/javascript">

$(document).ready(function () {
	$('#hpiiTable').DataTable({
   		dom: 'Bfrtip',
	        buttons: [
	            'copyHtml5', 'csvHtml5', 
	            {
		            extend: 'pdfHtml5',
		            download: 'open',
		            pageSize: 'A0'
		        }
	        ],
	    'lengthChange': false,
	    'order' : []
	}); 
});

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Show uploading file
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function updateFileName(input) {
    var fileName = input.files[0].name;
    var fileNameContainer = document.getElementById("file-name-container");
    fileNameContainer.innerHTML = "<p>Selected file: " + fileName + "</p>";
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Show Loading image
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function beforeUpload() {
	 // Show the spinner and disable the form elements
     $('#spinner').modal('show');
     // Prevent form submission if no file is selected
     $('.upload-button').prop('disabled', true).addClass('disabled');
}

</script> 

<style>
       
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

    #hpiiTable {
        width: 100%;
        border-collapse: collapse;
    }

    #hpiiTable th,
    #hpiiTable td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }

    #hpiiTable th {
        background-color: #f2f2f2;
    }

    #hpiiTable tbody tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    #hpiiTable tbody tr:hover {
        background-color: #f2f2f2;
    }

    .ellipsis {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    
	
	.upload-section {
        background-color: #f8f9fa;
        border-radius: 10px;
        padding: 20px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }

    .upload-section h2 {
        color: #007bff;
        margin-bottom: 20px;
    }

    .csv-image {
        max-width: 100%;
        border-radius: 10px;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
    }

    .csv-template {
        font-weight: bold;
        color: #6c757d;
    }

    .download-link i {
        color: #007bff;
        text-decoration: none;
    }

    .download-link:hove i {
        color: #007bff;
    }

    .upload-button {
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .upload-button:hover {
        background-color: #0056b3;
    }

    .file-input {
        display: none;
    }

    .upload-label {
        display: block;
        margin-top: 10px;
        color: #6c757d;
    }
    .upload-section .form-row .input-group {
    width: 100%;
	}
	
	.upload-section .form-row .upload-label {
	    cursor: pointer;
	}
	
	.upload-section .form-row .file-input {
	    display: none;
	}
	
	.upload-section .form-row {
	    width: 100%;
	}
	
	.recommendation-message {
        display: block;
        background-color: #d1ecf1;
        border: 1px solid #dee2e6;
        padding: 10px;
        border-radius: 5px;
        margin-top: 15px;
        font-size: 0.8em;
        text-align: left;
        color: #333;
    }
    
</style>

<div style="width: 85%; margin:0 auto;">
	<!-- Facility Code -->
	<c:if test="${not empty hospitals}">
  		<c:set var="firstHospital" value="${hospitals[1]}" />
  	</c:if>
	<!-- Search section -->
	<div class="row m-3 pt-5 justify-content-center">
		<div class="upload-section col-md-8">
	    <h2 class="text-center">Upload CSV File</h2>
	    <form id="fileUploadForm" method="post" action="${pageContext.request.contextPath}/batchHpii" enctype="multipart/form-data" onsubmit="return beforeUpload()">
	        <div class="form-row p-4">
	            <div class="col-md-9">
	                <img src="${pageContext.request.contextPath}/images/sample.png" class="csv-image" alt="Sample of CSV file">
	            </div>
	            <div class="col-md-2 d-flex align-items-center justify-content-center">
	                <p class="csv-template">CSV Template</p>
	            </div>
	            <div class="col-md-1 d-flex align-items-center justify-content-center mb-3">
	                <a href="${pageContext.request.contextPath}/csv/sample.csv" download class="download-link">
	                    <i class="fa fa-file-o fa-2x"></i>
	                </a>
	            </div>
	        </div>
	        <div class="form-row p-4">
	            <div class="col-md-8">
	                <!-- Include CSRF token -->
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                <div class="input-group">
	                    <input type="file" name="file" class="file-input form-control" id="file-input" onchange="updateFileName(this)">
	                    <label for="file-input" class="upload-label input-group-text">Choose File</label>
	                </div>
	                <div id="file-name-container"></div>
	            </div>
	            <input type="hidden" name="facility" value="${firstHospital.value}" />
	            <div class="col-md-4 text-right">
	                <button type="submit" class="upload-button btn btn-primary">Upload</button>
	            </div>
	        </div>
	        <div class="form-row p-4">
	            <div class="col-md-12">
	                <span class="recommendation-message">For optimal performace, we recommend using Batch Search <b>outside of regular working hours</b></span>
	            </div>
	        </div>
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
		<div class="row m-3 pt-5 justify-content-center">
		    <div id="hpiiResult" class="col-md-10">
		  	<c:choose>
				<c:when test="${not empty hpiiList}">
				<!-- Display data if hpiiList; is not empty -->
				<p class="text-primary text-left font-weight-bold">Success rate of retreiving HPI-Is <span class="text-secondary small">(<c:out value="${success}"/> out of <c:out value="${total}"/>)</span> </p>
				<div class="progress mb-5">
					<div class="progress-bar" id="succesRateBar" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber type='percent' maxIntegerDigits='3' value='${(success / total)}' />;">
						<fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(success / total)}" />
					</div>
				</div>
				<div class="stats">
					<!-- Display table if hpiiList is not empty -->
					<table id="hpiiTable" class="display">
						<!-- Table headers -->
						<thead>
							<tr>
								<th class="text-center" style="width: 20%">Last Name</th>
								<th class="text-center" style="width: 20%">First Name</th>
								<th class="text-center" style="width: 20%">Ahpra Number</th>
								<th class="text-center" style="width: 20%">HPI-I</th>
								<th class="text-center" style="width: 15%;">Retrieved Time</th>
								<th class="text-center" style="width: 5%">Status</th>
							</tr>
						</thead>
						<!-- Table body -->
						<tbody>
						<!-- Iterate over hpiiList -->
						<c:forEach items="${hpiiList}" var="record">
							<tr>
								<!-- Display record if not empty -->
								<c:if test="${not empty record}">
									<td class="small ellipsis"><span><c:out value="${record.lastName}" /></span></td>
									<td class="small ellipsis"><span><c:out value="${record.firstName}" /></span></td>
									<td class="small ellipsis"><span><c:out value="${record.ahpra}" /></span></td>
									<td class="small ellipsis"><span><c:out value="${record.hpii}" /></span></td>
									<td class="small ellipsis"><span><c:out value="${record.createDate}" /></span></td>
									<td class="small ellipsis text-center"><span><c:out value="${record.status}" /></span></td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				</c:when>
				<c:otherwise>
					<!-- Display warning message if hpiiList is empty -->
					<div class="alert alert-warning" role="alert">
						<h5>
						<i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;Please upload <strong>CSV</strong> file with <strong>Practitioner Information</strong>.
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
<!-- Loading Spinner -->
<div id="spinner" class="text-center modal fade">
	<img src="${pageContext.request.contextPath}/images/loading.gif" style="width: 100px; height: auto;" alt="Processing...">
</div>
		
