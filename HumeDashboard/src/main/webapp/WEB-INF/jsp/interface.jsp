<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Hume Dashboard</title>    
    <!-- JQuery JavaScript -->  
    <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script> 
    <!-- Core plugin JavaScript-->
	<script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/fontfamily.css" rel="stylesheet">
	<!-- Bootscrtap Core CSS -->
	<link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- DataTables CSS -->
	<link href="${pageContext.request.contextPath}/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
	<!-- DataTables Responsive CSS -->
	<link href="${pageContext.request.contextPath}/vendor/datatables/responsive.bootstrap4.min.css" rel="stylesheet">
	<!-- Custom styles for this template-->
    <!-- <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">  -->
	<link href="${pageContext.request.contextPath}/css/interface.css" rel="stylesheet">
 
 	<!-- Chart.js -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/chart.js/Chart.min.css">
	
	<!-- DataTables JavaScript -->
	
	<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap4.min.js"></script>
	
	      
	<!-- Bootstrap core JavaScript-->
	<script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<!-- Page level plugins -->
	<script src="${pageContext.request.contextPath}/vendor/chart.js/Chart.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/chart.js/Gauge.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


<style>

/* Compact table style */
.compact-table td,
.compact-table th {
    padding: 0.4rem 0.5rem;
    font-size: 0.78rem;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    vertical-align: middle;
}

/* Optional: cleaner font for headers */
.compact-table thead th {
    font-size: 0.82rem;
    font-weight: 600;
    background-color: #007bff;
    color: white;
    text-transform: uppercase;
}

/* Subtle hover for rows */
.compact-table tbody tr:hover {
    background-color: #f0f8ff;
    transition: background-color 0.2s ease;
}

/* HL7 message pop-up size*/
.modal-xl {
  max-width: 1140px;
}

#messageDetail {
  white-space: pre-wrap;
  word-wrap: break-word;
  font-family: monospace; /* Optional: better for HL7 readability */
}

/* Thicker and rounded modal border */

.modal-header {
  background-color: #007bff !important;
  border-bottom: 2px solid #0056b3 !important;

  /* Remove any gap/margin/padding at the top */
  margin-top: 0;
  padding-top: 1.25rem;

  /* Match modal content radius */
  border-top-left-radius: 5px !important;
  border-top-right-radius: 5px !important;

  /* Clip child content to prevent background leak */
  overflow: hidden;
}

.modal-content {
  background-color: #ffffff !important;
  overflow: hidden;
  border: 3px solid #007bff !important;
  border-radius: 15px !important; /* Already done — keep this */
  box-shadow: 0 0 20px rgba(0, 123, 255, 0.3);

  /* ensure full top corner rounding */
  border-top-left-radius: 15px !important;
  border-top-right-radius: 15px !important;
}

.modal-footer {
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
}

.modal-dialog {
  border-radius: 15px;
  overflow: hidden;
}

.modal-header .close {
  color: white;
  opacity: 1;
  text-shadow: none;
  font-size: 1.4rem;
  font-weight: bold;
}

.modal-header .close:hover {
  color: #dceeff;
  opacity: 1;
}


.scroll-to-top {
  position: fixed;
  right: 1.5rem;
  bottom: 1.5rem;
  z-index: 1030;
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #007bff, #3399ff);
  color: #fff;
  border: none;
  border-radius: 50%;
  box-shadow: 0 4px 10px rgba(0, 123, 255, 0.3);
  transition: all 0.3s ease;
}

.scroll-to-top:hover {
  background: linear-gradient(135deg, #0056b3, #1e90ff);
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(0, 123, 255, 0.45);
  color: white;
  text-decoration: none;
}

</style>
    
</head>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

<!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">
    <!-- Main Content -->
    <div id="content" class="background-color">
    <!-- Topbar -->
    <jsp:include page="include/header.jsp"/>
	<!-- End of Topbar -->

<!-- Begin Page Content -->
<div class="container-fluid">

<!-- Contents Row -->
<!-- ######################################################### -->
<!-- Interface Info : Inbound & Outbound Sections -->
<!-- ######################################################### -->
<div class="row">
	<div class="col-12">
		<div class="card shadow mb-4">
        	<!-- <h6 class="card-header py-3 d-flex flex-row align-items-center justify-content-between m-0 font-weight-bold text-primary">Hume HSIE CommPoints</h6> -->
            <!-- Card Body -->
            <div class="card-body">
           		<div class="row">
           			<!-- Inbound -->
            		<div class="col-6">
						<div class="card h-100">
							<div class="card-header text-primary" style="font-size: 16px;">
								<i class="fas fa-sign-in-alt"></i>&nbsp;&nbsp;Inbound CommPoints
							</div>
							<div class="card-body">
								<!-- iterate inbound list -->		           											
								<div class="row d-flex align-items-center justify-content-center ellipsis h6">
									<c:forEach items="${inboundList}" var="inbound">
										<!-- decide color based on connection condition -->
										<c:set var="borderClass" value="interface-border-warning" />
										<c:set var="headerClass" value="interface-header-warning" />
										<c:set var="textClass" value="interface-text-warning" />
										<c:if test="${inbound.connectionCount >= 1}">
										    <c:set var="borderClass" value="interface-border-success" />
										    <c:set var="headerClass" value="interface-header-success" />
										    <c:set var="textClass" value="interface-text-success" />
										</c:if>
										<div class="card ${borderClass} col-sm-12 col-md-6 col-lg-4 col-xl-3 col-xxl-2 mx-3 mt-3" onclick="displayLog('${inbound.name}')">
										<!--<c:set var="formattedName" value="${fn:replace(fn:substringAfter(inbound.name, 'CP.tcp_'), '_IO', '')}" />-->
										<c:choose>
										    <c:when test="${fn:contains(inbound.name, 'CP.tcp_HRA From ')}">
										        <c:set var="formattedName" value="${fn:replace(fn:substringAfter(inbound.name, 'CP.tcp_HRA From '), '_IO', '')}" />
										    </c:when>
										    <c:otherwise>
										        <c:set var="formattedName" value="${fn:replace(fn:substringAfter(inbound.name, 'CP.tcp_'), '_IO', '')}" />
										    </c:otherwise>
										</c:choose>										
										<div class="card-header ${headerClass} ellipsis" title="${formattedName}">
										    <c:out value="${formattedName}"/>
										</div>
										<div class="card-body d-flex justify-conent-center align-items-center">
											<table width="100%">
												<tbody>
													<tr>
														<td class="text-center" colspan="2"><i class="fas fa-sign-in-alt"></i></td>
														<td class="text-center" colspan="2"><i class="fas fa-sign-out-alt"></i></td>
													</tr>
													<tr class="text-center small">
														<td>
														<c:out value="${inbound.receivedCount}"/>
														</td>
														<td colspan="2"><i class="far fa-check-circle ${textClass}" data-toggle="tooltip" title="Processed Messages"></i></td>
														<td>
														<c:out value="${inbound.sentCount}"/>
														</td>															
													</tr>
													<tr class="text-center small">
														<td>
														<c:out value="${inbound.inQueueSize}"/>
														</td>
														<td colspan="2"><i class="far fa-comment-alt ${textClass}" data-toggle="tooltip" title="Current Message Count"></i></td>
														<td>
														<c:out value="${inbound.outQueueSize}"/>
														</td>															
													</tr>
													<tr class="text-center small">
														<td>
														<c:out value="${inbound.inputIdleTime}"/>
														</td>
														<td colspan="2"><i class="far fa-hourglass ${textClass}" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
														<td>
														<c:out value="${inbound.outputIdleTime}"/>
														</td>															
													</tr>
												</tbody>
											</table>
										</div>											
									</div>
									</c:forEach>
								</div>
							</div>
						</div>
			        </div> <!--  end of Inbound -->			        
			 		<!-- Outbound -->
			 		<div class="col-6">
						<div class="card h-100">
							<div class="card-header text-primary" style="font-size: 16px;">
								<i class="fas fa-sign-out-alt"></i>&nbsp;&nbsp;Outbound CommPoints
							</div>
							<div class="card-body">
								<!-- iterate inbound list -->		           											
								<div class="row d-flex align-items-center justify-content-center ellipsis h6">
									<c:forEach items="${outboundList}" var="outbound">
										<!-- decide color based on connection condition -->
										<c:set var="borderClass" value="interface-border-warning" />
										<c:set var="headerClass" value="interface-header-warning" />
										<c:set var="textClass" value="interface-text-warning" />
										<c:if test="${outbound.connectionCount >= 1}">
										    <c:set var="borderClass" value="interface-border-success" />
										    <c:set var="headerClass" value="interface-header-success" />
										    <c:set var="textClass" value="interface-text-success" />
										</c:if>
										<div class="card ${borderClass} col-sm-12 col-md-6 col-lg-4 col-xl-3 col-xxl-2 mx-3 mt-3" onclick="displayLog('${outbound.name}')">
										<!-- 
										<c:set var="formattedName" value="${fn:replace(fn:substringAfter(outbound.name, 'CP.tcp_HRA To '), '_OI', '')}" />
										-->
										<c:choose>
										    <c:when test="${fn:contains(outbound.name, 'CP.tcp_HRA To ')}">
										        <c:set var="formattedName" value="${fn:replace(fn:substringAfter(outbound.name, 'CP.tcp_HRA To '), '_OI', '')}" />
										    </c:when>
										    <c:otherwise>
										        <c:set var="formattedName" value="${outbound.name}" />
										    </c:otherwise>
										</c:choose>
										<div class="card-header ${headerClass} ellipsis" title="${formattedName}">										
										    <c:out value="${formattedName}"/>
										</div>
										<div class="card-body d-flex justify-conent-center align-items-center">
											<table width="100%">
												<tbody>
													<tr>
														<td class="text-center" colspan="2"><i class="fas fa-sign-in-alt"></i></td>
														<td class="text-center" colspan="2"><i class="fas fa-sign-out-alt"></i></td>
													</tr>
													<tr class="text-center small">
														<td>
														<c:out value="${outbound.receivedCount}"/>
														</td>
														<td colspan="2"><i class="far fa-check-circle ${textClass}" data-toggle="tooltip" title="Processed Messages"></i></td>
														<td>
														<c:out value="${outbound.sentCount}"/>
														</td>															
													</tr>
													<tr class="text-center small">
														<td>
														<c:out value="${outbound.inQueueSize}"/>
														</td>
														<td colspan="2"><i class="far fa-comment-alt ${textClass}" data-toggle="tooltip" title="Current Message Count"></i></td>
														<td>
														<c:out value="${outbound.outQueueSize}"/>
														</td>															
													</tr>
													<tr class="text-center small">
														<td class="ellipsis">
														<c:out value="${outbound.inputIdleTime}"/>
														</td>
														<td colspan="2"><i class="far fa-hourglass ${textClass}" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
														<td class="ellipsis">
														<c:out value="${outbound.outputIdleTime}"/>
														</td>															
													</tr>
												</tbody>
											</table>
										</div>											
									</div>
									</c:forEach>
								</div>
							</div>
						</div>
			        </div> <!-- end of Outbound -->
       	 		</div>
       	 	</div> <!-- end of main card body -->
		</div><!-- end of card-->
	</div>
</div><!-- end of first row for Engine Health -->

<!-- Log File Row -->
<!-- ######################################################### -->
<!-- Log File Info -->
<!-- ######################################################### -->

<div class="row">
	<div class="col-xl-12 col-lg-12">
		<div class="card shadow mb-4">
        	<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                 <h6 class="m-0 text-primary" id="logSectionTitle">Log Details</h6>
            </div>
            <!-- Card Body -->
    		<div class="card-body">
    			<div class="table-responsive">
        			<div class="col-sm-12">
	                 	<table class="table table-bordered rounded table-hover table-striped shadow-sm compact-table dataTable" id="logTable" role="grid" aria-describedby="dataTable_info">
	                    	<thead>
	                        	<tr role="row">
	                        		<th class="text-center align-middle text-white">Date Time</th>
	                        		<th class="text-center align-middle text-white">Sending App</th>
									<th class="text-center align-middle text-white">Receiving App</th>
									<th class="text-center align-middle text-white">Msg Type</th>
									<th class="text-center align-middle text-white">Msg Event</th>
	                        		<th class="text-center align-middle text-white">Msg Cont Id</th>
									<th class="text-center align-middle text-white">Patient UR</th>
									<th class="text-center align-middle text-white">Given Name</th>
									<th class="text-center align-middle text-white">Family Name</th>
									<th class="text-center align-middle text-white">Gender</th>
									<th class="text-center align-middle text-white">Visit ID</th>
									<th class="text-center align-middle text-white">HL7 Msg</th>
	                        	</tr>
	                        </thead>
	                     </table>
   					</div>    				
    			</div>
    		</div>
		</div><!-- end of card-->
	</div>
</div><!-- end of first row for CommPoints Contents -->

</div><!-- End Page Content -->

</div><!-- End of Main Content -->

<!-- Footer -->
<jsp:include page="include/footer.jsp"/>
<!-- End of Footer -->

</div>
<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-double-up fa-lg"></i>
</a>


<!-- Pop up HL7 Message -->
<div class="modal fade" id="layerpopMessage" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle">
  <div class="modal-dialog modal-xl" role="document">
    <div class="modal-content">
      <!-- header -->
      <div class="modal-header bg-primary">
        <!-- header title -->
        <h4 class="modal-title text-white w-100 text-center" id="messageName">Header</h4>
        <!-- x button -->
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
  			<span aria-hidden="true">&times;</span>
		</button>
      </div>
      <!-- body -->
      <div class="modal-body" id="messageDetail" style="word-wrap: break-word;">
      	Loading...
      </div>
      <!-- Footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>










<!-- DataTables Initialization -->
<script>
$(document).ready(function() {
      $('#logTable').DataTable({
          "paging": false,
          "searching": false,
          "ordering": false,
          "info": false,
          "responsive": true,
          "autoWidth": false
      });
});

//////////////////////////////////////////////////////
//
// Display logs for Commpoint
//
/////////////////////////////////////////////////////
function displayLog(service){
    var encodedService = encodeURIComponent(service);
 	// update `logSectionTitle` by ID and update with service parameter
	var cleanService = service.replace(/^CP\.tcp_/, '');
	$('#logSectionTitle').text("Log Details for " + cleanService);
    $.ajax({
        url : '${pageContext.request.contextPath}/getLogSection/' + encodedService,
        method: "GET",
        success: function(data) {
            var tbody = $('#logTable tbody');
            tbody.empty(); // Clear existing rows
            $.each(data, function(index, log) {
                console.log(log);
                var row = $('<tr>');
                row.append('<td class="text-center align-middle">' + log.lastUpdate + '</td>');
                row.append('<td class="text-left align-middle">' + log.sendingApp + '</td>');
                row.append('<td class="text-left align-middle">' + log.receivingApp + '</td>');
                row.append('<td class="text-center align-middle">' + log.msgType + '</td>');
                row.append('<td class="text-center align-middle">' + log.msgEvent + '</td>');
                row.append('<td class="text-left align-middle">' + log.msgId + '</td>');
                row.append('<td class="text-left align-middle">' + log.patientUr + '</td>');
                row.append('<td class="text-left align-middle">' + log.patientFirstName + '</td>');
                row.append('<td class="text-left align-middle">' + log.patientLastName + '</td>');

               	var gender = log.patientGender;
               	var genderIcon = "";
               	// Check gender is not null/empty, then compare
           		if (gender && gender.trim() !== "") {
           		    if (gender.toLowerCase() === 'm') {
           		        genderIcon = "<i class='fa fa-male text-primary fa-lg' title='Male'></i>"; // man
           		    } else if (gender.toLowerCase() === 'f') {
           		        genderIcon = "<i class='fa fa-female text-danger fa-lg' title='Female'></i>"; // woman
           		    } else {
           		        genderIcon = "<i class='fa fa-question text-muted fa-lg' title='Other'></i>"; // other
           		    }
           		} else {
           		    genderIcon = "<i class='fa fa-question text-secondary fa-lg' title='Unknown'></i>"; // unknown
           		}
               	                row.append('<td class="text-center align-middle">' + genderIcon + '</td>');
                row.append('<td class="text-left align-middle">' + log.visitingId + '</td>');
                //row.append('<td class="text-center align-middle" data-target="#layerpopMessage" data-toggle="modal" style="cursor: pointer;" onclick="showHL7Message(\'' + log.auditId + '\')"><i class="far fa-envelope fa-lg"></i></td>');
                row.append('<td class="text-center align-middle" style="cursor: pointer;" onclick="showHL7Message(\'' + log.auditId + '\')"><i class="far fa-envelope fa-lg"></i></td>');
                tbody.append(row);
            });

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Error : ' + errorThrown);
        }
    });
}


//////////////////////////////////////////////////////
//
// Pop up the original HL7 message with Ack
//
/////////////////////////////////////////////////////
function showHL7Message(id){

	
	$('#messageName').html("Original HL7 Message" + "&nbsp;&nbsp;&nbsp;" + "<i class='far fa-envelope-open fa-lg'></i>");
    $.ajax({
        url : '${pageContext.request.contextPath}/logDetail/' + id,
        method: "GET",
        success: function(data) {
            console.log(data);
            // Insert new lines before and after the Ack line
            const formatted = data.replace(/(=+ Ack =+=+)/, '\n$1\n');
            $('#messageDetail').text(formatted); // use `.text()` instead of `.html()` to preserve formatting
            $('#layerpopMessage').modal('show');
        	

            
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Error : ' + errorThrown);
        }
    });
}
</script>

       
</body>

</html>