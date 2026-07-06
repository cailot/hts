<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
   	
    <style>
    .dt-buttons{
    float: right !important;
	}
    
    .dataTables_filter{
    float: left !important;
	}
	
	.center-cell { 
		display: table-cell;
		text-align:center !important; 
		vertical-align:middle !important; 
		font-size: larger;
	}
	
	h4 {
		text-align: center !important;
		margin: 0 auto;
	}
	
	.ellipsis {
    	position: relative;
	}
	.ellipsis:before {
	    content: '&nbsp;';
	    visibility: hidden;
	}
	.ellipsis span {
	    position: absolute;
	    left: 0;
	    right: 0;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
    </style>
    
    
	<script type="text/javascript">

	$(document).ready( function () {

		$.fn.dataTable.moment('DD/MM/YYYY HH:mm:ss');

		
	    $('#auditTable').DataTable({
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
	        columnDefs: [ 
	        	{ width: '10%', targets: 0 },
	    		{ width: '10%', targets: 1 },
	    		{ width: '10%', targets: 2 },
	    		{ width: '10%', targets: 3 },
	    		{ width: '15%', targets: 4 },
	    		{ width: '15%', targets: 5 },
	    		{ width: '10%', targets: 6 },
	    		{ width: '15%', targets: 7 },
	    		{ width: '5%', targets: 8 },
		        {
	            'targets': [8],
	            'orderable': false,
	         	}
		    ],
	         'order' : [[0, 'desc']]
		});

		
	

	} );

	
	var today = new Date();
		$(function() {
			$("#fromDate").datepicker({
				dateFormat : 'dd/mm/yy',
				minDate: "-6w",
				setDate : true,
				maxDate : new Date(today.setDate(today.getDate())), 
				numberOfMonths : 1,
				onClose : function(selectedDate) {
					$("#toDate").datepicker("option", "minDate", selectedDate);
				}
			});
			$("#toDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : false,
				maxDate : new Date(today.setDate(today.getDate())), 
				numberOfMonths : 1,
				onClose : function(selectedDate) {
					$("#fromDate").datepicker("option", "maxDate", selectedDate);
				}
			});
		});

		function showPatient(auditId){

			// initial page with empty data
			$('#patientInfoHeader').html("Searching Info ...."  + "&nbsp;&nbsp;&nbsp;" + "<i class='fa fa-info-circle fa-lg'></i>");
			$('#patientName').html("");
		   	$('#patientDob').html("");
		   	$('#patientGender').html("");
		   	$('#patientAddress').html("");
		   	$('#patientUrn').html("");
		   	$('#patientIhi').html("");
		   	$('#patientIhiStatus').html("");
		   	$('#patientMedicare').html("");
		   	$('#patientDva').html("");
			
			$.ajax({
			     type: "GET",
			     url: 'patient?id=' + auditId,
			     data: {},
			     success: function(data) {
				     var obj = JSON.parse(data);

					 if(obj.firstName == null && obj.lastName == null){ // no matching data
						$('#patientInfoHeader').html("No Matching Patient Info"  + "&nbsp;&nbsp;&nbsp;" + "<i class='fa fa-info-circle fa-lg'></i>");
						 	
					}else{ // patient info
						$('#patientInfoHeader').html("Patient Information"  + "&nbsp;&nbsp;&nbsp;" + "<i class='fa fa-info-circle fa-lg'></i>");
					   	$('#patientName').html(obj.firstName+ " " + obj.lastName);
					   	$('#patientDob').html(obj.dob);
					   	if(obj.gender == '1'){
					   		$('#patientGender').html("<i class='fa fa-male text-primary fa-lg'></i>");
						}else if(obj.gender == '2'){
					   		$('#patientGender').html("<i class='fa fa-female text-danger fa-lg'></i>");
						}else{
							$('#patientGender').html("<i class='fa fa-question fa-lg'></i>");
						}	
					   	$('#patientAddress').html(obj.address);
					   	$('#patientUrn').html(obj.urn);
					   	$('#patientIhi').html(obj.ihi);
					   	$('#patientIhiStatus').html(obj.ihiStatus);
					   	$('#patientMedicare').html(obj.medicare);
					   	$('#patientDva').html(obj.dva);
					}
			     }
			  });
		}

</script> 



<div style="width: 85%; margin:0 auto;">
	<div class="row m-3 justify-content-center">
		<div id="searchCondition">
			<form method="get" action="${pageContext.request.contextPath}/audit" class="form-inline form-control-row">
		    <div class="dropdown pr-3" style="display: inline-block;">
		    <div class="btn-group btn-group-inline">
		        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown">
		            Select Hospital
		        </button>
		        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		        	<c:forEach items="${hospitals}" var="hospital">
						<a class="dropdown-item" href="#">${hospital.display}</a>
					</c:forEach>
		        </div>
		    </div>
		        <input type="hidden" name="siteName" id="siteName" value="" />
		    </div>
			<script>
				$(".dropdown-menu a ").click(function () {
					var selected = $(this).text();
					$(this).parents(".btn-group-inline").find('.btn').text(selected);
					document.querySelector("#siteName").value = selected;
				});
			</script>
			<div class="form-group pr-2">
				<input type="text" class="form-control" id="patientSearch" name="patientSearch"
					placeholder="Patient Information" />
			</div>
			<div class="form-group pr-2">
				<input type="text" class="form-control" id="fromDate" name="fromDate" value="${fromDate}"
					placeholder="From" readonly />
			</div>
			<div class="form-group pr-3">
				<input type="text" class="form-control" id="toDate" name="toDate" value="${toDate}"
					placeholder="To" readonly />
			</div>
			<button type="submit" class="btn btn-primary" id="searchDate"  onclick="return validateFormWithPatientInfo();">Search</button>
			<input type="hidden" name="searchCheck" value="true" />
			</form>
		</div>
	</div>
	<c:choose>
		<c:when test="${auditData != null}">
			<div class="stats">
			<table id="auditTable" class="display">
				<thead>
					<tr>
						<th colspan="9" style="text-align: center;"> 
							<c:choose>
								<c:when test="${auditHospital != null}">
									<div class="h6 font-italic">
										Audit tracking 
										<c:if test="${not empty patientInfo}">
										for patient : <span class="text-primary font-weight-bold"><c:out value="${patientInfo}" /></span> 
										</c:if>
										at <span class="text-primary font-weight-bold"><c:out value="${auditHospital}" /></span> from <span class="text-primary font-weight-bold"><c:out value="${auditFromDate}"/></span> to <span class="text-primary font-weight-bold"><c:out value="${auditToDate}"/></span> 
									</div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</th>
					</tr>
					<tr>
						<th>Date</th>
						<th>Patient Name</th>
						<th>MRN</th>
						<th>IHI</th>
						<th>Service Name</th>
						<th>Document Name</th>
						<th>Accessed By</th>
						<th>Clinician Id</th>
						<th>Patient</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${auditData}" var="audits">
						<tr>
							<td class="small ellipsis"><span><c:out value="${audits.dateCreated}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.firstName} ${audits.lastName}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.urNumber}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.ihi}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.serviceName}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.documentName}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.accessBy}" /></span></td>
							<td class="small ellipsis"><span><c:out value="${audits.clinicianId}" /></span></td>
							<td class="center-cell">
								<i class="fa fa-user text-info" onclick="showPatient('${audits.patientMasterId}')" style="cursor:hand;" data-target="#layerpopPatient" data-toggle="modal"></i>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</c:when>
		<c:otherwise>
			<div class="container">
				<div class="alert alert-warning" role="alert">
					<h5>
						<i class="fa fa-info-circle fa-lg"></i>&nbsp;&nbsp;Please select <strong>Hospital</strong> and <strong>Date Range</strong> with/without <strong>Patient Information</strong>.
					</h5>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

</div>




<div id="validation-error" class="modal fade">
	<div class="modal-dialog">
		<div class="alert alert-block alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<i class="fa fa-frown-o fa-lg"></i>&nbsp;&nbsp;Please make sure <b>Hospital</b>, <b>Patient Information</b>, <b>FromDate</b> & <b>ToDate</b> within last month filled in
		</div>
	</div>
</div>

<div class="modal fade" id="layerpopPatient" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
      <!-- header -->
      <div class="modal-header">
        <!-- header title -->
        <h4 class="modal-title text-info" id="patientInfoHeader">Header</h4>
        <!-- x button -->
        <button type="button" class="close" data-dismiss="modal">×</button>
        
      </div>
      <!-- body -->
      <div class="modal-body" id="patientInfoDetail" style="word-wrap: break-word;">
      	<div id="demographicInfo" align="left" class="block">
      		<section class="fieldset rounded border-info">
      			<header class="text-info font-weight-bold">Name</header>
      			<div id="patientName" name="patientName" style="width: 100%;"></div>
			</section>
			<br>
      		<section class="fieldset rounded border-info">
      			<header class="text-info font-weight-bold">Date Of Birth & Gender</header>
      			<table style="border-spacing:5px; border-collapse:initial; width:100%;">
					<tr>
						<td><div id="patientDob" name="patientDob" style="width: 60%;"></div></td>
						<td>
						<div id="patientGender" name="patientGender" style="width: 40%;"></div>
						</td>
					</tr>
				</table>
			</section>
			<br>
			<section class="fieldset rounded border-info">
      			<header class="text-info font-weight-bold">Address</header>
      			<div id="patientAddress" name="patientAddress" style="width: 100%;"></div>
			</section>
			<br>
			<section class="fieldset rounded border-info">
      			<header class="text-info font-weight-bold">Patient ID</header>
      			<table style="border-spacing:5px; border-collapse:initial; width:100%;">
				<tr>
					<td style="width: 20%;">MRN : </td>
					<td style="width: 80%;" colspan="2"><div id="patientUrn" name="patientUrn" style="width: 100%;"></div></td>
				</tr>
				<tr>
					<td style="width: 20%;">IHI : </td>
					<td style="width: 40%;"><div id="patientIhi" name="patientIhi"></div></td>
					<td style="width: 40%;"><div id="patientIhiStatus" name="patientIhiStatus"></div></td>
				</tr>
				<tr>
					<td style="width: 20%;">Medicare : </td>
					<td style="width: 80%;" colspan="2"><div id="patientMedicare" name="patientMedicare"></div></td>
				</tr>
				<tr>
					<td style="width: 20%;">DVA : </td>
					<td style="width: 80%;" colspan="2"><div id="patientDva" name="patientDva"></div></td>
				</tr>
				</table>
			</section>
		</div>
      	
      </div>
      <!-- Footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
	



