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
    
</head>



<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

<!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">
    <!-- Main Content -->
    <div id="content">
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
	<div class="col-xl-12 col-lg-12">
		<div class="card shadow mb-4 border-primary">
        	<h6 class="card-header py-3 d-flex flex-row align-items-center justify-content-between m-0 font-weight-bold text-primary">Hume HSIE CommPoints<i class="fas fa-cog fa-lg" style="cursor: pointer;" onclick="window.open('${console}', '_blank');"></i></h6>
            <!-- Card Body -->
            <div class="card-body">
           		<div class="row col-xl-12 col-lg-12">
           			<!-- Inbound -->
            		<div class="col-xl-6 col-md-6">
						<div class="card border-success h-100">
							<div class="card-header bg-success text-white ellipsis"
								style="font-size: 16px;">
								<i class="fa fa-cog"></i>&nbsp;Inbound
							</div>
							<div class="card-body d-flex align-items-center justify-cntent-center">
								<div class="card-text ellipsis" style="font-size: 15px;">
									
									
									
									<!-- iterate inbound list -->		           											
									<div class="row col-xl-12 col-lg-12">
										<c:forEach items="${inboundList}" var="inbound">
										<div class="card border-primary col-md-2 col-mx-2 col-lg-4" style="cursor: pointer;" onclick="displayLog('${inbound.name}')">
											<c:set var="formattedName" value="${fn:replace(fn:substringAfter(inbound.name, 'CP.tcp_'), '_IO', '')}" />
											<div class="card-header">
											    <c:out value="${formattedName}"/>
											</div>
											<div class="card-body">
												<table border="1">
													<tbody>
														<tr>
															<td colspan="4">
															Uptime : <c:out value="${inbound.uptime}"/>
															</td>	
														</tr>
														<tr>
															<td colspan="4">
															<i class="fas fa-link text-primary" data-toggle="tooltip" title="Number Of Connections"></i> <c:out value="${inbound.connectionCount}"/>
															</td>	
														</tr>
														<tr>
															<td colspan="2">Input</td>
															<td colspan="2">Output</td>
														</tr>
														<tr>
															<td>
															<c:out value="${inbound.receivedCount}"/>
															</td>
															<td colspan="2"><i class="far fa-check-circle text-primary" data-toggle="tooltip" title="Processed Messages"></i></td>
															<td>
															<c:out value="${inbound.sentCount}"/>
															</td>															
														</tr>
														<tr>
															<td>
															<c:out value="${inbound.inQueueSize}"/>
															</td>
															<td colspan="2"><i class="far fa-comment-alt text-primary" data-toggle="tooltip" title="Current Message Count"></i></td>
															<td>
															<c:out value="${inbound.outQueueSize}"/>
															</td>															
														</tr>
														<tr>
															<td>
															<c:out value="${inbound.inputIdleTime}"/>
															</td>
															<td colspan="2"><i class="far fa-hourglass text-primary" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
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
						</div>
			        </div>
            
					<!-- Outbound -->            
		            <div class="col-xl-6 col-md-6">
		        	 	<div class="card border-primary">
		            		<div class="card-header bg-primary text-white ellipsis">
               					<i class="fas fa-memory"></i> &nbsp;Outbound
           					</div>
           					
           					
           						<div class="card-body d-flex align-items-center justify-cntent-center">
								<div class="card-text ellipsis" style="font-size: 15px;">
									
									
									
									<!-- iterate inbound list -->		           											
									<div class="row col-xl-12 col-lg-12">
										<c:forEach items="${outboundList}" var="outbound">
										<div class="card border-primary col-md-2 col-mx-2 col-lg-4" style="cursor: pointer;" onclick="displayLog('${outbound.name}')">
											<c:set var="formattedName" value="${fn:replace(fn:substringAfter(outbound.name, 'CP.tcp_'), '_IO', '')}" />
											<div class="card-header">
											    <c:out value="${formattedName}"/>
											</div>
											<div class="card-body">
												<table border="1">
													<tbody>
														<tr>
															<td colspan="4">
															Uptime : <c:out value="${outbound.uptime}"/>
															</td>	
														</tr>
														<tr>
															<td colspan="4">
															<i class="fas fa-link text-primary" data-toggle="tooltip" title="Number Of Connections"></i> <c:out value="${outbound.connectionCount}"/>
															</td>	
														</tr>
														<tr>
															<td colspan="2">Input</td>
															<td colspan="2">Output</td>
														</tr>
														<tr>
															<td>
															<c:out value="${outbound.receivedCount}"/>
															</td>
															<td colspan="2"><i class="far fa-check-circle text-primary" data-toggle="tooltip" title="Processed Messages"></i></td>
															<td>
															<c:out value="${outbound.sentCount}"/>
															</td>															
														</tr>
														<tr>
															<td>
															<c:out value="${outbound.inQueueSize}"/>
															</td>
															<td colspan="2"><i class="far fa-comment-alt text-primary" data-toggle="tooltip" title="Current Message Count"></i></td>
															<td>
															<c:out value="${outbound.outQueueSize}"/>
															</td>															
														</tr>
														<tr>
															<td>
															<c:out value="${outbound.inputIdleTime}"/>
															</td>
															<td colspan="2"><i class="far fa-hourglass text-primary" data-toggle="tooltip" title="Idle Time Since Last Message"></i></td>
															<td>
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
           					
           					
           					
           					
           					
           					
           					
           					
           					
           					
           					
           					
           					
       					</div>
       				 </div>
        	 		
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
                 <h6 class="m-0 font-weight-bold text-primary">Log Details</h6>
            </div>
            <!-- Card Body -->
    		<div class="card-body">
    			<div class="table-responsive">
	        			<div class="col-sm-12">
		                 	<table class="table table-bordered dataTable" id="logTable" role="grid" aria-describedby="dataTable_info">
		                    	<thead>
		                        	<tr role="row">
		                        		<th class="text-center align-middle text-primary">DTM</th>
		                        		<th class="text-center align-middle text-primary">Sending App</th>
										<th class="text-center align-middle text-primary">Receiving App</th>
										<th class="text-center align-middle text-primary">Msg Type</th>
										<th class="text-center align-middle text-primary">Msg Event</th>
		                        		<th class="text-center align-middle text-primary">Msg Cont Id</th>
										<th class="text-center align-middle text-primary">Patient UR</th>
										<th class="text-center align-middle text-primary">Given Name</th>
										<th class="text-center align-middle text-primary">Family Name</th>
										<th class="text-center align-middle text-primary">Sex</th>
										<th class="text-center align-middle text-primary">Visit ID</th>
										<th class="text-center align-middle text-primary">HL7 Msg</th>
		                        	</tr>
		                        </thead>
		                        <%--
		                        <tbody>
		                        	<c:forEach items="${logList}" var="log">
									<tr>
										<td class="text-left align-middle"><span><c:out value="${log.lastUpdate}" /></span></td>
										<td class="text-left align-middle"><span><c:out value="${log.sendingApp}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${log.receivingApp}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${log.msgType}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${log.msgEvent}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${log.msgId}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${log.patientUr}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${log.patientFirstName}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${log.patientLastName}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${log.patientGender}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${log.visitingId}" /></span></td>
										<td class="text-center align-middle" onclick="showHL7Message('${log.auditId}')" style="cursor: pointer;"><span><c:out value="${log.auditId}" /></span></td>
									</tr>
									</c:forEach>
		                        </tbody>
		                        --%>
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
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">x</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="login.html">Logout</a>
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
	
    $.ajax({
        url : '${pageContext.request.contextPath}/getLogSection/' + encodedService,
        method: "GET",
        success: function(data) {
            var tbody = $('#logTable tbody');
            tbody.empty(); // Clear existing rows
            $.each(data, function(index, log) {
                console.log(log);
                var row = $('<tr>');
                row.append('<td class="text-left align-middle">' + log.lastUpdate + '</td>');
                row.append('<td class="text-left align-middle">' + log.sendingApp + '</td>');
                row.append('<td class="text-right align-middle">' + log.receivingApp + '</td>');
                row.append('<td class="text-center align-middle">' + log.msgType + '</td>');
                row.append('<td class="text-right align-middle">' + log.msgEvent + '</td>');
                row.append('<td class="text-right align-middle">' + log.msgId + '</td>');
                row.append('<td class="text-center align-middle">' + log.patientUr + '</td>');
                row.append('<td class="text-right align-middle">' + log.patientFirstName + '</td>');
                row.append('<td class="text-right align-middle">' + log.patientLastName + '</td>');
                row.append('<td class="text-center align-middle">' + log.patientGender + '</td>');
                row.append('<td class="text-center align-middle">' + log.visitingId + '</td>');
                row.append('<td class="text-center align-middle" style="cursor: pointer;" onclick="showHL7Message(\'' + log.auditId + '\')">' + log.auditId + '</td>');
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
    $.ajax({
        url : '${pageContext.request.contextPath}/logDetail/' + id,
        method: "GET",
        success: function(data) {
            console.log(data);
            alert(data);
             // Reinitialize tooltips after content is added
            // $('[data-toggle="tooltip"]').tooltip();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Error : ' + errorThrown);
        }
    });
}
</script>

       
</body>

</html>