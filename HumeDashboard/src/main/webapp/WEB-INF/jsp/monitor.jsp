<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="au.org.hts.dashboard.util.HumeDashboardUtils" %>

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
	<!-- Bootscrtap Core CSS
	<link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
	<link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap5.min.css" rel="stylesheet">
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
  width: 3rem;
  height: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  background-color: #C63663 !important;
  border-radius: 50%;
  line-height: 46px;
  transition: all 0.3s ease-in-out;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.scroll-to-top:hover {
  background-color: #4a4a2f;
  color: white;
  text-decoration: none;
}

.session-timeout-bar {
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1050;
        display: none;
}

.main-content {
    transition: padding-top 0.3s; /* Smooth transition for padding */
}

.tooltip {
z-index: 1050 !important; /* Default for Bootstrap */
}


@media (min-width: 1800px) {
  .col-xxxl-2 {
    flex: 0 0 auto;
    width: 16.6667%; /* 100 / 6 columns per row */
  }
}

</style>



<script type="text/javascript">


</script>
    
</head>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

<!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">
	<!--Session Timeout -->
    <div class="session-timeout-bar alert alert-danger alert-dismissible fade show" role="alert">
		<div style="display: flex; justify-content: space-between; align-items: center;">
			<div>
				<strong>Session Timeout</strong>
				<span class="small ml-3">Your session's been inactive for a while, so we've logged you off from Hume Dashboard to keep your accounts and details safe.</span>
			</div>
			<a href="${pageContext.request.contextPath}/login">Return to the log on page</a>
		</div>
    </div>
    <!-- Main Content -->
    <div id="content main-content" class="background-color">
	    <!-- Topbar -->
	    <jsp:include page="include/header.jsp"/>
		<!-- Begin Monitor Page Content -->
		<div class="container-fluid">
		
			<!-- ######################################################### -->
			<!-- Engine Info : Total msg count & last update with Pie chart -->
			<!-- ######################################################### -->
			<div class="row">
				<div class="col-xl-12 col-lg-12">
					<div class="card shadow mb-4">
			        	<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
			            	<h6 class="m-0 text-primary">Hume HSIE Application Server</h6>
			            </div>
			            <!-- Card Body -->
			            <div class="card-body">
			           		<div class="row col-xl-12 col-lg-12">
			           			
			           			<!-- Server Information -->
			            		<div class="col-xl-3 col-md-3 mb-4">
									<div class="card border-primary shadow-sm rounded-3 h-100">
										<div class="card-header bg-primary text-white">
											<i class="fa fa-info-circle"></i>&nbsp;&nbsp;Server Information
										</div>
										<div class="card-body d-flex align-items-center justify-content-center">
										<c:set var="aDisk1" value="${Math.round(engineInfo.availableDisk/1024)}" />
										<c:set var="tDisk1" value="${Math.round(engineInfo.totalDisk/1024)}" />					
										<table class="table table-sm table-borderless mb-0 mx-3">
											<tbody>
											  <tr>
											    <td class="text-left">Engine Uptime</td>
											    <td class="text-end text-primary"><strong>
											    ${HumeDashboardUtils.removeWhitespaceAfterDigits(engineInfo.uptime)}
											    </strong></td>
											  </tr>
											  <tr>
											    <td class="text-left">Available Space</td>
											    <td class="text-end text-primary"><strong>${aDisk1} GB</strong></td>
											  </tr>
											  <tr>
											    <td class="text-left">Total Space</td>
											    <td class="text-end text-primary"><strong>${tDisk1} GB</strong></td>
											  </tr>
											  <tr>
											    <td class="text-left">Disk Allocated</td>
											    <td class="text-end text-primary"><strong><fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(tDisk1-aDisk1) / tDisk1}" /></strong></td>
											  </tr>	  
											  <tr>
											    <td colspan="2">
											    	<div class="progress">
										          		<div class="progress-bar progress-bar-success" role="progressbar" style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(tDisk1-aDisk1) / tDisk1} " />;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
													</div>	    
											    </td>
											  </tr>
											</tbody>
										</table>
										</div>
									</div>
						        </div>								
			           												           			
			            		<!-- CPU -->
			            		<div class="col-xl-3 col-md-3 mb-4">
									<div class="card border-primary shadow-sm rounded-3 h-100">
										<div class="card-header bg-primary text-white">
											<i class="fas fa-server"></i>&nbsp;&nbsp;CPU
										</div>
										<div class="card-body d-flex align-items-center justify-content-center">
											<div class="card-text">
												<canvas id="cpuChart1" ></canvas>
											</div>
										</div>
									</div>
							   	</div>
			            
								<!-- Memory -->            
					            <div class="col-xl-6 col-md-6 mb-4">
					        	 	<div class="card border-primary">
					            		<div class="card-header bg-primary text-white ellipsis">
			               					<i class="fas fa-chart-line"></i>&nbsp;&nbsp;Memory Timeline
			           					</div>
			           					<div class="card-body">
			               					<div class="chart-area">
			                   					<canvas id="timelineChart1"></canvas>
			               					</div>
			            				</div>
			       					</div>
			       				 </div>
			        	 		
			       	 		</div>
			       	 	</div> <!-- end of main card body -->
					</div><!-- end of card-->
				</div>
			</div><!-- end of first row for Engine Health -->
		
			<!-- Contents Row -->
			<!-- ######################################################### -->
			<!-- Interface Info : Inbound & Outbound Sections -->
			<!-- ######################################################### -->
			<div class="row">
				<div class="col-12">
					<div class="card shadow mb-4">
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
													<c:choose>
													    <c:when test="${inbound.state eq 'STOPPED'}">
															<c:set var="borderClass" value="interface-border-danger" />
													   		<c:set var="headerClass" value="interface-header-danger" />
													    	<c:set var="textClass" value="interface-text-danger" />													   
													    </c:when>
													    <c:otherwise>
															<c:if test="${outbound.connectionCount >= 1}">
															    <c:set var="borderClass" value="interface-border-success" />
															    <c:set var="headerClass" value="interface-header-success" />
															    <c:set var="textClass" value="interface-text-success" />
															</c:if>
													    </c:otherwise>
													</c:choose>
													<div class="card ${borderClass} col-12 col-sm-6 col-md-4 col-lg-3 col-xl-3 col-xxl-3 col-xxxl-2 m-3" onclick="displayLog('${inbound.name}')">													
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
													<div class="card-body d-flex justify-conent-center align-items-center" style="padding: 0.6rem;">
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
																	${HumeDashboardUtils.removeWhitespaceAfterDigits(inbound.outputIdleTime)}
																	</td>
																	<td colspan="2"><i class="far fa-hourglass ${textClass}" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
																	<td>
																	${HumeDashboardUtils.removeWhitespaceAfterDigits(inbound.outputIdleTime)}
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
											<!-- iterate outbound list -->		           											
											<div class="row d-flex align-items-center justify-content-center ellipsis h6">
												<c:forEach items="${outboundList}" var="outbound">
													<!-- decide color based on connection condition -->
													<c:set var="borderClass" value="interface-border-warning" />
													<c:set var="headerClass" value="interface-header-warning" />
													<c:set var="textClass" value="interface-text-warning" />
													<c:choose>
													    <c:when test="${outbound.state eq 'STOPPED'}">
															<c:set var="borderClass" value="interface-border-danger" />
													   		<c:set var="headerClass" value="interface-header-danger" />
													    	<c:set var="textClass" value="interface-text-danger" />													   
													    </c:when>
													    <c:otherwise>
															<c:if test="${outbound.connectionCount >= 1}">
															    <c:set var="borderClass" value="interface-border-success" />
															    <c:set var="headerClass" value="interface-header-success" />
															    <c:set var="textClass" value="interface-text-success" />
															</c:if>
													    </c:otherwise>
													</c:choose>
													<div class="card ${borderClass} col-12 col-sm-6 col-md-4 col-lg-3 col-xl-3 col-xxl-3 col-xxxl-2 m-3" onclick="displayLog('${outbound.name}')">
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
													<div class="card-body d-flex justify-conent-center align-items-center" style="padding: 0.6rem;">
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
																	${HumeDashboardUtils.removeWhitespaceAfterDigits(outbound.outputIdleTime)}
																	</td>
																	<td colspan="2"><i class="far fa-hourglass ${textClass}" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
																	<td class="ellipsis">
																	${HumeDashboardUtils.removeWhitespaceAfterDigits(outbound.outputIdleTime)}
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
			        			<!-- <div class="col-sm-12"> -->
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
			   					<!-- </div> -->    				
			    			</div>
			    		</div>
					</div><!-- end of card-->
				</div>
			</div><!-- end of row for logs Contents -->
	
		</div><!-- End Monitor Page Content -->

	</div><!-- End of Main Content -->

	<!-- Footer -->
	<jsp:include page="include/footer.jsp"/>

</div><!-- End of Content Wrapper -->

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


<script>

var sessionTimeout = 1000*60*15; // 10 mins for demonstration
var warningTime = 1000*60*15; // Show warning at 5 mins

var timeoutTimer;
var warningTimer;
var inactivityInterval;
var alertVisible = false;

function showSessionTimeoutBar() {
    var timeoutBar = document.querySelector('.session-timeout-bar');
    timeoutBar.style.display = 'block';
    document.querySelector('.main-content').style.paddingTop = timeoutBar.offsetHeight + 'px';
    alertVisible = true;
}

function resetSessionTimeout() {
    clearTimeout(timeoutTimer);
    clearTimeout(warningTimer);

    timeoutTimer = setTimeout(function() {
        // Perform session timeout actions, e.g., log out the user
    }, sessionTimeout);

    if (!alertVisible) {
        warningTimer = setTimeout(showSessionTimeoutBar, warningTime);
    }
}

function setupSessionExtension() {
    document.addEventListener('mousemove', resetInactivityInterval);
    document.addEventListener('keypress', resetInactivityInterval);
    document.addEventListener('click', resetInactivityInterval);
    document.addEventListener('scroll', resetInactivityInterval);
}

function resetInactivityInterval() {
    clearInterval(inactivityInterval);
    resetSessionTimeout(); // Reset the session timeout timers
    inactivityInterval = setInterval(function() {
        if (!alertVisible) {
            showSessionTimeoutBar();
        }
    }, sessionTimeout); // Restart the inactivity interval
}

// Initialize session timeout tracking
document.addEventListener('DOMContentLoaded', function() {
    setupSessionExtension();
    resetInactivityInterval(); // Initial setup
});




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


<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>
<script type="text/javascript">
    
 // Set new default font family and font color to mimic Bootstrap's default styling
    Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
    Chart.defaults.global.defaultFontColor = '#858796';

    function number_format(number, decimals, dec_point, thousands_sep) {
      // *     example: number_format(1234.56, 2, ',', ' ');
      // *     return: '1 234,56'
      number = (number + '').replace(',', '').replace(' ', '');
      var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
          var k = Math.pow(10, prec);
          return '' + Math.round(n * k) / k;
        };
      // Fix for IE parseFloat(0.55).toFixed(0) = 0;
      s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
      if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
      }
      if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
      }
      return s.join(dec);
    }


    ///////////////////////////////////////////////////////////////////
    //
    //		Gauge Graph for CPU
    //
    ///////////////////////////////////////////////////////////////////

    var cpuPercentage1 = ${cpuInfo};
	var ctx1 = document.getElementById("cpuChart1").getContext("2d");	
	new Chart(ctx1, {
		type : "tsgauge",
		data : {
			datasets : [ {
				backgroundColor : [ "#28A745", "#FFC107","#DC3545" ],
				borderWidth : 0,
				gaugeData : {
					value : cpuPercentage1,
					valueColor : "#000000"
				},
				gaugeLimits : [ 0, 30, 70, 100 ]
			} ]
		},
		options : {
			events : [],
			showMarkers : true
		}
	});

    ///////////////////////////////////////////////////////////////////
    //
    //		Line Graph for Memory
    //
    ///////////////////////////////////////////////////////////////////

    var jsonfile1 = ${memoryInfo}
	var times1 = jsonfile1.data.results.map(function(e) {
		return e.time;
	});
	var timeL1 = times1.map(time => {
		var hour =  time.substring(time.indexOf("T")+1, time.indexOf("T")+3);
		var minute = time.substring(time.indexOf("T")+3, time.indexOf("T")+5);
		return hour + ':' + minute;
	});
	var memories1 = jsonfile1.data.results.map(function(e) {
		return e.value;
	});
	var memoryL1 = memories1.map(memory => (parseInt(memory)/(1024*1024*1024)));


	//var maxMemory = Math.max(...memoryL1);
	//var dynamicMax = Math.ceil(maxMemory * 2) / 2;
	//dynamicMax += 0.5; // Optional padding
    
    // Area Chart Example
    var ctx1 = document.getElementById("timelineChart1");
    var myLineChart1 = new Chart(ctx1, {
      type: 'line',
      data: {
        labels: timeL1,
        datasets: [{
          label: "Memory",
          lineTension: 0.3,
          backgroundColor: "rgba(78, 115, 223, 0.05)",
          borderColor: "rgba(78, 115, 223, 1)",
          pointRadius: 3,
          pointBackgroundColor: "rgba(78, 115, 223, 1)",
          pointBorderColor: "rgba(78, 115, 223, 1)",
          pointHoverRadius: 3,
          pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
          pointHoverBorderColor: "rgba(78, 115, 223, 1)",
          pointHitRadius: 10,
          pointBorderWidth: 2,
          data: memoryL1,
        }],
      },
      options: {
        maintainAspectRatio: false,
        layout: {
          padding: {
            left: 10,
            right: 25,
            top: 25,
            bottom: 0
          }
        },
        scales: {
          xAxes: [{
            time: {
              unit: 'date'
            },
            gridLines: {
              display: false,
              drawBorder: false
            },
            ticks: {
              maxTicksLimit: 10 // every 3 mins
            }
          }],
          yAxes: [{
            ticks: {
              maxTicksLimit:5,//5
              //min: 1,
              //max: dynamicMax,
              //stepSize: 0.5,
              padding: 10,
              // Include a dollar sign in the ticks
              callback: function(value, index, values) {
                return number_format(value);
              }
            },
            gridLines: {
              color: "rgb(234, 236, 244)",
              zeroLineColor: "rgb(234, 236, 244)",
              drawBorder: false,
              borderDash: [2],
              zeroLineBorderDash: [2]
            }
          }],
        },
        legend: {
          display: false
        },
        tooltips: {
          backgroundColor: "rgb(255,255,255)",
          bodyFontColor: "#858796",
          titleMarginBottom: 10,
          titleFontColor: '#6e707e',
          titleFontSize: 14,
          borderColor: '#dddfeb',
          borderWidth: 1,
          xPadding: 15,
          yPadding: 15,
          displayColors: false,
          intersect: false,
          mode: 'index',
          caretPadding: 10,
          callbacks: {
            label: function(tooltipItem, chart) {
              var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
              return datasetLabel + ': ' + number_format(tooltipItem.yLabel) + ' GB';
            }
          }
        }
      }
    });
       
</script>
</body>

</html>