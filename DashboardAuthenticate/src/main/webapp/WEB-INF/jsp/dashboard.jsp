<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
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
<!-- Hume Info : Total msg count & last update with Pie chart -->
<!-- ######################################################### -->
<div class="row">
	<div class="col-xl-12 col-lg-12">
		<div class="card shadow mb-4 border-primary">
        	<h6 class="card-header py-3 d-flex flex-row align-items-center justify-content-between m-0 font-weight-bold text-primary">Hume HSIE Application Server<i class="fas fa-cog fa-lg" style="cursor: pointer;"></i></h6>
            <!-- Card Body -->
            <div class="card-body">
           		<div class="row col-xl-12 col-lg-12">
           			<!-- Server Information -->
            		<div class="col-xl-3 col-md-3 mb-4">
						<div class="card border-success h-100">
							<div class="card-header bg-success text-white ellipsis"
								style="font-size: 16px;">
								<i class="fa fa-cog"></i>&nbsp;Server Isnformation
							</div>
							<div class="card-body d-flex align-items-center justify-cntent-center">
								<div class="card-text ellipsis" style="font-size: 15px;">
									<span class="text-muted">Rhapsody Name</span>&nbsp;&nbsp;<span class="font-weight-bold text-success">${engineInfo.name}</span><br>
									Version &nbsp;&nbsp;<span class="font-weight-bold text-success">${engineInfo.version}</span><br>
									Since &nbsp;&nbsp;<span class="font-weight-bold text-success">${engineInfo.uptime}</span><br>
									<c:set var="aDisk1" value="${Math.round(engineInfo.availableDisk/1024)}" />
									<c:set var="tDisk1" value="${Math.round(engineInfo.totalDisk/1024)}" />
									
									Available Space &nbsp;<span class="font-weight-bold text-success">${aDisk1} GB</span><br> 
									Total Space &nbsp;&nbsp;<span class="font-weight-bold text-success">${tDisk1} GB</span><br>
									
									Disk Allocated<span class="float-right font-weight-bold text-success"><fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(tDisk1-aDisk1) / tDisk1}" /></span><br>
									<div class="progress mb-4">
                     					<div class="progress-bar progress-bar-success" role="progressbar" style="width: <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${(tDisk1-aDisk1) / tDisk1} " />;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                 					</div>
                 					
                 					<span class="font-weight-bolder font-italic">Message Transactions</span><br>
									${totalMsgCnt}
								</div>
							</div>
						</div>
			        </div>
            
            		<!-- CPU -->
            		<div class="col-xl-3 col-md-3 mb-4">
						<div class="card border-info h-100">
							<div class="card-header bg-info text-white">
								<i class="fa fa-microchip"></i> &nbsp;CPU
							</div>
							<div class="card-body d-flex align-items-center justify-cntent-center">
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
               					<i class="fas fa-memory"></i> &nbsp;Memory Timeline
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

<!-- Communication Points Row -->
<!-- ######################################################### -->
<!-- CommPoint Info : Commpoints List -->
<!-- ######################################################### -->

<div class="row">
	<div class="col-xl-12 col-lg-12">
		<div class="card shadow mb-4">
        	<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                 <h6 class="m-0 font-weight-bold text-primary">Communication Point Details</h6>
            </div>
            <!-- Card Body -->
    		<div class="card-body">
    			<div class="table-responsive">
	        		<div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
	               		<div class="col-sm-12">
		                 	<table class="table table-bordered dataTable" id="commpointTable" role="grid" aria-describedby="dataTable_info">
		                    	<thead>
		                        	<tr role="row">
		                        		<th class="text-center align-middle text-primary" rowspan="2">Name</th>
		                        		<th class="text-center align-middle text-primary" rowspan="2">Mode</th>
										<th class="text-center align-middle text-primary" colspan="3">Input</th>
										<th class="text-center align-middle text-primary" colspan="3">Output</th>
										<th class="text-center align-middle text-primary" rowspan="2">Uptime</th>
										<th class="text-center align-middle" rowspan="2"><i class="fas fa-link text-primary" data-toggle="tooltip" title="Number Of Connections"></i></th>
		                        	</tr>
		                        	<tr>
		                        		<th class="text-center align-middle"><i class="far fa-check-circle text-primary" data-toggle="tooltip" title="Processed Messages"></i></th>
										<th class="text-center align-middle"><i class="far fa-comment-alt text-primary" data-toggle="tooltip" title="Current Message Count"></i></th>
										<th class="text-center align-middle"><i class="far fa-hourglass text-primary" data-toggle="tooltip" title="Idle Time Since Last Message"></i></th>
										<th class="text-center align-middle"><i class="far fa-check-circle text-primary" data-toggle="tooltip" title="Processed Messages"></i></th>
										<th class="text-center align-middle"><i class="far fa-comment-alt text-primary" data-toggle="tooltip" title="Current Message Count"></i></th>
										<th class="text-center align-middle"><i class="far fa-hourglass text-primary" data-toggle="tooltip" title="Idle Time Since Last Message"></i></th>
		                        	</tr>
		                        </thead>
		                        <tbody>
		                        	<c:forEach items="${commpointList}" var="point">
									<tr>
										<td class="text-left align-middle"><span><c:out value="${point.name}" /></span></td>
										<td class="text-left align-middle"><span><c:out value="${point.mode}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${point.receivedCount}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${point.inQueueSize}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${point.inputIdleTime}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${point.sentCount}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${point.outQueueSize}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${point.outputIdleTime}" /></span></td>
										<td class="text-right align-middle"><span><c:out value="${point.uptime}" /></span></td>
										<td class="text-center align-middle"><span><c:out value="${point.connectionCount}" /></span></td>
									</tr>
									</c:forEach>
		                        </tbody>
		                     </table>
    					</div>
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
        $('#commpointTable').DataTable({
            "paging": true,
            "searching": true,
            "ordering": true,
            "info": true,
            "responsive": true,
            "autoWidth": false,
            //"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            "language": {
                "search": "Search:",
                "lengthMenu": "Show _MENU_ entries",
                "info": "Showing _START_ to _END_ of _TOTAL_ entries"
            }
        });

        // Activate Bootstrap tooltips
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>
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
				backgroundColor : [ "#1cc88a", "#f6c23e","#e74a3b" ],
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